package com.carservice.telemetry.service;

import com.carservice.telemetry.dto.VehicleTelemetryDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TelemetryProducer {
    private static final String TOPIC = "vehicle-telemetry";
    private final KafkaTemplate<String, VehicleTelemetryDto> kafkaTemplate;

    public TelemetryProducer(KafkaTemplate<String, VehicleTelemetryDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTelemetry(VehicleTelemetryDto telemetry) {
        kafkaTemplate.send(TOPIC, telemetry.vin(), telemetry);
        System.out.println(" [Telemetry OS] Данные отправлены в Kafka для VIN: " + telemetry.vin());
    }
}
