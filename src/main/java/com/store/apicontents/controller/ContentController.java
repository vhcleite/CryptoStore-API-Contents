package com.store.apicontents.controller;

import com.store.apicontents.model.ContentEntity;
import com.store.apicontents.model.dto.ContentGetDto;
import com.store.apicontents.model.dto.ContentPostDto;
import com.store.apicontents.service.ContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("store/v1/content")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<Page<ContentGetDto>> getAll(@RequestParam(name = "userId", required = true) String userId,
                                                      @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = false) Integer pageSize) {
        Page<ContentGetDto> contentPage = contentService.findPage(PageRequest.of(page, pageSize), userId);
        return new ResponseEntity<Page<ContentGetDto>>(contentPage, HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentGetDto> getById(@PathVariable(name = "contentId") Long contentId) {
        ContentGetDto content = contentService.findById(contentId);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContentEntity> create(@RequestBody @Validated ContentPostDto content) {
        return new ResponseEntity<ContentEntity>(contentService.create(content), HttpStatus.CREATED);
    }

}
