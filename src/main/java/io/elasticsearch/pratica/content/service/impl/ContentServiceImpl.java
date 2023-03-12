package io.elasticsearch.pratica.content.service.impl;

import io.elasticsearch.pratica.common.util.ModelMapperUtil;
import io.elasticsearch.pratica.content.elastic.builder.ContentIndexBuilder;
import io.elasticsearch.pratica.content.elastic.builder.ContentQueryBuilder;
import io.elasticsearch.pratica.content.elastic.document.ContentDocument;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import io.elasticsearch.pratica.content.model.entity.Content;
import io.elasticsearch.pratica.content.elastic.repository.ContentElasticsearchRepository;
import io.elasticsearch.pratica.content.model.repository.ContentRespository;
import io.elasticsearch.pratica.content.service.ContentService;
import lombok.RequiredArgsConstructor;
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
public class ContentServiceImpl implements ContentService {
    private static final String INDEX_PREFIX_NAME = "el_content";
    private static final String ALIAS_NAME = "el_content";

    private final ContentIndexBuilder contentIndexBuilder;
    private final ContentRespository contentRespository;
    private final ContentElasticsearchRepository contentElasticsearchRepository;
    private final ContentQueryBuilder contentQueryBuilder;
    private final ElasticsearchOperations operations;

    @Override
    @Transactional
    public boolean rollingContentIndex() throws Exception {
        boolean rtn = false;

        // 1. 인덱스 생성
        String newIndexName = INDEX_PREFIX_NAME + "-" + Instant.now().toEpochMilli();
        contentElasticsearchRepository.createIndex(newIndexName, contentIndexBuilder.getSettingsBuilder(), contentIndexBuilder.getMappingBuilder());
        // 1.1 인덱스 래핑
        IndexCoordinates indexNameWrapper = IndexCoordinates.of(newIndexName);
        IndexCoordinates aliasNameWrapper = IndexCoordinates.of(ALIAS_NAME);

        // 2. alias 됐던 인덱스 전부를 찾는다.
        Set<String> existIndexNames = contentElasticsearchRepository.findIndexNamesByAlias(aliasNameWrapper);

        // 3. JPA를 통해 필요 데이터를 조회한다.
        List<Content> contentList = contentRespository.findAll();

//        // 4. Entity => Document로 변환한다.
//        List<ContentDocument> contentDocuments = contentList.stream()
//                .map(seq -> modelMapper.map(seq, ContentDocument.class))
//                .collect(Collectors.toList());
//        // 5. Document 저장 (3/4번에서 조회->변환된 데이터)
//        contentElasticsearchRepository.saveAll(contentDocuments, indexNameWrapper);

        // 4/5 recode bulk index 4,5 도 동일하게 벌크로 넘겨주는 것은 가능하나, saveAll을 저렇게 수정하지 않으면 안되네
        // save를 까보면 결국 doc 이름을 Coordinate하게되는데, 수정없이 사용하면 alias처리가 안됨
        List<IndexQuery> indexQueries = contentList.stream()
                .map(seq -> {
                    ContentDocument contentDocument =  ModelMapperUtil.map(seq, ContentDocument.class);
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setObject(contentDocument);
                    return indexQuery;
                })
                .collect(Collectors.toList());
        contentList.stream().map(seq -> ModelMapperUtil.map(seq, ContentDocument.class));
        operations.bulkIndex(indexQueries, indexNameWrapper);

        // 6. 2번에서 조회된 index를 일괄 삭제한다. 필요에 따라 해당기능만 발라서 배치처리
        existIndexNames.forEach(indexName -> contentElasticsearchRepository.deleteIndex(IndexCoordinates.of(indexName)));

        // 7. 5번에서 저장된 데이터를 alias처리
        rtn = contentElasticsearchRepository.setAlias(indexNameWrapper, aliasNameWrapper);

        return rtn;
    }

    @Override
    @Transactional
    public Content saveContent(ContentDTO.Req reqContent) throws Exception {
        // 1. JPA를 통한 업데이트 필요
        // 1-1. API로 호출될 경우 별도 db로 저장하지 않아도 되는 점
        Content content =  ModelMapperUtil.map(reqContent, Content.class);
        contentRespository.save(content);

        // 2. el DOC에 추가
        // TODO: 인덱스 롤링일 때는 부분 색인을 진행하지 않도록 조치할 것 + JPA와 비즈니스가 같이 있는게 안정성 측면에서 문제되지 않나?
        // 물론 MSA라고 가정하면 분리된 트랜잭션이라서 문제 없을 것 같다.
        ContentDocument contentDocument = ModelMapperUtil.map(content, ContentDocument.class);
        IndexCoordinates aliasNameWrapper = IndexCoordinates.of(ALIAS_NAME);
        contentElasticsearchRepository.save(contentDocument, aliasNameWrapper);

        return content;
    }

    @Override
    @Transactional
    public SearchPage<ContentDocument> getContent(ContentDTO.Req reqContent) throws Exception {
        contentQueryBuilder.createQuery(reqContent);
        NativeSearchQuery searchQuery = contentQueryBuilder.getSearch();
        SearchHits<ContentDocument>  searchHits = operations.search(searchQuery, ContentDocument.class);
        SearchPage<ContentDocument>  searchPage = SearchHitSupport.searchPageFor(searchHits, contentQueryBuilder.getPageRequest());

        return searchPage;
    }
}
