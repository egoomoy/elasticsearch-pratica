package io.elasticsearch.pratica.crsseq.model.elasticsearch;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

public interface QueryBuilderInterface {
    void createQuery(CrsseqDTO.Req req);
    NativeSearchQuery getSearch();
}
