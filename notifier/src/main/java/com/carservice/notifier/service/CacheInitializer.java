package com.carservice.notifier.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Говорим Спрингу: "Создай этот объект при старте"
public class CacheInitializer implements CommandLineRunner {

    private final ErrorCodeCacheService cacheService;

    // Внедряем твой созданный сервис кэширования
    public CacheInitializer(ErrorCodeCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(" [Redis Prep] Начинаем наполнение кэша кодов ошибок OBD2...");
        
        // Вот здесь мы должны вызвать метод сохранения для базовых кодов ошибок
        cacheService.saveErrorDescription("P0300", "Пропуски зажигания в цилиндрах двигателя (Критично)");
        cacheService.saveErrorDescription("P0171", "Слишком бедная воздушно-топливная смесь в системе");
        cacheService.saveErrorDescription("P0420", "Эффективность катализатора ниже допустимого порога");
        
        System.out.println(" [Redis Prep] Справочник успешно загружен в оперативную память!");
    }
}
