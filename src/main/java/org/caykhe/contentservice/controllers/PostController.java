package org.caykhe.contentservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.PostDto;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/by/{username}")
    public ResponseEntity<?> get(@PathVariable String username, String tag, Integer page, Integer size) {
        return new ResponseEntity<>(postService.getByUser(username, tag, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return new ResponseEntity<>(postService.get(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto) {
        Post createdPost = postService.create(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.update(id, postDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPostAggregations(@RequestParam(required = false, name = "page") Integer page,
                                                 @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
                                                 @RequestParam(required = false, defaultValue = "") String tag) {
        return new ResponseEntity<>(postService.getPosts(page, limit, tag), HttpStatus.OK);
    }

    @GetMapping("/postsSameAuthor/{authorName}")
    public ResponseEntity<?> getPostsSameAuthor(@PathVariable String authorName, @RequestParam Integer postId) {
        return new ResponseEntity<>(postService.postsByTheSameAuthorsExcludingCurrent(authorName, postId), HttpStatus.OK);
    }

    @GetMapping("/totalPost/{username}")
    public ResponseEntity<?> getTotalPost(@PathVariable String username) {
        return new ResponseEntity<>(postService.countPostCreateBy(username), HttpStatus.OK);
    }

    @PutMapping("/updateScore")
    public ResponseEntity<?> updateScore(@RequestParam Integer id, @RequestParam int score) {
        return new ResponseEntity<>(postService.updateScore(id, score), HttpStatus.OK);
    }

}