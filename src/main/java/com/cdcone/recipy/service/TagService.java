package com.cdcone.recipy.service;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;

    public List<TagEntity> getAllTags() {
        return tagDao.findAll();
    }

    public TagEntity saveTag(String name) {
        try {
            TagEntity newTag = new TagEntity(name.toLowerCase());
            return tagDao.save(newTag);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public TagEntity getById(int id) {
        return tagDao.findById(id).get();
    }

    public String editTag(int old, String tag) {
        Optional<TagEntity> byName = tagDao.findById(old);
        String s = null;
        if (byName.isPresent()) {
            TagEntity toBeEdited = byName.get();
            tag = tag.toLowerCase();
            if (tag.equals(toBeEdited.getName())) {
                s = "error: tag already exist";
            } else {
                toBeEdited.setName(tag);
                tagDao.save(toBeEdited);
                s = "success: data updated";
            }
        }
        return s;
    }

    public TagEntity deleteTag(int tagId) {
        Optional<TagEntity> byId = tagDao.findById(tagId);
        if (byId.isPresent()) {
            TagEntity toBeDeleted = byId.get();
            tagDao.delete(toBeDeleted);
            return toBeDeleted;
        }
        return null;
    }

    public Set<TagEntity> getByRecipeId(Long recipeId) {
        return tagDao.findByRecipeId(recipeId);
    }
}
