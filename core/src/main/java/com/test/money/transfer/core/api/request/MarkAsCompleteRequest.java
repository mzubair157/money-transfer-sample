package com.test.money.transfer.core.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class MarkAsCompleteRequest implements Serializable {
    private String id;
}
