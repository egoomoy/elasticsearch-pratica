package io.elasticsearch.pratica.content.elastic.builder;

import io.elasticsearch.pratica.common.elastic.builder.QueryBuilderInterface;
import io.elasticsearch.pratica.content.elastic.filters.EduTypeFilter;
import io.elasticsearch.pratica.content.elastic.filters.KeywordsTermFilter;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import io.elasticsearch.pratica.content.elastic.filters.KeywordsMatchFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class ContentQueryBuilder implements QueryBuilderInterface<ContentDTO.Req> {
    private NativeSearchQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public ContentQueryBuilder() {
        this.searchQueryBuilder = new NativeSearchQueryBuilder();
    }

    @Override
    public void createQuery(ContentDTO.Req req) {
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

    private void setFields(ContentDTO.Req req) {
    }

    private void setSorting(ContentDTO.Req req) {
    }

    private void setAggregation(ContentDTO.Req req) {
    }

    private void setFilters(ContentDTO.Req req) {
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

    private void setPageOffset(ContentDTO.Req req) {
        this.pageRequest = PageRequest.of(req.getPageNumber(), req.getPageSize());
        this.searchQueryBuilder.withPageable(this.pageRequest);
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }
}
