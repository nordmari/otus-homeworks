package ru.otus.dataprocessor.jackson.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeasurementMappingMixIn {
    @JsonCreator
    public MeasurementMappingMixIn(@JsonProperty("name") String name, @JsonProperty("value") double value) {
    }
}
