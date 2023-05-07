package com.mobile.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DataContoller {

    @GetMapping("/getAllMobilesData")
    public ResponseEntity<List<Mobile>> getAllMobilesdata()
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/resources/static/data.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Mobile> mobiles = objectMapper.readValue(objectMapper.writeValueAsString(obj),ArrayList.class);
            return new ResponseEntity<>(mobiles, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getMobileDataWithId/{id}")
    public ResponseEntity<Optional<Mobile>> getMobileDataWithId(@PathVariable("id") String mobileId)
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/resources/static/data.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            List<Mobile> mobiles = objectMapper.readValue(objectMapper.writeValueAsString(obj),new TypeReference<List<Mobile>>() {});
            Optional<Mobile> mobile = mobiles.stream().filter((mobile1 -> mobile1.getId().equals(mobileId))).findFirst();
            return new ResponseEntity<>(mobile, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
