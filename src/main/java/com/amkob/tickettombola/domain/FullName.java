package com.amkob.tickettombola.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value

@Builder
public class FullName {
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
}
