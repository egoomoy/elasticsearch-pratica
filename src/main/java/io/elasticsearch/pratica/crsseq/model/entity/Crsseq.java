package io.elasticsearch.pratica.crsseq.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Getter
@Entity(name="TB_CRSSEQ")
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
    @Builder
    public Crsseq(String title, String tags) {
        this.title = title;
        this.tags = tags;
    }
}
