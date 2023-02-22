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
    }

    @Getter
    @FieldNameConstants
    public static class Req {
        private Long id;
        private String title;
        private String tags;
        private String keywords;
    }

    @Getter
    @Setter
    @Builder
    public static class Res {
        private Long id;
        private String title;
        private String tags;
    }

//    @Getter
//    @Builder
//    public static class pageRes {
//        private List<Res> content;
//        private long totalElements;
//        private int totalPages;
//        private int pageNumber;
//        private int pageSize;
//    }

    @Getter
    @FieldNameConstants
    @Builder
    public static class SearchReq {
        private String type;
        private String keywords;
        private Integer pageNumber = 1;
        private Integer pageSize = 5;
    }

}
