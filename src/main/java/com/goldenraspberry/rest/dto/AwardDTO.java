package com.goldenraspberry.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class AwardDTO {

    private List<ProducerAwardDTO> min;
    private List<ProducerAwardDTO> max;
}
