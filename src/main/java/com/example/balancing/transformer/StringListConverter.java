//package com.example.balancing.transformer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Converter
//public class StringListConverter implements AttributeConverter<List<String>, String> {
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(List<String> list) {
//        try {
//            return mapper.writeValueAsString(list);
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    @Override
//    public List<String> convertToEntityAttribute(String json) {
//        try {
//            return json == null ? new ArrayList<>() :
//                    mapper.readValue(json, new TypeReference<List<String>>() {});
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//}
