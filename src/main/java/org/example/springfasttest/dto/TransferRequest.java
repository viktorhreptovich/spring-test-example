package org.example.springfasttest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    private long idSender;
    private long idReceiver;
    private BigDecimal amount;

}
