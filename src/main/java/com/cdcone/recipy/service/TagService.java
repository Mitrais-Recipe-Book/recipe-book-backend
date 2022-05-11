package com.cdcone.recipy.service;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;
import lombok.RequiredArgsConstructor;
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
        TagEntity newTag = new TagEntity(name.toLowerCase());
        tagDao.save(newTag);
        return newTag;
    }

    public TagEntity getById(int id) {
        return tagDao.findById(id).get();
    }

    public void editTag(int old, String tag) {
        Optional<TagEntity> byName = tagDao.findById(old);

        byName.get().setName(tag);
        tagDao.save(byName.get());
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
