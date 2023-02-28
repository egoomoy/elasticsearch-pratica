package io.elasticsearch.pratica.crsseq.elasticsearch.builder;

import io.elasticsearch.pratica.common.elasticsearch.qurey.QueryBuilderInterface;
import io.elasticsearch.pratica.crsseq.elasticsearch.filters.KeywordsFilter;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class CrsseqQueryBuilder implements QueryBuilderInterface<CrsseqDTO.SearchReq> {
    private NativeSearchQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public CrsseqQueryBuilder() {
        this.searchQueryBuilder = new NativeSearchQueryBuilder();
    }

    @Override
    public void createQuery(CrsseqDTO.SearchReq req) {
        this.setPageOffset(req);
        this.setFilters(req);
        this.setAggregation(req);
        this.setSorting(req);
        this.setFields(req);
    }

    @Override
    public NativeSearchQuery getSearch() {
        return this.searchQueryBuilder.build();
    }

    private void setFields(CrsseqDTO.SearchReq req) {
    }

    private void setSorting(CrsseqDTO.SearchReq req) {
    }

    private void setAggregation(CrsseqDTO.SearchReq req) {
    }

    private void setFilters(CrsseqDTO.SearchReq req) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (!req.getKeywords().isEmpty()) {
            boolQueryBuilder.should(KeywordsFilter.createFilter(req));
        }
        this.searchQueryBuilder.withQuery(boolQueryBuilder);
    }

    private void setPageOffset(CrsseqDTO.SearchReq req) {
        this.pageRequest = PageRequest.of(req.getPageNumber(), req.getPageSize());
        this.searchQueryBuilder.withPageable(this.pageRequest);
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }
}
