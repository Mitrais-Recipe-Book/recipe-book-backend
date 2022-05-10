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
        TagEntity newTag = new TagEntity(name);
        String s;
        try {
            tagDao.save(newTag);
            s = newTag.getName();
        } catch (Exception e) {
            s = "Tag already exist";
        }
        return s;
    }

    public TagEntity getById(int id){
        return tagDao.findById(id).get();
    }

    public void editTag(int old, String tag) {
        TagEntity byName = tagDao.findById(old).get();
        byName.setName(tag);
        tagDao.save(byName);
    }
}
