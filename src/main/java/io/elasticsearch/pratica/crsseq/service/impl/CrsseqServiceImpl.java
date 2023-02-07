package io.elasticsearch.pratica.crsseq.service.impl;

import io.elasticsearch.pratica.common.util.IndexUtil;
import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import io.elasticsearch.pratica.crsseq.model.repository.elasticsearch.CrsseqDocumentRepository;
import io.elasticsearch.pratica.crsseq.model.repository.jpa.CrsseqRespository;
import io.elasticsearch.pratica.crsseq.service.CrsseqService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

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
    @Override
    public void indexingCrsseqDate() throws Exception {
        // 1. 인덱스 네임 생성
        IndexCoordinates indexNameWrapper = IndexUtil.createIndexNameWithPostFixWrapper(INDEX_PREFIX_NAME);
        IndexCoordinates aliasNameWrapper = IndexUtil.createIndexNameWrapper(ALIAS_NAME);

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
        existIndexNames.forEach(indexName -> crsseqDocumentRepository.deleteIndex(IndexUtil.createIndexNameWrapper(indexName)));

        // 7. 5번에서 저장된 데이터를 alias처리
        crsseqDocumentRepository.setAlias(indexNameWrapper, aliasNameWrapper);
    }
}
