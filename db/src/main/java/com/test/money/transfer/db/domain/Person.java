package com.test.money.transfer.db.domain;

import com.test.money.transfer.db.domain.enums.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode
public class Person {
    private String govtIdentificationNumber;
    private String fullName;
    private String mobileNumber;
    private Country country;
}
