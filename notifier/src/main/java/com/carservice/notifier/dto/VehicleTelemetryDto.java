package com.carservice.notifier.dto;

import java.util.List;
import java.time.LocalDateTime;

public record VehicleTelemetryDto(
    String vin,
    double mileage,
    double fuelLevel,
    List<String> obd2Errors,
    LocalDateTime time
) {} 
