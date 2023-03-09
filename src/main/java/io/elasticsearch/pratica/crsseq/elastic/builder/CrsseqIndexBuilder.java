package io.elasticsearch.pratica.crsseq.elastic.builder;

import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

@Component
public class CrsseqIndexBuilder {
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
                    // 정확한 데이터를 검색하고 저장하기 위해서는 keyword 타입으로?
//                    builder.startObject("fields");
//                    builder.startObject("fulltext"); // 하위 필드이기 때문에 마음대로 지정 가능
//                    builder.field("type", "keyword");
//                    builder.field("ignore_Above", "200");
//                    builder.endObject();
//                    builder.endObject();
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

                builder.startObject("crsregStrtdt");
                {
                    builder.field("type", "date");
                    builder.field("format", "yyyy-MM-dd HH:mm:ss");
                }
                builder.endObject();

                builder.startObject("crsregEnddt");
                {
                    builder.field("type", "date");
                    builder.field("format", "yyyy-MM-dd HH:mm:ss");
                }
                builder.endObject();

            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }

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
                .value("VX")
                .value("XSV")
                .value("XSN")
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
                .field("decompound_mode", "mixed") //원어민=> 원어 + 민으로 구성되는데, mixed는 둘 다 검색, none은 원어민만 아마도 합성어를 나누는 기준일 듯 네이버에서도 원어민은 원어민으로 만 검색됨
                .field("user_dictionary", "userdict_ko.txt")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        return builder;
    }
}
