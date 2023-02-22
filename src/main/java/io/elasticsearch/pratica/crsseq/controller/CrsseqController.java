package io.elasticsearch.pratica.crsseq.controller;

import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import io.elasticsearch.pratica.crsseq.model.dto.CrsseqDTO;
import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import io.elasticsearch.pratica.crsseq.service.CrsseqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;


@RequiredArgsConstructor
@RestController
public class CrsseqController {
    private final CrsseqService crsseqService;

    @GetMapping(value = "/rollingCrsseqIndex")
    public String loginOut() throws Exception {
        crsseqService.rollingCrsseqIndex();
        return "rolling";
    }

    @PostMapping("/crsseq")
    public ResponseEntity saveCrsseq(@RequestBody CrsseqDTO.Req reqCrsseq) throws Exception {
        Crsseq crsseq = crsseqService.saveCrsseq(reqCrsseq);
        CrsseqDTO.Res resCrsseq = CrsseqDTO.Res.builder()
                .id(crsseq.getId())
                .title(crsseq.getTitle())
                .tags(crsseq.getTags()).build();
        return new ResponseEntity(resCrsseq, HttpStatus.OK);
    }

    @GetMapping(value = "/crsseq")
    public ResponseEntity getCrsseq(CrsseqDTO.SearchReq reqCrsseq) throws Exception {
        SearchPage<CrsseqDocument> crsseqDocs = crsseqService.getCrsseq(reqCrsseq);

        Iterator<SearchHit<CrsseqDocument>> iterator = crsseqDocs.iterator();
        while (iterator.hasNext()) {
            CrsseqDocument crsseqDocument = iterator.next().getContent();
            System.out.println(crsseqDocument.getTitle());
        }

//        CrsseqDTO.pageRes rtnDocs = CrsseqDTO.pageRes.builder()
//                .content(crsseqDocs.getSearchHits())
//                .build();


//        List<CrsseqDTO.Res> resCrsseqs = crsseqDocs.stream().map(crsseqDoc -> {
//            CrsseqDTO.Res resCrsseq = CrsseqDTO.Res.builder()
//                    .id(crsseqDoc.getId())
//                    .title(crsseqDoc.getTitle())
//                    .tags(crsseqDoc.getTags()).build();
//            return resCrsseq;
//        }).collect(Collectors.toList());
        return new ResponseEntity(crsseqDocs, HttpStatus.OK);
    }
}
