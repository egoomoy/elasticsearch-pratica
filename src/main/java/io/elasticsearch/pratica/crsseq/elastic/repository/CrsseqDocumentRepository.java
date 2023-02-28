package io.elasticsearch.pratica.crsseq.elastic.repository;

import io.elasticsearch.pratica.common.elastic.repository.BaseElasticSearchRepository;
import io.elasticsearch.pratica.crsseq.elastic.document.CrsseqDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CrsseqDocumentRepository extends ElasticsearchRepository<CrsseqDocument, String>
        , BaseElasticSearchRepository<CrsseqDocument> {
}
