package org.caykhe.contentservice.services;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.ApiException;
import org.caykhe.contentservice.dtos.ResultCount;
import org.caykhe.contentservice.dtos.TagCount;
import org.caykhe.contentservice.models.Tag;
import org.caykhe.contentservice.repositories.PostTagRepository;
import org.caykhe.contentservice.repositories.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    public Tag findByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new ApiException("Thẻ #" + name + " không tồn tại", HttpStatus.NOT_FOUND));
    }

    public ResultCount<Tag> get(Integer page, Integer size) {
        Pageable pageable = (page == null || size == null || page < 1 || size < 1)
                ? Pageable.unpaged()
                : PageRequest.of(page - 1, size, Sort.by("name"));

        List<Tag> tags = tagRepository.findAll(pageable).getContent();
        long count = tagRepository.count();

        return new ResultCount<>(tags, count);
    }

    public List<TagCount> getTagCounts(String username) {
        return postTagRepository.countTagsByPost_CreatedBy(username);
    }
}
