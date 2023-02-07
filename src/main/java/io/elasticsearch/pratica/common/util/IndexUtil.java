package io.elasticsearch.pratica.common.util;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import java.time.Instant;

public class IndexUtil {
    public static IndexCoordinates createIndexNameWithPostFixWrapper(String indexName) {
        return IndexCoordinates.of(indexName + "-" + Instant.now().toEpochMilli());
    }

    public static IndexCoordinates createIndexNameWrapper(String indexName) {
        return IndexCoordinates.of(indexName);
    }

}