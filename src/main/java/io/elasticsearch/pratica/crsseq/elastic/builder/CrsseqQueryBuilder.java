package io.elasticsearch.pratica.crsseq.elastic.builder;

import io.elasticsearch.pratica.common.elastic.builder.QueryBuilderInterface;
import io.elasticsearch.pratica.crsseq.elastic.filters.EduTypeFilter;
import io.elasticsearch.pratica.crsseq.elastic.filters.KeywordsTermFilter;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.elastic.filters.KeywordsMatchFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class CrsseqQueryBuilder implements QueryBuilderInterface<CrsseqDTO.Req> {
    private NativeSearchQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public CrsseqQueryBuilder() {
        this.searchQueryBuilder = new NativeSearchQueryBuilder();
    }

    @Override
    public void createQuery(CrsseqDTO.Req req) {
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

    private void setFields(CrsseqDTO.Req req) {
    }

    private void setSorting(CrsseqDTO.Req req) {
    }

    private void setAggregation(CrsseqDTO.Req req) {
    }

    private void setFilters(CrsseqDTO.Req req) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (req.getKeywords() != null && !req.getKeywords().isEmpty()) {
            boolQueryBuilder.should(KeywordsMatchFilter.createFilter(req));
            boolQueryBuilder.should(KeywordsTermFilter.createFilter(req));
        }
        if (req.getEduType() != null && !req.getEduType().isEmpty()) {
            boolQueryBuilder.filter(EduTypeFilter.createFilter(req));
        }
        this.searchQueryBuilder.withQuery(boolQueryBuilder);
    }

    private void setPageOffset(CrsseqDTO.Req req) {
        this.pageRequest = PageRequest.of(req.getPageNumber(), req.getPageSize());
        this.searchQueryBuilder.withPageable(this.pageRequest);
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }
}
