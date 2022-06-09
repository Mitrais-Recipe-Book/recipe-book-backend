package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.service.TagService;


public class TagControllerTest {
    private static final TagService TAG_SERVICE = mock(TagService.class);

    private static final TagEntity TAG_ENTITY = mock(TagEntity.class);

    private static TagController tagController;
    
    @BeforeAll
    static void init(){
        tagController = new TagController(TAG_SERVICE);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void getAll(){
        Pair<String, List<TagEntity>> mockResult = Pair.of("success", mock(List.class));

        when(TAG_SERVICE.getAllTags()).thenReturn(mockResult);

        assertEquals(HttpStatus.OK, tagController.getAll().getStatusCode());
    }
}
