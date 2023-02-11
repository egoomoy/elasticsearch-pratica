package io.elasticsearch.pratica.common.repository.elasticsearch.Impl;

import io.elasticsearch.pratica.common.repository.elasticsearch.BaseElasticSearchRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
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
public class BaseElasticSearchRepositoryImpl<T> implements BaseElasticSearchRepository<T> {

    private final ElasticsearchOperations operations;

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
    public XContentBuilder getSettingsBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("number_of_shards", 1)
                .field("number_of_replicas", 1)
                .startObject("analysis")
                .startObject("filter")
                .startObject("my_pos_f")
                .field("type", "nori_part_of_speech")
                .startArray("stoptags")
                .value("J")
                .value("VV")
                .value("MAG")
                .value("E")
                .endArray()
                .endObject()
                .endObject()
                .startObject("analyzer")
                .startObject("korean")
                .field("type", "nori")
                .field("stopwords", "_korean_")
                .endObject()
                .startObject("my_analyzer")
                .field("type", "custom")
                .field("tokenizer", "nori_user_dict")
                .startArray("filter")
                .value("my_pos_f")
                .endArray()
                .endObject()
                .endObject()
                .startObject("tokenizer")
                .startObject("nori_user_dict")
                .field("type", "nori_tokenizer")
                .field("decompound_mode", "none") //원어민=> 원어 + 민으로 구성되는데, mixed는 둘 다 검색, none은 원어민만 아마도 합성어를 나누는 기준일 듯 네이버에서도 원어민은 원어민으로 만 검색됨
                .field("user_dictionary", "userdict_ko.txt")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        return builder;
    }

    @Override
    public XContentBuilder getMappingBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("title");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                }
                builder.endObject();

                builder.startObject("content");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }
}
