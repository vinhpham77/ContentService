package org.caykhe.contentservice.utils;

import org.caykhe.contentservice.dtos.SeriesDto;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Series;
import org.caykhe.contentservice.models.SeriesPost;
import org.caykhe.contentservice.repositories.SeriesPostRepository;

import java.util.List;

public class ConverterUtils {
    public static SeriesDto convertSeriesDto(Series series, SeriesPostRepository seriesPostRepository) {
        List<Integer> postIds = seriesPostRepository.findAllBySeriesId(series.getId())
                .stream().map(SeriesPost::getPost)
                .map(Post::getId)
                .toList();

        return SeriesDto.builder()
                .id(series.getId())
                .title(series.getTitle())
                .content(series.getContent())
                .postIds(postIds)
                .isPrivate(series.getIsPrivate())
                .createdBy(series.getCreatedBy())
                .updatedAt(series.getUpdatedAt())
                .score(series.getScore())
                .commentCount(series.getCommentCount())
                .build();
    }
}
