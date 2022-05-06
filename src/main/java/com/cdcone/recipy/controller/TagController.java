package com.cdcone.recipy.controller;

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
        List<String> allTags = tagService.getAllTags();
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, allTags));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> addTag(@RequestBody String tagName) {
        String savedTag = tagService.saveTag(tagName);
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, "Success", savedTag));
    }

    @PutMapping("{name}")
    public ResponseEntity<CommonResponse> editTag(@PathVariable String name, @RequestBody String newTag) {
        String editedTag = tagService.editTag(name, newTag);
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK, "Success", editedTag));
    }
}
