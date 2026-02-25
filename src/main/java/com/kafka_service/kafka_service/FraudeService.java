package com.kafka_service.kafka_service;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudeService {

    private final RedisCacheManager cacheManager;

    public FraudeService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean isFraude(String chave) {

        var cache = cacheManager.getCache("transacoesCache");
        LocalDateTime agora = LocalDateTime.now();

        if (cache.get(chave) == null) {
            cache.put(chave, agora);
            return false;
        }

        cacheManager.getCache("transacoesCache").put(chave, agora);
        return true;
    }
}
