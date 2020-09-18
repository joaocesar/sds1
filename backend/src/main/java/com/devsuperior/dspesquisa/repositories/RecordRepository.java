package com.devsuperior.dspesquisa.repositories;

import com.devsuperior.dspesquisa.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT rec from Record rec WHERE " +
            "(coalesce(:minDate, null) IS NULL OR rec.moment >= :minDate) AND " +
            "(coalesce(:maxDate, null) IS NULL OR rec.moment <= :maxDate)")
    Page<Record> findByMoments(Instant minDate, Instant maxDate, Pageable pageable);
}
