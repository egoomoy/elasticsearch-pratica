package io.elasticsearch.pratica.crsseq.model.repository.elasticsearch;

import io.elasticsearch.pratica.common.repository.elasticsearch.BaseElasticSearchRepository;
import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.io.IOException;

public interface CrsseqDocumentRepository extends ElasticsearchRepository<CrsseqDocument, String>
        , BaseElasticSearchRepository<CrsseqDocument> {

    }
