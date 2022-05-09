package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TagService.class})
@ExtendWith(SpringExtension.class)
class TagServiceTest {
    @MockBean
    private TagDao tagDao;

    @Autowired
    private TagService tagService;

    /**
     * Method under test: {@link TagService#getAllTags()}
     */
    @Test
    void testGetAllTags() {
        when(this.tagDao.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.tagService.getAllTags().isEmpty());
        verify(this.tagDao).findAll();
    }

    /**
     * Method under test: {@link TagService#saveTag(String)}
     */
    @Test
    void testSaveTag() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("Name");
        when(this.tagDao.save((TagEntity) any())).thenReturn(tagEntity);
        assertEquals("Name", this.tagService.saveTag("Name"));
        verify(this.tagDao).save((TagEntity) any());
    }

    /**
     * Method under test: {@link TagService#editTag(String, String)}
     */
    @Test
    void testEditTag() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("Name");
        Optional<TagEntity> ofResult = Optional.of(tagEntity);

        TagEntity tagEntity1 = new TagEntity();
        tagEntity1.setId(1);
        tagEntity1.setName("Name");
        when(this.tagDao.save((TagEntity) any())).thenReturn(tagEntity1);
        when(this.tagDao.findByName((String) any())).thenReturn(ofResult);
        assertEquals("Name", this.tagService.editTag("Old", "Tag"));
        verify(this.tagDao).save((TagEntity) any());
        verify(this.tagDao).findByName((String) any());
    }

    /**
     * Method under test: {@link TagService#editTag(String, String)}
     */
    @Test
    void testEditTag2() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("Name");
        when(this.tagDao.save((TagEntity) any())).thenReturn(tagEntity);
        when(this.tagDao.findByName((String) any())).thenReturn(Optional.empty());
        assertEquals("Tag not found", this.tagService.editTag("Old", "Tag"));
        verify(this.tagDao).findByName((String) any());
    }
}

