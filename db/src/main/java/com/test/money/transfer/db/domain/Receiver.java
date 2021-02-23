package com.test.money.transfer.db.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class Receiver extends Person implements Serializable {
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private final String id = UUID.randomUUID().toString();
}
