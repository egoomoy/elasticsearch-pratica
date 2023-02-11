package io.elasticsearch.pratica.crsseq.service;

import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;

public interface CrsseqService {
    public void rolloverCrsseqDoc() throws Exception;
    public  void saveCrsseq(CrsseqDTO crsseqDTO) throws Exception;
}
