package com.cdcone.recipy.controller;

import com.cdcone.recipy.dtoAccess.TagDtoAdmin;
import com.cdcone.recipy.dtoRequest.EditTagDto;
import com.cdcone.recipy.entity.TagEntity;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<CommonResponse> getAll() {
        Pair<String, List<TagEntity>> result = tagService.getAllTags();

        if (result.getFirst().charAt(0) == 's'){
            return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
        }

        return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> addTag(@RequestBody String tagName) {
       Pair<String, TagEntity> result = tagService.saveTag(tagName);

       if (result.getFirst().charAt(0) == 's'){
           return ResponseEntity.ok().body(new CommonResponse(result.getFirst(), result.getSecond()));
       }

       return ResponseEntity.badRequest().body(new CommonResponse(result.getFirst()));
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse> editTag(@RequestBody EditTagDto dto) {
        try {
            Pair<HttpStatus, Map<String, String>> editTag = tagService.editTag(dto.getTagId(), dto.getTagReplace());
            Map<String, String> payload = editTag.getSecond();
            String msg = payload.get("msg");
            payload.remove("msg");
            return ResponseEntity.status(editTag.getFirst()).body(new CommonResponse(msg, payload));
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

    @PutMapping("addview")
    public ResponseEntity<CommonResponse> addViewCount(int tagId) {
        String result = tagService.addViewCount(tagId);
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (result.charAt(0) == 's') {
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(new CommonResponse(result));
    }

    @GetMapping("all")
    public ResponseEntity<CommonResponse> getAllWithViewCount() {
        Set<TagDtoAdmin> result = tagService.getAllTagsView();
        return ResponseEntity.ok(new CommonResponse("success: data retrieved", result));
    }
}
