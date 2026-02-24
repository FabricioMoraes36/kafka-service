package com.kafka_service.kafka_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FraudeServiceTest {

    @Test
    void checkIfIsFraude_retornaFalse_QuandoNaoExisteFraude() {
        Map<String, LocalDateTime> chavesExistentes = new HashMap<>();
        var service = new FraudeService(chavesExistentes);
        String chave = "1";

        assertFalse(service.isFraude(chave));
        assertTrue(chavesExistentes.containsKey("1"));
    }

    @Test
    void checkIfIsFraude_retornaTrue_QuandoExisteFraude() {
        Map<String, LocalDateTime> chavesExistentes = new HashMap<>();
        var service = new FraudeService(chavesExistentes);
        String chave = "1";

        LocalDateTime antigoDateTime = LocalDateTime.now().minusMinutes(3);

        chavesExistentes.put(chave, antigoDateTime);

        assertTrue(service.isFraude(chave));
        assertNotEquals(chavesExistentes.get(chave), antigoDateTime);
    }


    @ParameterizedTest
    @ValueSource(ints = {5, 6 ,7})
    void checkIfIsFraude_retornaFalse_QuandoOhTempoForMaiorQueOhTtl(Integer minutos) {
        Map<String, LocalDateTime> chavesExistentes = new HashMap<>();
        var service = new FraudeService(chavesExistentes);
        String chave = "1";

        LocalDateTime antigoDateTime = LocalDateTime.now().minusMinutes(minutos);

        chavesExistentes.put(chave, antigoDateTime);

        assertFalse(service.isFraude(chave));
        assertNotEquals(chavesExistentes.get(chave), antigoDateTime);
    }
}
