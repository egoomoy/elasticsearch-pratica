package io.elasticsearch.pratica.crsseq.model.elasticsearch.qurey;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

public interface QueryBuilderInterface {
    void createQuery(CrsseqDTO.SearchReq req);
    NativeSearchQuery getSearch();
}
