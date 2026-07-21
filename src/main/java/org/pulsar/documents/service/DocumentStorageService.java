package org.pulsar.documents.service;

import org.pulsar.documents.model.Document;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class DocumentStorageService {

    private final ObjectMapper objectMapper;

    public DocumentStorageService() {
        this.objectMapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
    }

    public void saveToFile(File file, List<Document> documents) {
        try {
            objectMapper.writerFor(new TypeReference<List<Document>>() {})
                    .writeValue(file, documents);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранения JSON: " + e.getMessage());
        }
    }
}
