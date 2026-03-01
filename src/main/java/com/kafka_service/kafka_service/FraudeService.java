package com.kafka_service.kafka_service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class FraudeService {
    private static final int TTL = 5;

    private final RedisTemplate<String, String> redisTemplate;

    public FraudeService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isFraude(TransacaoDto transacaoDto) {
        var agora = OffsetDateTime.now();
        var agoraMenosTtl = agora.minusMinutes(TTL);

        // Ignorar mensagens do passdo
        if (transacaoDto.dataHora().isBefore(agoraMenosTtl)) {
            return false;
        }

        // Validar se existe a chave no redis
        if (redisTemplate.opsForValue().get(transacaoDto.id()) != null) {
            atualizaCache(agoraMenosTtl, transacaoDto);
            System.out.println("Eh fraude");
            return true;
        }

        atualizaCache(agoraMenosTtl, transacaoDto);

        return false;
    }

    private void atualizaCache(OffsetDateTime agoraMenosTtl, TransacaoDto transacaoDto) {
        var diferencaEmSegundos = ChronoUnit.SECONDS.between(agoraMenosTtl, transacaoDto.dataHora());
        redisTemplate.opsForValue().set(transacaoDto.id(), transacaoDto.toString(), diferencaEmSegundos, TimeUnit.SECONDS);
    }
}
