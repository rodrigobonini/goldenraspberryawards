package com.goldenraspberry.rest.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ProducerAwardDTO {

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

}
