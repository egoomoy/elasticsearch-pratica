package io.elasticsearch.pratica.crsseq.elastic.filters;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;

public class KeywordsMatchFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.SearchReq req) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords())
                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%");
        return matchQueryBuilder;
    }
}
