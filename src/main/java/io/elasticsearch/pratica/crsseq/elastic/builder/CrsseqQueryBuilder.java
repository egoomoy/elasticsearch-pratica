<<<<<<<< HEAD:src/main/java/io/elasticsearch/pratica/crsseq/elastic/builder/CrsseqQueryBuilder.java
package io.elasticsearch.pratica.crsseq.elastic.builder;

import io.elasticsearch.pratica.common.elastic.builder.QueryBuilderInterface;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.elastic.filters.KeywordsFilter;
========
package io.elasticsearch.pratica.crsseq.elasticsearch.builder;

import io.elasticsearch.pratica.common.elasticsearch.qurey.QueryBuilderInterface;
import io.elasticsearch.pratica.crsseq.elasticsearch.filters.KeywordsFilter;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
>>>>>>>> 7d9e2d911fae614c3db5ba2cc30f15f6a8bdc458:src/main/java/io/elasticsearch/pratica/crsseq/elasticsearch/builder/CrsseqQueryBuilder.java
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
