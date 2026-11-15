package com.carservice.telemetry.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration // 1. Аннотация конфигурации переехала наверх, над классом
public class KafkaTopicConfig {

    @Bean // 2. Эта аннотация говорит Спрингу: "Запусти этот метод и сохрани результат в свой контекст"
    public NewTopic telemetryTopic() { // 3. Тип возвращаемого значения изменился на NewTopic
        return TopicBuilder.name("vehicle-telemetry")
                .partitions(3) // Разделяем топик на 3 части для параллельной обработки
                .build();
    }
}
