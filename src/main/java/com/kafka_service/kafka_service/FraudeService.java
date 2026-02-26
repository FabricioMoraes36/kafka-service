package com.kafka_service.kafka_service;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FraudeService {

    private final RedisCacheManager cacheManager;

    public FraudeService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean isFraude(TransacaoDto transacao) {

        Cache cache = cacheManager.getCache("transacoesCache");

        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime ultimaTransacao = cache.get(transacao.id(), OffsetDateTime.class);

        if (ultimaTransacao == null) {
            cache.put(transacao.id(), agora);
            return false;
        }

        if (agora.isBefore(ultimaTransacao.plusSeconds(10))) {
            System.out.println("Possivel fraude para o ID: " + transacao.id());
            return true;
        }

        cache.put(transacao.id(), agora);
        return false;
    }
}
