package de.mightysarabor.parser;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mightysarabor.records.Flight_Info;

import java.util.concurrent.Flow;

public class JSONParser {
    private final ObjectMapper mapper;

    public JSONParser() {
        mapper = new ObjectMapper()
                .configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false
                );
    }

    public Flight_Info parse(String json) throws JsonProcessingException {
        return mapper.readValue(
                json, Flight_Info.class
        );
    }

    public JsonNode parseToTree(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }
}