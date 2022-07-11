package com.goldenraspberry.rest.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ProducerDTO {

    private String producer;
    private Integer year;
}
