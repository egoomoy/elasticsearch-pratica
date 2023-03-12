package io.elasticsearch.pratica.content.elastic.filters;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import org.elasticsearch.index.query.*;

public class KeywordsTermFilter {
    public static AbstractQueryBuilder createFilter(ContentDTO.Req req) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(ContentDTO.Req.Fields.keywords, req.getKeywords());

        return termQueryBuilder;
    }
}
