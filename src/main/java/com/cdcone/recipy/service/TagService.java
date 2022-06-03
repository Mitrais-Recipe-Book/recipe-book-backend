package com.cdcone.recipy.service;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;

    public Pair<String, List<TagEntity>> getAllTags() {
        return Pair.of("success: data retrieved", tagDao.findAll());
    }

    public TagEntity saveTag(String name) {
        try {
            TagEntity newTag = new TagEntity(name.toLowerCase());
            return tagDao.save(newTag);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public Pair<TagEntity, String> getById(int id) {
        try {
            return Pair.of(tagDao.findById(id).get(), "success: data retrieved");
        } catch (NoSuchElementException e) {
            return Pair.of(new TagEntity(), "failed: tag with id " + id + " not found");
        }
    }

    public Pair<HttpStatus, Map<String, String>> editTag(int old, String tag) {
        Optional<TagEntity> byName = tagDao.findById(old);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> payload = new HashMap<>();
        payload.put("input", tag);
        if (byName.isPresent()) {
            TagEntity toBeEdited = byName.get();
            String oldName = toBeEdited.getName();
            payload.put("toBeEdited", oldName);
            tag = tag.toLowerCase();
            try {
                if (!tag.equals(oldName)) {
                    toBeEdited.setName(tag);
                    tagDao.save(toBeEdited);
                    payload.put("msg", "success: data updated");
                    payload.put("new", toBeEdited.getName());
                    payload.remove("input");
                    status = HttpStatus.OK;
                } else {
                    payload.put("msg", "error: tag must be different");
                }
            } catch (DataIntegrityViolationException e) {
                payload.put("msg", "error: tag already exists");
            }
        } else {
            payload.put("msg", "error: tag not found");
            status = HttpStatus.NOT_FOUND;
        }
        return Pair.of(status, payload);
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
