package io.elasticsearch.pratica.crsseq.model.repository.elasticsearch.Impl;

import io.elasticsearch.pratica.crsseq.model.document.CrsseqDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.Map;

@RequiredArgsConstructor
public class CrsseqDocumentRepositoryImpl {
    private final ElasticsearchOperations operations;

    public XContentBuilder getMappingBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("title");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                    builder.field("copy_to", "keywords");
                }
                builder.endObject();

                builder.startObject("tags");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                    builder.field("copy_to", "keywords");
                }
                builder.endObject();

                builder.startObject("keywords");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "my_analyzer");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }

    public XContentBuilder getSettingsBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("number_of_shards", 1)
                .field("number_of_replicas", 1)
                .startObject("analysis")
                .startObject("filter")
                .startObject("my_pos_f")
                .field("type", "nori_part_of_speech")
                .startArray("stoptags")
                .value("J")
                .value("VV")
                .value("MAG")
                .value("E")
                .value("VX")
                .value("XSV")
                .value("XSN")
                .endArray()
                .endObject()
                .endObject()
                .startObject("analyzer")
                .startObject("korean")
                .field("type", "nori")
                .field("stopwords", "_korean_")
                .endObject()
                .startObject("my_analyzer")
                .field("type", "custom")
                .field("tokenizer", "nori_user_dict")
                .startArray("filter")
                .value("my_pos_f")
                .endArray()
                .endObject()
                .endObject()
                .startObject("tokenizer")
                .startObject("nori_user_dict")
                .field("type", "nori_tokenizer")
                .field("decompound_mode", "mixed") //원어민=> 원어 + 민으로 구성되는데, mixed는 둘 다 검색, none은 원어민만 아마도 합성어를 나누는 기준일 듯 네이버에서도 원어민은 원어민으로 만 검색됨
                .field("user_dictionary", "userdict_ko.txt")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        return builder;
    }

    public SearchPage<CrsseqDocument> termAndMatchFind(String index, Map<String, Object> searchParam, Pageable pageable) throws Exception {
        String searchKey = searchParam.keySet().iterator().next();

        NativeSearchQueryBuilder searchQueryBuilder;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // create a term query with minimumShouldMatch
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.termQuery(searchKey, searchParam.get(searchKey)))
                        .should(QueryBuilders.matchQuery(searchKey, searchParam.get(searchKey))
                                .operator(org.elasticsearch.index.query.Operator.OR).minimumShouldMatch("50%")))
                .withPageable(PageRequest.of(pageable.getPageNumber(),pageable.getPageSize()))
                .build();

        SearchHits<CrsseqDocument> results = operations.search(searchQuery, CrsseqDocument.class);
        SearchPage<CrsseqDocument> CrsseqDocuments = SearchHitSupport.searchPageFor(results, searchQuery.getPageable());

//        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .should(QueryBuilders.termQuery(searchKey, searchParam.get(searchKey)))
//                .should(QueryBuilders.matchQuery(searchKey, searchParam.get(searchKey))
//                        .minimumShouldMatch("50%").operator(Operator.OR));
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(queryBuilder);
//        searchSourceBuilder.from(pageable.getPageNumber());
//        searchSourceBuilder.size(pageable.getPageSize());
//
//        SearchRequest searchRequest = new SearchRequest(index);
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHits searchHits = response.getHits();
//
//        Page<CrsseqDocument> rtnDocs = (Page<CrsseqDocument>) new ArrayList<CrsseqDocument>();
//
////        for (SearchHit hit : searchHits) {
////            Map<String, Object> sourceMap = hit.getSourceAsMap();
////            CrsseqDocument document = new CrsseqDocument();
////            document.setId(Long.parseLong((String) sourceMap.get("id")));
////            document.setTitle((String) sourceMap.get("title"));
////            document.setTags((String) sourceMap.get("tags"));
////            rtnDocs.add(document);
////        }
//
////        List<CrsseqDocument> documents = Arrays.stream(response.getHits().getHits())
////                .map(hit -> {
////                    Map<String, Object> source = hit.getSourceAsMap();
////                    CrsseqDocument document = new CrsseqDocument();
////                    document.setId((String) sourceMap.get("id"));
////                    document.setTitle((String) source.get("title"));
////                    document.setTags((String) source.get("tags"));
////                    return document;
////                })
////                .collect(Collectors.toList());

        return CrsseqDocuments;
    }

}
