package io.elasticsearch.pratica.crsseq.service.impl;

import io.elasticsearch.pratica.crsseq.elastic.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import io.elasticsearch.pratica.crsseq.service.CrsseqService;
import org.springframework.data.elasticsearch.core.SearchPage;

/*
@Service 를 사용안하는 쪽에 지워주면 주입 변환 가능
*/
public class TestCrsseqServiceImpl implements CrsseqService {
    @Override
    public boolean rollingCrsseqIndex() throws Exception {
        return false;
    }

    @Override
    public Crsseq saveCrsseq(CrsseqDTO.Req reqCrsseq) throws Exception {
        return null;
    }

    @Override
    public SearchPage<CrsseqDocument> getCrsseq(CrsseqDTO.SearchReq reqCrsseq) throws Exception {
        return null;
    }
}
