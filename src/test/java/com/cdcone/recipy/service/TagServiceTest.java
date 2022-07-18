package com.cdcone.recipy.service;

import com.cdcone.recipy.error.handler.TagInUseException;
import com.cdcone.recipy.recipe.dto.response.TagAdminResponseDto;
import com.cdcone.recipy.recipe.dto.response.TagResponseDto;
import com.cdcone.recipy.recipe.entity.TagEntity;
import com.cdcone.recipy.recipe.repository.TagRepository;
import com.cdcone.recipy.recipe.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest {

    private TagService tagService;
    private final TagRepository tagRepository = mock(TagRepository.class);
    private final TagEntity tag1 = mock(TagEntity.class);
    private final TagEntity tag2 = mock(TagEntity.class);

    @BeforeEach
    void setUp() {
        tagService = new TagService(tagRepository);

        when(tag1.getId()).thenReturn(1);
        when(tag1.getName()).thenReturn("breakfast");
        when(tag2.getId()).thenReturn(2);
        when(tag2.getName()).thenReturn("dinner");
    }

    @Test
    void testSuccessGetAllTags() {
        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        List<TagResponseDto> allTags = tagService.getAllTags().getSecond();
        assertEquals(2, allTags.size());
        assertEquals("breakfast", allTags.get(0).getName());
        assertEquals(1, allTags.get(0).getId());
        assertEquals("dinner", allTags.get(1).getName());
        assertEquals(2, allTags.get(1).getId());
    }

    @Test
    void testFailToAddNewTag() {
        when(tagRepository.save(any(TagEntity.class)))
                .thenThrow(DataIntegrityViolationException.class);

        TagEntity newTag = tagService.saveTag("breakfast").getSecond();
        assertNull(newTag.getName());
    }

    @Test
    void testSuccessAddNewTag() {
        when(tagRepository.save(any(TagEntity.class)))
                .thenReturn(tag1);

        TagEntity newTag = tagService.saveTag("breakfast").getSecond();
        verify(tagRepository).save(any(TagEntity.class));
        assertEquals("breakfast", newTag.getName());
        assertEquals(1, newTag.getId());
    }

    @Test
    void testFailToGetTagById() {
        when(tagRepository.findById(1)).thenReturn(Optional.empty());
        //Pair<TagEntity, String> result = tagService.getById(1);

        assertTrue(tagService.getById(1).getSecond().startsWith("failed"));
    }

    @Test
    void testSuccessGetTagById() {
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag1));

        TagEntity byId = tagService.getById(1).getFirst();
        verify(tagRepository).findById(1);
        assertEquals("breakfast", byId.getName());
    }

    @Test
    void testFailToEditTagWithInvalidId() {
        when(tagRepository.findById(11)).thenReturn(Optional.empty());

        Pair<HttpStatus, Map<String, String>> newTag =
                tagService.editTag(11, "new tag");

        var payload = newTag.getSecond();
        assertEquals(HttpStatus.NOT_FOUND, newTag.getFirst());
        assertNotNull(payload);
        assertEquals("new tag", payload.get("input"));
        assertEquals("error: tag not found", payload.get("msg"));
    }

    @Test
    void testFailToEditTagWithSameName() {
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag1));

        var editTag = tagService.editTag(1, "breakfasT");
        var payload = editTag.getSecond();
        assertEquals(HttpStatus.BAD_REQUEST, editTag.getFirst());
        assertNotNull(payload);
        assertEquals("breakfast", payload.get("toBeEdited"));
        assertEquals("error: tag must be different", payload.get("msg"));
    }

    @Test
    void testFailToEditTagIfAlreadyExist() {
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag1));
        when(tagRepository.save(tag1)).thenThrow(DataIntegrityViolationException.class);

        var editTag = tagService.editTag(1, "already exists");
        var payload = editTag.getSecond();
        assertEquals(HttpStatus.BAD_REQUEST, editTag.getFirst());
        assertEquals("error: tag already exists", payload.get("msg"));
    }

    @Test
    void testSuccessEditTag() {
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag1));

        var editTag = tagService.editTag(1, "lunch");
        var payload = editTag.getSecond();
        verify(tagRepository).save(tag1);
        assertEquals(HttpStatus.OK, editTag.getFirst());
        assertEquals("success: data updated", payload.get("msg"));
    }

    @Test
    void testFailToDeleteTagIfNotExist() {
        when(tagRepository.findById(2)).thenReturn(Optional.of(tag2));
        doThrow(DataIntegrityViolationException.class)
                .when(tagRepository)
                .delete(tag2);
        assertThrows(TagInUseException.class, () -> tagService.deleteTag(2));
    }

    @Test
    void testSuccessDeleteTag() {
        when(tagRepository.findById(2)).thenReturn(Optional.of(tag2));

        var deleteTag = tagService.deleteTag(2);
        verify(tagRepository).delete(tag2);
        assertEquals(2, deleteTag.getId());
        assertEquals("dinner", deleteTag.getName());
    }


    @Test
    void testFailAddViewCountIfNotFound() {
        when(tagRepository.findById(1)).thenReturn(Optional.empty());

        String result = tagService.addViewCount(1);
        assertEquals("failed: tag not found", result);
    }

    @Test
    void testSuccessAddViewCount() {
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag1));
        when(tag1.getViews()).thenReturn(0);

        String result = tagService.addViewCount(1);
        verify(tagRepository).save(tag1);
        assertEquals("success: data updated", result);
    }

    @Test
    void testSuccessGetAllTagAdmin() {
        when(tagRepository.findAllByOrderByNameAsc()).thenReturn(List.of(tag1, tag2));

        List<TagAdminResponseDto> result = tagService.getAllTagsView();
        assertEquals(2, result.size());
    }
}