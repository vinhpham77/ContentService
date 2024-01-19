package org.caykhe.contentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.services.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {
    private final SeriesService seriesService;
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        seriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/detail/{seriesId}")
    public ResponseEntity<?> getPostsBySeriesId(@PathVariable Integer seriesId) {
        List<Post> postList = seriesService.getListPost(seriesId);
        if (postList.isEmpty()) {
            return new ResponseEntity<>("Không thấy bài viết nào trong series", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(postList, HttpStatus.OK);
        }
    }
    @GetMapping("/totalSeries/{username}")
    public ResponseEntity<?> getTotalPost(@PathVariable String username){
        return new ResponseEntity<>(seriesService.countSeriesCreateBy(username),HttpStatus.OK);
    }
    @PutMapping("/updateScore")
    public ResponseEntity<?> updateScore( @RequestParam Integer id,@RequestParam int score ){
        return  new ResponseEntity<>(seriesService.upDateScore(id,score),HttpStatus.OK);
    }
}
