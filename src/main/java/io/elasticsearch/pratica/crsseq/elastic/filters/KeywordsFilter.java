<<<<<<<< HEAD:src/main/java/io/elasticsearch/pratica/crsseq/elastic/filters/KeywordsFilter.java
package io.elasticsearch.pratica.crsseq.elastic.filters;
========
package io.elasticsearch.pratica.crsseq.elasticsearch.filters;
>>>>>>>> 7d9e2d911fae614c3db5ba2cc30f15f6a8bdc458:src/main/java/io/elasticsearch/pratica/crsseq/elasticsearch/filters/KeywordsFilter.java

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
