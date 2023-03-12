package io.elasticsearch.pratica.crsseq.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity(name = "TB_CRSSEQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Crsseq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crsseq_id")
    private Long id;
    @Column
    private String title;
    @Column
    private String tags;
    @Column
    private String eduType;
    @Column
    private LocalDateTime crsregStrtdt;
    @Column
    private LocalDateTime crsregEnddt;

    @Builder
    public Crsseq(String title, String tags, String eduType, LocalDateTime crsregStrtdt, LocalDateTime crsregEnddt) {
        this.title = title;
        this.tags = tags;
        this.eduType = eduType;
        this.crsregStrtdt = crsregStrtdt;
        this.crsregEnddt = crsregEnddt;
    }
}
