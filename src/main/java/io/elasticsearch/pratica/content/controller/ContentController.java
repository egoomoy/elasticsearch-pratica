package io.elasticsearch.pratica.content.controller;

import io.elasticsearch.pratica.content.elastic.document.ContentDocument;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import io.elasticsearch.pratica.content.model.entity.Content;
import io.elasticsearch.pratica.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;


@RequiredArgsConstructor
@RestController
public class ContentController {
    private final ContentService contentService;

    @GetMapping(value = "/rollingContentIndex")
    public String loginOut() throws Exception {
        contentService.rollingContentIndex();
        return "rolling";
    }

    @PostMapping("/content")
    public ResponseEntity saveContent(@RequestBody ContentDTO.Req reqContent) throws Exception {
        Content contentItem = contentService.saveContent(reqContent);
        ContentDTO.Res resContent = ContentDTO.Res.builder()
                .id(contentItem.getId())
                .title(contentItem.getTitle())
                .tags(contentItem.getTags())
                .eduType(contentItem.getEduType())
                .crsregEnddt(contentItem.getCrsregEnddt())
                .crsregStrtdt(contentItem.getCrsregStrtdt())
                .build();
        return new ResponseEntity(resContent, HttpStatus.OK);
    }

    @GetMapping(value = "/content")
    public ResponseEntity getContent( ContentDTO.Req reqContent) throws Exception {
        SearchPage<ContentDocument> contentDocs = contentService.getContent(reqContent);

        Iterator<SearchHit<ContentDocument>> iterator = contentDocs.iterator();
        while (iterator.hasNext()) {
            ContentDocument contentDocument = iterator.next().getContent();
            System.out.println(contentDocument.getTitle());
        }

//        ContentDTO.pageRes rtnDocs = ContentDTO.pageRes.builder()
//                .content(contentDocs.getSearchHits())
//                .build();


//        List<ContentDTO.Res> resContents = contentDocs.stream().map(contentDoc -> {
//            ContentDTO.Res resContent = ContentDTO.Res.builder()
//                    .id(contentDoc.getId())
//                    .title(contentDoc.getTitle())
//                    .tags(contentDoc.getTags()).build();
//            return resContent;
//        }).collect(Collectors.toList());
        return new ResponseEntity(contentDocs, HttpStatus.OK);
    }
}
