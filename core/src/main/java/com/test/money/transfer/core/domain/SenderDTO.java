package com.test.money.transfer.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SenderDTO {
    private String govtIdentificationNumber;
    private String fullName;
    private String mobileNumber;
}
