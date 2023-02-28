package io.elasticsearch.pratica.crsseq.elasticsearch.repository;

import io.elasticsearch.pratica.common.elasticsearch.repository.CustomElasticsearchRepository;
import io.elasticsearch.pratica.crsseq.elasticsearch.document.CrsseqDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CrsseqElasticsearchRepository extends ElasticsearchRepository<CrsseqDocument, String>
        , CustomElasticsearchRepository<CrsseqDocument> {
}
