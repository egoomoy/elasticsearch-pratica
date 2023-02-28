package io.elasticsearch.pratica.crsseq.service.impl;

import io.elasticsearch.pratica.crsseq.elasticsearch.builder.CrsseqIndexBuilder;
import io.elasticsearch.pratica.crsseq.elasticsearch.builder.CrsseqQueryBuilder;
import io.elasticsearch.pratica.crsseq.elasticsearch.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.elasticsearch.repository.CrsseqElasticsearchRepository;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import io.elasticsearch.pratica.crsseq.model.repository.CrsseqRespository;
import io.elasticsearch.pratica.crsseq.service.CrsseqService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrsseqServiceImpl implements CrsseqService {
    private static final String INDEX_PREFIX_NAME = "el_crsseq";
    private static final String ALIAS_NAME = "el_crsseq";

    private final CrsseqRespository crsseqRespository;
    private final CrsseqElasticsearchRepository crsseqElasticsearchRepository;
    private final CrsseqIndexBuilder crsseqIndexBuilder;
    private final ModelMapper modelMapper;
    private final CrsseqQueryBuilder crsseqQueryBuilder;
    private final ElasticsearchOperations operations;

    @Override
    @Transactional
    public boolean rollingCrsseqIndex() throws Exception {
        boolean rtn = false;

        // 1. 인덱스 생성
        String newIndexName = INDEX_PREFIX_NAME + "-" + Instant.now().toEpochMilli();
        crsseqElasticsearchRepository.createIndex(newIndexName, crsseqIndexBuilder.getSettingsBuilder(), crsseqIndexBuilder.getMappingBuilder());
        // 1.1 인덱스 래핑
        IndexCoordinates indexNameWrapper = IndexCoordinates.of(newIndexName);
        IndexCoordinates aliasNameWrapper = IndexCoordinates.of(ALIAS_NAME);

        // 2. alias 됐던 인덱스 전부를 찾는다.
        Set<String> existIndexNames = crsseqElasticsearchRepository.findIndexNamesByAlias(aliasNameWrapper);

        // 3. JPA를 통해 필요 데이터를 조회한다.
        List<Crsseq> crsseqList = crsseqRespository.findAll();

        // 4. Entity => Document로 변환한다.
//        List<CrsseqDocument> crsseqDocuments = crsseqList.stream()
//                .map(seq -> modelMapper.map(seq, CrsseqDocument.class))
//                .collect(Collectors.toList());
        // 5. Document 저장 (3/4번에서 조회->변환된 데이터)
//        crsseqDocumentRepository.saveAll(crsseqDocuments, indexNameWrapper);

        // 4/5 recode bulk index
        List<IndexQuery> indexQueries = crsseqList.stream()
                .map(seq -> {
                    CrsseqDocument crsseqDocument = modelMapper.map(seq, CrsseqDocument.class);
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setObject(crsseqDocument);
                    return indexQuery;
                })
                .collect(Collectors.toList());
        crsseqList.stream().map(seq -> modelMapper.map(seq, CrsseqDocument.class));
        operations.bulkIndex(indexQueries, indexNameWrapper);

        // 6. 2번에서 조회된 index를 일괄 삭제한다. 필요에 따라 해당기능만 발라서 배치처리
        existIndexNames.forEach(indexName -> crsseqElasticsearchRepository.deleteIndex(IndexCoordinates.of(indexName)));

        // 7. 5번에서 저장된 데이터를 alias처리
        rtn = crsseqElasticsearchRepository.setAlias(indexNameWrapper, aliasNameWrapper);

        return rtn;
    }

    @Override
    @Transactional
    public Crsseq saveCrsseq(CrsseqDTO.Req reqCrsseq) throws Exception {
        // 1. JPA를 통한 업데이트 필요
        // 1-1. API로 호출될 경우 별도 db로 저장하지 않아도 되는 점
        Crsseq crsseq = modelMapper.map(reqCrsseq, Crsseq.class);
        crsseqRespository.save(crsseq);

        // 2. el DOC에 추가
        // TODO: 인덱스 롤링일 때는 부분 색인을 진행하지 않도록 조치할 것 + JPA와 비즈니스가 같이 있는게 안정성 측면에서 문제되지 않나?
        // 물론 MSA라고 가정하면 분리된 트랜잭션이라서 문제 없을 것 같다.
        CrsseqDocument crsseqDocument = modelMapper.map(reqCrsseq, CrsseqDocument.class);
        IndexCoordinates aliasNameWrapper = IndexCoordinates.of(ALIAS_NAME);
        crsseqElasticsearchRepository.save(crsseqDocument, aliasNameWrapper);

        return crsseq;
    }

    @Override
    @Transactional
    public SearchPage<CrsseqDocument> getCrsseq(CrsseqDTO.SearchReq reqCrsseq) throws Exception {
        crsseqQueryBuilder.createQuery(reqCrsseq);
        NativeSearchQuery searchQuery = crsseqQueryBuilder.getSearch();
        SearchHits<CrsseqDocument>  searchHits = operations.search(searchQuery,CrsseqDocument.class);
        SearchPage<CrsseqDocument>  searchPage = SearchHitSupport.searchPageFor(searchHits,crsseqQueryBuilder.getPageRequest());

        return searchPage;
    }
}
