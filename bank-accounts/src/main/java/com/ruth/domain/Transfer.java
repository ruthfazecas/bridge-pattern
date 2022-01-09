package com.ruth.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Transfer {
    private final long serialNumber = currentStaticSerialNumber.incrementAndGet();
    private static final AtomicLong currentStaticSerialNumber = new AtomicLong(-1);

    private final long sourceSerialNumber;
    private final long destinationSerialNumber;

    private final BigDecimal amount;
}
