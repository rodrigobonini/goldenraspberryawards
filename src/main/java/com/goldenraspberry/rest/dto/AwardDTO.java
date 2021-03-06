package com.goldenraspberry.rest.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class AwardDTO {

    private List<ProducerAwardDTO> min;
    private List<ProducerAwardDTO> max;
}
