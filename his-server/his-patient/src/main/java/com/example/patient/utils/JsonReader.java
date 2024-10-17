package com.example.patient.utils;

import com.example.dto.patient.PatientAlterDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonReader {

    public List<PatientAlterDTO> readJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<List<PatientAlterDTO>>() {});
    }
}

