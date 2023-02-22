package io.elasticsearch.pratica.crsseq.model.repository;

import io.elasticsearch.pratica.crsseq.model.entity.Crsseq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrsseqRespository extends JpaRepository<Crsseq, Long> {

}
