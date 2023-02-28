package io.elasticsearch.pratica.common.elasticsearch.Impl;

import io.elasticsearch.pratica.common.elasticsearch.repository.CustomElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class BaseElasticSearchRepositoryImpl<T> implements CustomElasticsearchRepository<T> {
    // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.17/java-rest-high-create-index.html
    private final ElasticsearchOperations operations;
    private final RestHighLevelClient restHighLevelClient;

    @Override
    public <S extends T> S save(S entity, IndexCoordinates indexName) {
        return operations.save(entity, indexName);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities, IndexCoordinates indexName) {
        return operations.save(entities, indexName);
    }

    @Override
    public boolean setAlias(IndexCoordinates indexNameWrapper, IndexCoordinates aliasNameWrapper) {
        IndexOperations indexOperations = operations.indexOps(indexNameWrapper);
        AliasActions aliasActions = new AliasActions();
        aliasActions.add(new AliasAction.Add(AliasActionParameters.builder()
                .withIndices(indexOperations.getIndexCoordinates().getIndexNames())
                .withAliases(aliasNameWrapper.getIndexName())
                .build()));
        return indexOperations.alias(aliasActions);
    }

    @Override
    public Set<String> findIndexNamesByAlias(IndexCoordinates aliasNameWrapper) {
        IndexOperations indexOperations = operations.indexOps(aliasNameWrapper);
        return indexOperations.getAliasesForIndex(aliasNameWrapper.getIndexName()).keySet();
    }

    @Override
    public boolean deleteIndex(IndexCoordinates indexNameWrapper) {
        IndexOperations indexOperations = operations.indexOps(indexNameWrapper);
        return indexOperations.delete();
    }

    @Override
    public boolean createIndex(String indexName, XContentBuilder settingsBuilder, XContentBuilder mappingBuilder) throws Exception {
        boolean acknowledged = false;
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(settingsBuilder);
        request.mapping(mappingBuilder);
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        response.isAcknowledged();
        return acknowledged;
    }
}
