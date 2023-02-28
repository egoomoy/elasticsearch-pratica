package io.elasticsearch.pratica.crsseq.service;

import io.elasticsearch.pratica.crsseq.elasticsearch.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import org.springframework.data.elasticsearch.core.SearchPage;


public interface CrsseqService {
    public boolean rollingCrsseqIndex() throws Exception;
    public Crsseq saveCrsseq(CrsseqDTO.Req reqCrsseq) throws Exception;
    public SearchPage<CrsseqDocument> getCrsseq(CrsseqDTO.SearchReq reqCrsseq) throws Exception;
}
