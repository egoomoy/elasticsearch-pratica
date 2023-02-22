package io.elasticsearch.pratica.crsseq.model.repository.elasticsearch;

import io.elasticsearch.pratica.common.repository.elasticsearch.BaseElasticSearchRepository;
import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import org.elasticsearch.xcontent.XContentBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Map;

public interface CrsseqDocumentRepository extends ElasticsearchRepository<CrsseqDocument, String>
        , BaseElasticSearchRepository<CrsseqDocument> {
    public XContentBuilder getMappingBuilder() throws Exception;
    public XContentBuilder getSettingsBuilder() throws Exception;
    public SearchPage<CrsseqDocument> termAndMatchFind(String index, Map<String, Object> searchParam, Pageable pageable) throws  Exception;
}
