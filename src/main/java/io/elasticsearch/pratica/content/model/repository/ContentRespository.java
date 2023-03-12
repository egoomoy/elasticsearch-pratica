package io.elasticsearch.pratica.content.model.repository;

import io.elasticsearch.pratica.content.model.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRespository extends JpaRepository<Content, Long> {

}
