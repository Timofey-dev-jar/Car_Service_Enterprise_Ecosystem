package com.carservice.notifier.service;

import org.springframework.data.redis.core.StringRedisTemplate; // Правильный импорт
import org.springframework.stereotype.Service;

@Service
public class ErrorCodeCacheService {

    // Это наш главный рабочий инструмент для связи с Redis
    private final StringRedisTemplate redisTemplate;

    // Спринг сам создаст StringRedisTemplate и передаст его сюда в конструктор
    public ErrorCodeCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    } 

    // Метод для записи кода ошибки и её описания в Redis
    public void saveErrorDescription(String code, String description) {
        redisTemplate.opsForValue().set(code, description);
        System.out.println(" [Redis Cache] Успешно сохранено: " + code + " -> " + description);
    }

    // Метод для мгновенного чтения описания ошибки по её коду
    public String getErrorDescription(String code) {
        String description = redisTemplate.opsForValue().get(code);
        
        // Если в кэше такой ошибки еще нет, вернем заглушку "Неизвестная ошибка"
        return description != null ? description : "Неизвестный код ошибки OBD2";
    }
}
