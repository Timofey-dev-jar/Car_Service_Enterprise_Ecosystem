package com.carservice.telemetry.controller;

import com.carservice.telemetry.dto.VehicleTelemetryDto;
import com.carservice.telemetry.service.TelemetryProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {
    private final TelemetryProducer telemetryProducer;

    public TelemetryController(TelemetryProducer telemetryProducer) {
        this.telemetryProducer = telemetryProducer;
    }

    @PostMapping
    public String receiveTelemetry(@RequestBody VehicleTelemetryDto telemetry) {
        telemetryProducer.sendTelemetry(telemetry);
        return "Данные телеметрии успешно приняты и отправлены в очередь!";
    }
}
