package io.elasticsearch.pratica.content.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity(name = "tb_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
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
    public Content(String title, String tags, String eduType, LocalDateTime crsregStrtdt, LocalDateTime crsregEnddt) {
        this.title = title;
        this.tags = tags;
        this.eduType = eduType;
        this.crsregStrtdt = crsregStrtdt;
        this.crsregEnddt = crsregEnddt;
    }
}
