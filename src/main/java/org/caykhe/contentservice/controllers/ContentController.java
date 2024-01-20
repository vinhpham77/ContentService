package org.caykhe.contentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.services.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;
    
    @GetMapping("/{username}/stats")
    public ResponseEntity<?> getContentStats(@PathVariable String username) {
        return new ResponseEntity<>(contentService.getContentStats(username), HttpStatus.OK);
    }
}
