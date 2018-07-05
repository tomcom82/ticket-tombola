package com.amkob.tickettombola.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Tombola {

    @Getter
    @Setter
    private String name;
}
