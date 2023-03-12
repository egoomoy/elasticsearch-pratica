package io.elasticsearch.pratica.content.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

public class ContentDTO {
    @Getter
    @Setter
    public static class Item {
        private Long id;
        private String title;
        private String tags;
        private String eduType;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregStrtdt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregEnddt;
    }

    @Getter
    @FieldNameConstants
    public static class Req {
        private Long id;
        private String title;
        private String tags;
        private String keywords;
        private String eduType;
        private Integer pageNumber = 0;
        private Integer pageSize = 5;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregStrtdt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregEnddt;
    }

    @Getter
    @Builder
    public static class Res {
        private Long id;
        private String title;
        private String tags;
        private String eduType;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregStrtdt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime crsregEnddt;
    }

}
