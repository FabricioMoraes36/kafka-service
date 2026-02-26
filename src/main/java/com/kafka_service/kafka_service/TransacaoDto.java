package com.kafka_service.kafka_service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoDto(
        String id,
        String contaId,
        String cartaoId,
        BigDecimal valor,
        String comerciante,
        String localizacao,
        String tipoTransacao,
        OffsetDateTime dataHora
) {
}
