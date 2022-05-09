package com.cdcone.recipy.service;

import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.repository.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;

    public List<String> getAllTags() {
        return tagDao.findAll()
                .stream()
                .map(TagEntity::getName)
                .collect(Collectors.toList());
    }

    public String saveTag(String name) {
        TagEntity newTag = new TagEntity(name.toLowerCase());
        String s;
        try {
            tagDao.save(newTag);
            s = newTag.getName();
        } catch (Exception e) {
            s = "Tag already exist";
        }
        return s;
    }

    public String editTag(String old, String tag) {
        Optional<TagEntity> byName = tagDao.findByName(old);
        if (byName.isPresent()) {
            TagEntity tagEntity = byName.get();
            tagEntity.setName(tag);
            return tagDao.save(tagEntity).getName();
        }
        return "Tag not found";
    }
}
