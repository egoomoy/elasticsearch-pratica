package io.elasticsearch.pratica.crsseq.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;

public class CrsseqDTO {
    @Getter
    @Setter
    public static class Item {
        private Long id;
        private String title;
        private String tags;
        private String eduType;
    }

    @Getter
    @FieldNameConstants
    public static class Req {
        private Long id;
        private String title;
        private String tags;
        private String keywords;
        private String eduType;
    }

    @Getter
    @Setter
    @Builder
    public static class Res {
        private Long id;
        private String title;
        private String tags;
        private String eduType;
    }

    @Getter
    @FieldNameConstants
    @Builder
    public static class SearchReq {
        private String type;
        private String keywords;
        private String eduType;
        private Integer pageNumber = 1;
        private Integer pageSize = 5;
    }

}
