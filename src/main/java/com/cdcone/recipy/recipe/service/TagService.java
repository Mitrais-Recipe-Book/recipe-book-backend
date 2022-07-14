package com.cdcone.recipy.recipe.service;

import com.cdcone.recipy.recipe.dto.response.TagAdminResponseDto;
import com.cdcone.recipy.recipe.entity.TagEntity;
import com.cdcone.recipy.recipe.repository.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagRepository;

    public Pair<String, List<TagEntity>> getAllTags() {
        return Pair.of("success: data retrieved", tagRepository.findAll());
    }

    public Pair<String, TagEntity> saveTag(String name) {
        String msg;
        TagEntity tag;
        try {
            tag = tagRepository.save(new TagEntity(name.toLowerCase()));
            msg = "success: tag saved";
        } catch (DataIntegrityViolationException e) {
            msg = "failed: tag already exists";
            tag = new TagEntity();
        }
        return Pair.of(msg, tag);
    }

    public Pair<TagEntity, String> getById(int id) {
        Optional<TagEntity> tagById = tagRepository.findById(id);
        String msg = "failed: tag with id " + id + " not found";
        TagEntity result = new TagEntity();

        if (tagById.isPresent()) {
            msg = "success: data retrieved";
            result = tagById.get();
        }

        return Pair.of(result, msg);
    }

    public Pair<HttpStatus, Map<String, String>> editTag(int old, String tag) {
        Optional<TagEntity> byName = tagRepository.findById(old);
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
                    tagRepository.save(toBeEdited);
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
        Optional<TagEntity> byId = tagRepository.findById(tagId);
        if (byId.isPresent()) {
            TagEntity toBeDeleted = byId.get();
            tagRepository.delete(toBeDeleted);
            return toBeDeleted;
        }
        return null;
    }

    public Set<TagEntity> getByRecipeId(Long recipeId) {
        return tagRepository.findByRecipeId(recipeId);
    }

    public String addViewCount(int tagId) {
        String msg = "failed: tag not found";
        Optional<TagEntity> tagById = tagRepository.findById(tagId);
        if (tagById.isPresent()) {
            TagEntity tag = tagById.get();
            int viewCount = tag.getViews() == null ? 0 : tag.getViews();
            tag.setViews(viewCount + 1);
            tagRepository.save(tag);
            msg = "success: data updated";
        }
        return msg;
    }

    public Set<TagAdminResponseDto> getAllTagsView() {
        return tagRepository.findAllViewCount();
    }
}
