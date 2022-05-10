package com.cdcone.recipy.controller;

import com.cdcone.recipy.dto.EditTagDto;
import com.cdcone.recipy.response.CommonResponse;
import com.cdcone.recipy.service.TagService;
import lombok.RequiredArgsConstructor;
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
            List<String> allTags = tagService.getAllTags();
            return ResponseEntity.ok(new CommonResponse("success: data retrieved", allTags));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse> addTag(@RequestBody String tagName) {
        try {
            String savedTag = tagService.saveTag(tagName);
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
            return ResponseEntity.badRequest().body(new CommonResponse(e.getCause().toString()));
        }
    }
}
