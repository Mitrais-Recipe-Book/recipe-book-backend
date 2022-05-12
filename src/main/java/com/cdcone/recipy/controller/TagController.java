package com.cdcone.recipy.controller;

import com.cdcone.recipy.dtoRequest.EditTagDto;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<CommonResponse> getAll() {
        try {
            List<TagEntity> allTags = tagService.getAllTags();
            return ResponseEntity.ok(new CommonResponse("success: data retrieved", allTags));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse> addTag(@RequestBody String tagName) {
        try {
            TagEntity savedTag = tagService.saveTag(tagName);
            return ResponseEntity.ok(new CommonResponse("success: data saved", savedTag));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse> editTag(@RequestBody EditTagDto dto) {
        try {
            tagService.editTag(dto.getTagId(), dto.getTagReplace());
            return ResponseEntity.ok(new CommonResponse("success: data updated", dto.getTagReplace()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.toString()));
        }
    }

    @DeleteMapping("{tag_id}")
    public ResponseEntity<CommonResponse> deleteTag(@PathVariable(name = "tag_id") int tagId) {
        TagEntity tagEntity = tagService.deleteTag(tagId);
        HttpStatus status = HttpStatus.OK;
        String msg = "success: data deleted";
        if (tagEntity == null) {
            status = HttpStatus.BAD_REQUEST;
            msg = "error: Tag not found";
        }
        return ResponseEntity.status(status).body(new CommonResponse(msg, tagEntity));
    }
}
