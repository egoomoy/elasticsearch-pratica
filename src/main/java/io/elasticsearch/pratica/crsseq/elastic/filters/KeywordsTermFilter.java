package io.elasticsearch.pratica.crsseq.elastic.filters;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.*;

public class KeywordsTermFilter {
    public static AbstractQueryBuilder createFilter(CrsseqDTO.Req req) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(CrsseqDTO.Req.Fields.keywords, req.getKeywords());

        return termQueryBuilder;
    }
}
