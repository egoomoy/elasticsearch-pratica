package io.elasticsearch.pratica.content.service.impl;

import io.elasticsearch.pratica.content.elastic.document.ContentDocument;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import io.elasticsearch.pratica.content.model.entity.Content;
import io.elasticsearch.pratica.content.service.ContentService;
import org.springframework.data.elasticsearch.core.SearchPage;

/*
@Service 를 사용안하는 쪽에 지워주면 주입 변환 가능
*/
public class TestContentServiceImpl implements ContentService {
    @Override
    public boolean rollingContentIndex() throws Exception {
        return false;
    }

    @Override
    public Content saveContent(ContentDTO.Req reqContent) throws Exception {
        return null;
    }

    @Override
    public SearchPage<ContentDocument> getContent(ContentDTO.Req reqContent) throws Exception {
        return null;
    }
}
