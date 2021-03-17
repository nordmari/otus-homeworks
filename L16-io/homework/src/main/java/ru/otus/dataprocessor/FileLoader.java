package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import ru.otus.dataprocessor.exception.FileProcessingException;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

import static ru.otus.dataprocessor.jackson.mapping.ObjectMapperFactory.measurementMapper;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        var jsonMeasurementsList = readJsonStringFromFile();
        return deserializeMeasurements(jsonMeasurementsList);
    }

    private List<Measurement> deserializeMeasurements(String jsonMeasurementsArray) {
        try {
            return measurementMapper().readValue(jsonMeasurementsArray, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Failed to deserialize string " + jsonMeasurementsArray, e);
        }
    }

    private String readJsonStringFromFile() {
        var resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileProcessException("File not found " + fileName);
        }
        try {
            return Files.readString(new File(resource.toURI()).toPath());
        } catch (IOException | URISyntaxException e) {
            throw new FileProcessingException("Failed to read from file", e);
        }
    }
}
