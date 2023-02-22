package io.elasticsearch.pratica.crsseq.model.elasticsearch.qurey;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.elasticsearch.filters.KeywordsFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

@Component
public class QueryBuilder implements QueryBuilderInterface {
    private NativeSearchQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public QueryBuilder() {
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

    @Override
    public NativeSearchQuery getSearch() {
        return this.searchQueryBuilder.build();
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }
}
