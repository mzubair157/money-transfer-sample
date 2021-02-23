package com.test.money.transfer.db.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Currency;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExchangeRate implements Serializable {
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private final String id = UUID.randomUUID().toString();

    private Currency source;
    private Currency target;

    private Double rate;
}
