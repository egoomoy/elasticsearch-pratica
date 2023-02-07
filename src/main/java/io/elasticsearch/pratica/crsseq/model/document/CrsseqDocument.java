package io.elasticsearch.pratica.crsseq.model.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.springframework.data.elasticsearch.annotations.*;

// 도큐멘트 이름 정의, 자동생성은 안함, index에 패키지명~클래스명 포함하지 않음
@Document(indexName = "el_crsseq", createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrsseqDocument {
        @Id
        private String id;
        @Field(type = FieldType.Text)
        private String title;
        @Field(type = FieldType.Text)
        private String content;
}
