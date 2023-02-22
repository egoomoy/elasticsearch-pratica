package io.elasticsearch.pratica.crsseq.model.elasticsearch.qurey.filters;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class KeywordsFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.SearchReq req) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords())
                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(matchQueryBuilder)
                .should(termQueryBuilder);
        return boolQueryBuilder;
    }
}
