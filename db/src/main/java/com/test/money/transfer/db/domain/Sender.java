package com.test.money.transfer.db.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class Sender extends Person {
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private final String id = UUID.randomUUID().toString();

    @Builder.Default
    private boolean verified = false;
}
