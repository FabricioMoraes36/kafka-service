package com.kafka_service.kafka_service;

import java.time.OffsetDateTime;

public record TransacaoDTO(String id, Integer valor, String idCliente, OffsetDateTime data) {
}
