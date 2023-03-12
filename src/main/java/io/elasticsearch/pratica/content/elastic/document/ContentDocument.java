package io.elasticsearch.pratica.content.elastic.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

// 도큐멘트 이름 정의, 자동생성은 안함, index에 패키지명~클래스명 포함하지 않음
@Document(indexName = "el_content", createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
@Getter
@Setter
public class ContentDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String tags;
    @Field(type = FieldType.Text)
    private String eduType;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime crsregStrtdt;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime crsregEnddt;
}
