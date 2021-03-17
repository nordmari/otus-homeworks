package ru.otus.dataprocessor;

import ru.otus.dataprocessor.exception.FileProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static ru.otus.dataprocessor.jackson.mapping.ObjectMapperFactory.commonMapper;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try {
            String jsonString = commonMapper().writeValueAsString(data);
            Files.writeString(Paths.get(fileName), jsonString);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to write to file", e);
        }
    }
}
