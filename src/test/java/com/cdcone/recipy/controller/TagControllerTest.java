package com.cdcone.recipy.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;
import com.cdcone.recipy.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TagController.class, TagService.class})
@ExtendWith(SpringExtension.class)
class TagControllerTest {
    @Autowired
    private TagController tagController;

    @MockBean
    private TagDao tagDao;

    /**
     * Method under test: {@link TagController#addTag(String)}
     */
    @Test
    void testAddTag() throws Exception {
        when(this.tagDao.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/api/v1/tag")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link TagController#addTag(String)}
     */
    @Test
    void testAddTag2() throws Exception {
        when(this.tagDao.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/tag");
        getResult.characterEncoding("Encoding");
        MockHttpServletRequestBuilder contentTypeResult = getResult.contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link TagController#editTag(String, String)}
     */
    @Test
    void testEditTag() throws Exception {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("Name");
        Optional<TagEntity> ofResult = Optional.of(tagEntity);

        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setId(1);
        tagEntity1.setName("Name");
        when(this.tagDao.save((TagEntity) any())).thenReturn(tagEntity1);
        when(this.tagDao.findByName((String) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/v1/tag/{name}", "Name")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"Success\",\"payload\":\"Name\"}"));
    }

    /**
     * Method under test: {@link TagController#editTag(String, String)}
     */
    @Test
    void testEditTag2() throws Exception {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("Name");
        when(this.tagDao.save((TagEntity) any())).thenReturn(tagEntity);
        when(this.tagDao.findByName((String) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/v1/tag/{name}", "Name")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"OK\",\"message\":\"Success\",\"payload\":\"Tag not found\"}"));
    }

    /**
     * Method under test: {@link TagController#getAll()}
     */
    @Test
    void testGetAll() throws Exception {
        when(this.tagDao.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/tag");
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }

    /**
     * Method under test: {@link TagController#getAll()}
     */
    @Test
    void testGetAll2() throws Exception {
        when(this.tagDao.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/tag");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":\"OK\",\"message\":\"SUCCESS\",\"payload\":[]}"));
    }
}

