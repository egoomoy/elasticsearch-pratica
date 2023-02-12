package io.elasticsearch.pratica.crsseq.service.impl;

import io.elasticsearch.pratica.common.util.elasticsearch.IndexUtil;
import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import io.elasticsearch.pratica.crsseq.model.repository.elasticsearch.CrsseqDocumentRepository;
import io.elasticsearch.pratica.crsseq.model.repository.jpa.CrsseqRespository;
import io.elasticsearch.pratica.crsseq.service.CrsseqService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrsseqServiceImpl implements CrsseqService {

    private static final String INDEX_PREFIX_NAME ="el_crsseq";
    private static final String ALIAS_NAME = "el_crsseq";

    private final CrsseqRespository crsseqRespository;
    private final CrsseqDocumentRepository crsseqDocumentRepository;
    private final ModelMapper modelMapper;
    private final IndexUtil indexUtil;

    @Override
    @Transactional
    public void rollingCrsseqIndex() throws Exception {
        // 1. 인덱스 생성
        String newIndexName = INDEX_PREFIX_NAME + "-" + Instant.now().toEpochMilli();
        indexUtil.createIndex(newIndexName,crsseqDocumentRepository.getSettingsBuilder(), crsseqDocumentRepository.getMappingBuilder());

        // 1.1 인덱스
        IndexCoordinates indexNameWrapper = indexUtil.createIndexNameWrapper(newIndexName);
        IndexCoordinates aliasNameWrapper = indexUtil.createIndexNameWrapper(ALIAS_NAME);

        // 2. alias 됐던 인덱스 전부를 찾는다.
        Set<String> existIndexNames = crsseqDocumentRepository.findIndexNamesByAlias(aliasNameWrapper);

        // 3. JPA를 통해 필요 데이터를 조회한다.
        List<Crsseq> crsseqList = crsseqRespository.findAll();

        // 4. Entity => Document로 변환한다.
        List<CrsseqDocument> crsseqDocuments = crsseqList.stream()
                .map(seq->modelMapper.map(seq,CrsseqDocument.class))
                .collect(Collectors.toList());

        // 5. Document 저장 (3/4번에서 조회->변환된 데이터)
        crsseqDocumentRepository.saveAll(crsseqDocuments,indexNameWrapper);

        // 6. 2번에서 조회된 index를 일괄 삭제한다. 필요에 따라 해당기능만 발라서 배치처리
        existIndexNames.forEach(indexName -> crsseqDocumentRepository.deleteIndex(indexUtil.createIndexNameWrapper(indexName)));

        // 7. 5번에서 저장된 데이터를 alias처리
        crsseqDocumentRepository.setAlias(indexNameWrapper, aliasNameWrapper);
    }

    @Override
    @Transactional
    public void saveCrsseq(CrsseqDTO crsseqDTO) throws Exception {
        // 1. JPA를 통한 업데이트 필요
        // 1-1. API로 호출될 경우 별도 db로 저장하지 않아도 되는 점
        Crsseq crsseq = modelMapper.map(crsseqDTO,Crsseq.class);
        crsseqRespository.save(crsseq);

        // 2. el DOC에 추가
        // TODO: 인덱스 롤링일 때는 부분 색인을 진행하지 않도록 조치할 것 + JPA와 비즈니스가 같이 있는게 안정성 측면에서 문제되지 않나?
        // 물론 MSA라고 가정하면 분리된 트랜잭션이라서 문제 없을 것 같다.
        CrsseqDocument crsseqDocument = modelMapper.map(crsseq, CrsseqDocument.class);
        IndexCoordinates aliasNameWrapper = indexUtil.createIndexNameWrapper(ALIAS_NAME);
        crsseqDocumentRepository.save(crsseqDocument,aliasNameWrapper);
    }
}
