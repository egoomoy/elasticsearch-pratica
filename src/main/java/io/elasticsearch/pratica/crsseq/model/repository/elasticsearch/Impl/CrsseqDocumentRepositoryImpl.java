package io.elasticsearch.pratica.crsseq.model.repository.elasticsearch.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

public class CrsseqDocumentRepositoryImpl {
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
                    builder.field("copy_to", "keywords");
                }
                builder.endObject();

                builder.startObject("tags");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                    builder.field("copy_to", "keywords");
                }
                builder.endObject();

                builder.startObject("keywords");
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
