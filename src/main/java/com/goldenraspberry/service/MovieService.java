package com.goldenraspberry.service;

import com.goldenraspberry.domain.entity.MovieEntity;
import com.goldenraspberry.domain.repository.MovieRepository;
import com.goldenraspberry.rest.dto.AwardDTO;
import com.goldenraspberry.rest.dto.ProducerAwardDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public List<ProducerAwardDTO> getProducerAwards() {
        List<MovieEntity> producersWinner = movieRepository.findByWinnerTrueOrderByProducerAscYearAsc();
        return buildProducerAwards(producersWinner);
    }

    private List<ProducerAwardDTO> buildProducerAwards(List<MovieEntity> producersWinner) {
        long startTime = System.currentTimeMillis();
        List<ProducerAwardDTO> producers = new ArrayList<>();
        AtomicReference<ProducerAwardDTO> lastProducer = new AtomicReference<>();

        producersWinner.forEach(movieEntity -> {
            ProducerAwardDTO producerAwardDTO = lastProducer.get();
            if (producerAwardDTO != null && movieEntity.getProducer().equals(producerAwardDTO.getProducer())) {
                producerAwardDTO.setFollowingWin(movieEntity.getYear());
                producerAwardDTO.setInterval(movieEntity.getYear() - producerAwardDTO.getPreviousWin());
                producers.add(producerAwardDTO);
            }

            lastProducer.set(ProducerAwardDTO.builder()
                    .producer(movieEntity.getProducer())
                    .previousWin(movieEntity.getYear())
                    .build());
        });

        log.debug("#MovieService.buildProducerAwards | took {} ms", System.currentTimeMillis() - startTime);
        return producers;
    }

}
