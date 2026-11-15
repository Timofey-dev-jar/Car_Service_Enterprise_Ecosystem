package com.carservice.notifier.service;

import com.carservice.notifier.dto.VehicleTelemetryDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TelemetryConsumer {

    // 1. Подключаем наш сервис кэширования Redis как рабочий инструмент
    private final ErrorCodeCacheService cacheService;

    // 2. Спринг автоматически внедрит его сюда через конструктор
    public TelemetryConsumer(ErrorCodeCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @KafkaListener(topics = "vehicle-telemetry", groupId = "notifier-group")
    public void listenTelemetry(VehicleTelemetryDto dto) {
        System.out.println(" [Notifier OS] Получено сообщение из Kafka! Проверяем состояние авто...");
        System.out.println("VIN машины: " + dto.vin());
        System.out.println("Пробег: " + dto.mileage() + " км, Остаток топлива: " + dto.fuelLevel() + "%");
        System.out.println("Время фиксации: " + dto.time());
        
        // 3. Красиво расшифровываем коды ошибок с помощью Redis
        System.out.println("Обнаруженные неисправности:");
        if (dto.obd2Errors().isEmpty()) {
            System.out.println(" -> Ошибок бортового компьютера не обнаружено. Всё отлично!");
        } else {
            // Перебираем каждый код ошибки в цикле
            for (String error : dto.obd2Errors()) {
                // Достаем текстовое описание из оперативной памяти Redis по коду-ключу
                String description = cacheService.getErrorDescription(error);
                System.out.println(" -> [" + error + "] " + description);
            }
        }

        // 4. Твоя логика триггера критической тревоги
        if (dto.obd2Errors().contains("P0300")) {
            System.out.println("⚠️ ТРЕВОГА! Обнаружена критическая ошибка двигателя P0300! Отправляем экстренное уведомление владельцу...");
        }
        System.out.println("----------------------------------------------------------------");
    }
}
