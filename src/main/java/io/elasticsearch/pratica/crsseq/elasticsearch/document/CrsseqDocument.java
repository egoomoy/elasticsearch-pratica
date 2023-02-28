package io.elasticsearch.pratica.crsseq.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

import javax.persistence.Id;

// 도큐멘트 이름 정의, 자동생성은 안함, index에 패키지명~클래스명 포함하지 않음
@Document(indexName = "el_crsseq", createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
@Getter
@Setter
public class CrsseqDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String tags;
}
