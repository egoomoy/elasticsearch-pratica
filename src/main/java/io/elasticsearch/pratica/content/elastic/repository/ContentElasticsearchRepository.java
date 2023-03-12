package io.elasticsearch.pratica.content.elastic.repository;

import io.elasticsearch.pratica.common.elastic.repository.BaseElasticSearchRepository;
import io.elasticsearch.pratica.content.elastic.document.ContentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContentElasticsearchRepository extends ElasticsearchRepository<ContentDocument, String>
        , BaseElasticSearchRepository<ContentDocument> {
}
