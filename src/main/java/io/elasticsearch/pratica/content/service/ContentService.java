package io.elasticsearch.pratica.content.service;

import io.elasticsearch.pratica.content.elastic.document.ContentDocument;
import io.elasticsearch.pratica.content.model.dto.ContentDTO;
import io.elasticsearch.pratica.content.model.entity.Content;
import org.springframework.data.elasticsearch.core.SearchPage;


public interface ContentService {
    public boolean rollingContentIndex() throws Exception;
    public Content saveContent(ContentDTO.Req reqContent) throws Exception;
    public SearchPage<ContentDocument> getContent(ContentDTO.Req reqContent) throws Exception;
}
