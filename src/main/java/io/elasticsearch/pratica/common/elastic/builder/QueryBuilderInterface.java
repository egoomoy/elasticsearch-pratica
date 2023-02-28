package io.elasticsearch.pratica.common.elastic.builder;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

public interface QueryBuilderInterface<T> {
    void createQuery(T t);
    NativeSearchQuery getSearch();
}
