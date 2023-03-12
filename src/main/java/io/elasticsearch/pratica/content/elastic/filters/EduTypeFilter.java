package io.elasticsearch.pratica.content.elastic.filters;

import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import org.elasticsearch.index.query.*;

public class EduTypeFilter {
    public static AbstractQueryBuilder createFilter(ContentDTO.Req req) {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(ContentDTO.Req.Fields.eduType, req.getEduType());
//        QueryBuilder qb = QueryBuilders.boolQuery().filter(matchQueryBuilder);

        return matchQueryBuilder;
    }
}
