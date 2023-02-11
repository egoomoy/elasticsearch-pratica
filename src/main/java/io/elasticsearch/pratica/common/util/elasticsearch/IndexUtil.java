package io.elasticsearch.pratica.common.util.elasticsearch;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import javax.annotation.Resources;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class IndexUtil {
    private final RestHighLevelClient restHighLevelClient;
    public IndexCoordinates createIndexNameWithPostFixWrapper(String indexName) {
        return IndexCoordinates.of(indexName + "-" + Instant.now().toEpochMilli());
    }

    public IndexCoordinates createIndexNameWrapper(String indexName) {
        return IndexCoordinates.of(indexName);
    }

    public Boolean createIndex(String indexName, XContentBuilder settingsBuilder, XContentBuilder mappingBuilder) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        return _createIndex(createIndexRequest,indexName,settingsBuilder,mappingBuilder);
    }

    private Boolean _createIndex(CreateIndexRequest createIndexRequest, String indexName, XContentBuilder settingsBuilder, XContentBuilder mappingBuilder) {
        boolean acknowledged = false;
        // 세팅 정보
        createIndexRequest.settings(settingsBuilder);
        // 매핑 정보
        createIndexRequest.mapping(mappingBuilder);
        // 인덱스 생성
        CreateIndexResponse createIndexResponse;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            acknowledged = createIndexResponse.isAcknowledged();
        } catch (Exception e) {
        }
        if(acknowledged)
            System.out.println(indexName + " 인덱스가 생성되었습니다.");
        else
            System.out.println(indexName + " 인덱스 생성을 실패했습니다.");
        return acknowledged;
    }
}