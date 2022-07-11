package com.goldenraspberry.rest.controller;

import com.goldenraspberry.rest.dto.AwardDTO;
import com.goldenraspberry.service.AwardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/award/")
public class AwardController {

    private final AwardService service;

    @GetMapping("/max-min")
    public ResponseEntity<AwardDTO> getProducerWithMaxAndMinRange() {
        log.debug("#AwardController.getProducerWithMaxAndMinRange | interval award max and min");
        AwardDTO awardDTO = service.getProducerWithMaxAndMinRange();

        return ResponseEntity.ok().body(awardDTO);
    }

}
