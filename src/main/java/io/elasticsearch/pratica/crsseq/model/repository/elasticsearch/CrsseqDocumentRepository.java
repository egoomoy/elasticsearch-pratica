package io.elasticsearch.pratica.crsseq.model.repository.elasticsearch;

import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CrsseqDocumentRepository extends ElasticsearchRepository<CrsseqDocument, String>
        , BaseElasticSearchRepository<CrsseqDocument> {

}
