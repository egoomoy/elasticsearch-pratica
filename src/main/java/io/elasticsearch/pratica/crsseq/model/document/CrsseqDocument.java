package io.elasticsearch.pratica.crsseq.model.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 도큐멘트 이름 정의, 자동생성은 안함, index에 패키지명~클래스명 포함하지 않음
@Document(indexName = "el_crsseq", createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
@NoArgsConstructor
@AllArgsConstructor
public class CrsseqDocument {
        @Id
        private String id;
        @Field(type = FieldType.Text)
        private String title;
        @Field(type = FieldType.Text)
        private String tags;
}
