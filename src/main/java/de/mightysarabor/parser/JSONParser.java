package de.mightysarabor.parser;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mightysarabor.records.CreatePostRequest;
import de.mightysarabor.records.Flight_Info;

public class JSONParser {
    private final ObjectMapper mapper;

    public JSONParser() {
        mapper = new ObjectMapper()
                .configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false
                );
    }

    public <T> T parse(String json, Class <T> myRecord) throws JsonProcessingException {
        return mapper.readValue(
                json, myRecord
        );
    }

    public JsonNode parseToTree(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }

    public String writeValueAsString(CreatePostRequest post) throws JsonProcessingException {
        return mapper.writeValueAsString(post);
    }

}