package io.elasticsearch.pratica.crsseq.elastic.filters;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;

public class EduTypeFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.SearchReq req) {



//                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords());
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords())
//                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%");
//
//
//
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .should(matchQueryBuilder)
//                .should(termQueryBuilder);

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.eduType, req.getEduType());
        QueryBuilder qb = QueryBuilders.boolQuery().filter(matchQueryBuilder);

        return (AbstractQueryBuilder) qb;
    }
}
