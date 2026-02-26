package com.kafka_service.kafka_service;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FraudeService {

    private final RedisCacheManager cacheManager;

    public FraudeService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean isFraude(TransacaoDTO chave) {


        var cache = cacheManager.getCache("transacoesCache");
        OffsetDateTime agora = OffsetDateTime.now();

        if (cache.get(chave) == null) {
            cache.put(chave, agora);
            return false;
        }

        cacheManager.getCache("transacoesCache").put(chave, agora);
        return true;
    }
}
