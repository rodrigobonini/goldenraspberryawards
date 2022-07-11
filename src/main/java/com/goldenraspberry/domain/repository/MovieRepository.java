package com.goldenraspberry.domain.repository;

import java.util.List;

import com.goldenraspberry.domain.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByWinnerTrueOrderByProducerAscYearAsc();
}
