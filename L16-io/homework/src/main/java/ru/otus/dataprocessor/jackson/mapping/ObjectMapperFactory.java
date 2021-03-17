package ru.otus.dataprocessor.jackson.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public final class ObjectMapperFactory {
    private ObjectMapperFactory() {
    }

    private static final ObjectMapper commonMapper = new ObjectMapper();
    private static final ObjectMapper measurementMapper = new ObjectMapper();

    static {
        measurementMapper.addMixIn(Measurement.class, MeasurementMappingMixIn.class);
    }

    public static ObjectMapper measurementMapper() {
        return measurementMapper;
    }

    public static ObjectMapper commonMapper() {
        return commonMapper;
    }
}
