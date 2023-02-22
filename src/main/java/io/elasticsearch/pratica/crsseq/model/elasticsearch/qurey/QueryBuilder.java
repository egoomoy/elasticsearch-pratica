package io.elasticsearch.pratica.crsseq.model.elasticsearch.Impl;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.elasticsearch.QueryBuilderInterface;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

public class QueryBuilder implements QueryBuilderInterface {
    @Override
    public void createQuery(CrsseqDTO.Req req) {

    }

    @Override
    public NativeSearchQuery getSearch() {
        return null;
    }
}
