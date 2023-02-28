<<<<<<<< HEAD:src/main/java/io/elasticsearch/pratica/crsseq/elastic/document/CrsseqDocument.java
package io.elasticsearch.pratica.crsseq.elastic.document;
========
package io.elasticsearch.pratica.crsseq.elasticsearch.document;
>>>>>>>> 7d9e2d911fae614c3db5ba2cc30f15f6a8bdc458:src/main/java/io/elasticsearch/pratica/crsseq/elasticsearch/document/CrsseqDocument.java

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
