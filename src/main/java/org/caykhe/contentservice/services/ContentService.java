package org.caykhe.contentservice.services;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.ContentStats;
import org.caykhe.contentservice.repositories.PostRepository;
import org.caykhe.contentservice.repositories.PostTagRepository;
import org.caykhe.contentservice.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final SeriesRepository seriesRepository;

    public ContentStats getContentStats(String username) {
        int postCount = postRepository.countByCreatedBy(username);
        int questionCount = postTagRepository.countByPost_CreatedByAndTag_Name(username, "HoiDap");
        int seriesCount = seriesRepository.countByCreatedBy(username);

        return ContentStats.builder()
                .postCount(postCount)
                .questionCount(questionCount)
                .seriesCount(seriesCount)
                .build();
    }
}
