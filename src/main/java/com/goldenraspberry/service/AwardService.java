package com.goldenraspberry.service;

import com.goldenraspberry.rest.dto.AwardDTO;
import com.goldenraspberry.rest.dto.ProducerAwardDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class AwardService {

    private final MovieService movieService;

    public AwardDTO getProducerWithMaxAndMinRange() {
        long startTime = System.currentTimeMillis();
        List<ProducerAwardDTO> producers = movieService.getProducerAwards();
        log.debug("#AwardService.getProducerWithMaxAndMinRange | took {} ms", System.currentTimeMillis() - startTime);
        return buildAwardResponse(producers);
    }

   private AwardDTO buildAwardResponse(List<ProducerAwardDTO> producers) {
       List<ProducerAwardDTO> minProducers = getProducersWithMinRange(producers);
       List<ProducerAwardDTO> maxProducers = getProducersWithMaxRange(producers);

       return AwardDTO.builder()
                .min(minProducers)
                .max(maxProducers)
                .build();
    }

    private List<ProducerAwardDTO> getProducersWithMinRange(List<ProducerAwardDTO> producers) {
        ProducerAwardDTO minProducerAward = producers.stream()
                .min(Comparator.comparing(ProducerAwardDTO::getInterval))
                .orElse(ProducerAwardDTO.builder().build());

        return producers.stream()
                 .filter(producerAwardDTO -> producerAwardDTO.getInterval() == minProducerAward.getInterval())
                 .collect(Collectors.toList());
    }

    private List<ProducerAwardDTO> getProducersWithMaxRange(List<ProducerAwardDTO> producers) {
        ProducerAwardDTO maxProducerAward = producers.stream()
                .max(Comparator.comparing(ProducerAwardDTO::getInterval))
                .orElse(ProducerAwardDTO.builder().build());

        return producers.stream()
                .filter(producerAwardDTO -> producerAwardDTO.getInterval() == maxProducerAward.getInterval())
                .collect(Collectors.toList());
    }

}
