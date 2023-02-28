package io.elasticsearch.pratica.crsseq.elastic.filters;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;

public class KeywordsFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.SearchReq req) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords());
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords())
                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(matchQueryBuilder)
                .should(termQueryBuilder);
        return boolQueryBuilder;
    }
}
