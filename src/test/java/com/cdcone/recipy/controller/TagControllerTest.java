package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.cdcone.recipy.recipe.controller.TagController;
import com.cdcone.recipy.recipe.dto.response.TagResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.recipe.dto.request.EditTagRequestDto;
import com.cdcone.recipy.recipe.entity.TagEntity;
import com.cdcone.recipy.recipe.service.TagService;


public class TagControllerTest {
    private static final TagService TAG_SERVICE = mock(TagService.class);

    private static final TagEntity TAG_ENTITY = mock(TagEntity.class);
    private static final EditTagRequestDto EDIT_TAG_DTO = mock(EditTagRequestDto.class);

    private static TagController tagController;
    
    @BeforeAll
    static void init(){
        tagController = new TagController(TAG_SERVICE);
    }
    
    @Test
    void getAll(){
        Pair<String, List<TagResponseDto>> mockResult = Pair.of("success", List.of());

        when(TAG_SERVICE.getAllTags()).thenReturn(mockResult);

        assertEquals(HttpStatus.OK, tagController.getAll().getStatusCode());
    }


    @Test
    void addTag(){
        Pair<String, TagEntity> mockResult = Pair.of("success", TAG_ENTITY);
        
        when(TAG_SERVICE.saveTag("any")).thenReturn(mockResult);

        assertEquals(HttpStatus.OK, tagController.addTag("any").getStatusCode());
    }
    
    @Test
    void failedAddTag(){
        Pair<String, TagEntity> mockResult = Pair.of("failed", TAG_ENTITY);
        
        when(TAG_SERVICE.saveTag("any")).thenReturn(mockResult);

        assertNotEquals(HttpStatus.OK, tagController.addTag("any").getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void editTag(){
        Pair<HttpStatus, Map<String, String>> mockResult = Pair.of(HttpStatus.OK, mock(Map.class));

        when(TAG_SERVICE.editTag(1, "new")).thenReturn(mockResult);
        when(EDIT_TAG_DTO.getTagId()).thenReturn(1);
        when(EDIT_TAG_DTO.getTagReplace()).thenReturn("new");

        assertEquals(HttpStatus.OK, tagController.editTag(EDIT_TAG_DTO).getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failedEditTag(){
        Pair<HttpStatus, Map<String, String>> mockResult = Pair.of(HttpStatus.BAD_REQUEST, mock(Map.class));

        when(TAG_SERVICE.editTag(1, "new")).thenReturn(mockResult);
        when(EDIT_TAG_DTO.getTagId()).thenReturn(1);
        when(EDIT_TAG_DTO.getTagReplace()).thenReturn("new");

        assertNotEquals(HttpStatus.OK, tagController.editTag(EDIT_TAG_DTO).getStatusCode());
    }

    @Test
    void deleteTag(){
        when(TAG_SERVICE.deleteTag(1)).thenReturn(TAG_ENTITY);

        assertEquals(HttpStatus.OK, tagController.deleteTag(1).getStatusCode());
    }

    @Test
    void failDeleteTag(){
        when(TAG_SERVICE.deleteTag(1)).thenReturn(null);

        assertNotEquals(HttpStatus.OK, tagController.deleteTag(1).getStatusCode());
    }
}
