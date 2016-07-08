/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: SettingsService.java
 */
package com.ilts.anywhere.settings;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;

@Service
@ComponentScan
public class SettingsService {
//	private JdbcTemplate jdbcTemplate;
    @Autowired
    private SettingsDAO settingsDao;

    private List<Map<String, Object>> LanguageFieldsMap;

    SettingsService() {
    }

    private void prepareFieldsMap(Map<Object, String> map) {
        for (Map fieldmap : this.LanguageFieldsMap) {
            // check for ancestor
            Integer parentId = ((Long) fieldmap.get("parentId")).intValue();

            if (parentId == 0) {

            }
        }
    }

    public List<AppLanguageSerialized> getSerializedLanguages() {
        // TODO Auto-generated method stub
        return this.settingsDao.getLanguagesSerialized();
    }

    public AppLanguageSerialized getSerializedLanguages(String id) {
        // TODO Auto-generated method stub
        return this.settingsDao.getLanguagesSerialized(id);
    }

    public Object saveSerializedLanguage(String jsonRequest) {
        // parse post data
        ObjectMapper mapper = new ObjectMapper();
        AppLanguageSerialized language = new AppLanguageSerialized();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            language = mapper.readValue(jsonRequest, AppLanguageSerialized.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // save to DB
        this.settingsDao.updateLanguage(language.getId(), language.getJson());

        return ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);
    }

    public Object saveNewSerializedLanguage(String jsonRequest) {
        // parse post data
        ObjectMapper mapper = new ObjectMapper();
        AppLanguageSerialized language = new AppLanguageSerialized();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            language = mapper.readValue(jsonRequest, AppLanguageSerialized.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // save to DB
        this.settingsDao.createLanguage(language.getName(), language.getAlias(), language.getJson());

        return ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);
    }
}
