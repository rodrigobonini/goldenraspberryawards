package com.goldenraspberry.service;

import com.goldenraspberry.domain.entity.MovieEntity;
import com.goldenraspberry.domain.repository.MovieRepository;
import com.goldenraspberry.helper.Constants;
import com.goldenraspberry.rest.dto.ProducerAwardDTO;
import com.goldenraspberry.rest.dto.ProducerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@AllArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public List<ProducerAwardDTO> getProducerAwards() {
        List<MovieEntity> moviesWinner = movieRepository.findByWinnerTrueOrderByProducerAscYearAsc();
        List<ProducerDTO> producers = getProducers(moviesWinner);
        return buildProducerAwards(producers);
    }

    private List<ProducerDTO> getProducers(List<MovieEntity> moviesWinner) {
        long startTime = System.currentTimeMillis();
        List<ProducerDTO> producers = new ArrayList<>();
        moviesWinner.forEach(movieEntity -> splitProducers(producers, movieEntity));
        producers.sort(Comparator.comparing(ProducerDTO::getProducer).thenComparing(ProducerDTO::getYear));

        log.debug("#MovieService.getProducers | took {} ms", System.currentTimeMillis() - startTime);
        return producers;
    }

    private void splitProducers(List<ProducerDTO> producers, MovieEntity movieEntity) {
        long startTime = System.currentTimeMillis();
        String[] splits = movieEntity.getProducer().split(Constants.REGEX_SPLIT_PRODUCER);
        for (String split : splits) {
            producers.add(ProducerDTO.builder()
                    .producer(split)
                    .year(movieEntity.getYear())
                    .build());
        }
        log.debug("#MovieService.splitProducers | took {} ms", System.currentTimeMillis() - startTime);
    }

    private List<ProducerAwardDTO> buildProducerAwards(List<ProducerDTO> producersWinner) {
        long startTime = System.currentTimeMillis();
        List<ProducerAwardDTO> producers = new ArrayList<>();
        AtomicReference<ProducerAwardDTO> lastProducer = new AtomicReference<>();

        producersWinner.forEach(movieEntity -> {
            ProducerAwardDTO producerAwardDTO = lastProducer.get();
            if (producerAwardDTO != null && movieEntity.getProducer().equalsIgnoreCase(producerAwardDTO.getProducer())) {
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
