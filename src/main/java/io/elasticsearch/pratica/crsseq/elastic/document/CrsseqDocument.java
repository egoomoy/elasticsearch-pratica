package io.elasticsearch.pratica.crsseq.elastic.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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
    @Field(type = FieldType.Text)
    private String eduType;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crsregStrtdt;
    @Field(type = FieldType.Date, format = DateFormat.custom , pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crsregEnddt;
}
