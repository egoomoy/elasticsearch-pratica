package io.elasticsearch.pratica.content.elastic.filters;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import org.elasticsearch.index.query.*;

public class KeywordsMatchFilter {
    public static AbstractQueryBuilder createFilter(ContentDTO.Req req) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(ContentDTO.Req.Fields.keywords, req.getKeywords())
                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%");
        return matchQueryBuilder;
    }
}
