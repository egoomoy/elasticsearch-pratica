package io.elasticsearch.pratica.crsseq.elastic.filters;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;

public class EduTypeFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.Req req) {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(CrsseqDTO.Req.Fields.eduType, req.getEduType());
//        QueryBuilder qb = QueryBuilders.boolQuery().filter(matchQueryBuilder);

        return matchQueryBuilder;
    }
}
