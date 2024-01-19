package org.caykhe.contentservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.*;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Tag;
import org.caykhe.contentservice.repositories.PostRepository;
import org.caykhe.contentservice.utils.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserService userService;

    public Post get(Integer id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        String requester = SecurityContextHolder.getContext().getAuthentication().getName();
        String createdBy = post.getCreatedBy();

        if (post.getIsPrivate() && !createdBy.equals(requester)) {
            throw new ApiException("Bài viết tạm thời không có sẵn với mọi người", HttpStatus.FORBIDDEN);
        }

        return post;
    }

    @Transactional
    public ResultCount<Post> getByUser(String createdBy, String tag, Integer page, Integer size) {
        Page<Post> postPage;
        UserLatestPageable result = PaginationUtils.getUserLatestPageable(page, size);

        if (result.requester().equals(createdBy)) {
            postPage = (tag.isBlank()) ? postRepository.findByCreatedBy(createdBy, result.pageable())
                    : postRepository.findByCreatedByAndTagsName(createdBy, tag, result.pageable());
        } else {
            postPage = (tag.isBlank()) ? postRepository.findByCreatedByAndIsPrivateFalse(createdBy, result.pageable())
                    : postRepository.findByCreatedByAndTagsNameAndIsPrivateFalse(createdBy, tag, result.pageable());
        }

        List<Post> posts = postPage.toList();
        long count = postPage.getTotalElements();

        return new ResultCount<>(posts, count);
    }

    @Transactional
    public Post create(PostDto postDto) {
        var requester = SecurityContextHolder.getContext().getAuthentication().getName();

        Set<Tag> tags = postDto.getTags().stream()
                .map(tagService::findByName)
                .collect(Collectors.toSet());

        Post post = Post
                .builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .isPrivate(postDto.getIsPrivate())
                .createdBy(requester)
                .tags(tags)
                .build();

        try {
            post = postRepository.save(post);
            return post;
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi tạo bài viết. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Post update(Integer id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        String createdBy = post.getCreatedBy();
        String requester = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!createdBy.equals(requester)) {
            throw new ApiException("Bạn không có quyền chỉnh sửa bài viết này", HttpStatus.FORBIDDEN);
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setIsPrivate(postDto.getIsPrivate());
        post.setUpdatedAt(new Date().toInstant());

        List<Tag> tags = postDto.getTags().stream()
                .map(tagService::findByName)
                .toList();

        post.setTags(new HashSet<>(tags));

        try {
            return postRepository.save(post);
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi cập nhật bài viết. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void delete(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        String createdBy = post.getCreatedBy();
        String requester = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!createdBy.equals(requester)) {
            throw new ApiException("Bạn không có quyền xóa bài viết này", HttpStatus.FORBIDDEN);
        }

        try {
            postRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi xóa bài viết. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResultCount<Post> getPosts(Integer page, Integer size, String tag) {
        Page<Post> postPage;
        Pageable pageable = (page == null || size == null || page < 1 || size < 1)
                ? Pageable.unpaged()
                : PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"));

        postPage = tag.isBlank() ? postRepository.findByIsPrivateFalse(pageable)
                : postRepository.findByTagsNameAndIsPrivateFalse(tag, pageable);

        List<Post> posts = postPage.toList();
        long count = postPage.getTotalElements();

        return new ResultCount<>(posts, count);
    }

    public List<Post> postsByTheSameAuthorsExcludingCurrent(String authorName, Integer currentPostId) {
        Optional<User> author = userService.getByUsername(authorName);

        if (author.isPresent()) {
            return postRepository.findByCreatedByAndIdNot(author.get().getUsername(), currentPostId);
        } else {
            throw new ApiException("Người dùng @" + authorName + " không tồn tại", HttpStatus.NOT_FOUND);
        }
    }

    public int countPostCreateBy(String username) {
        return postRepository.countByCreatedBy(username);
    }

    public Post updateScore(Integer id, int score) {
        {
            Optional<Post> postOptional = postRepository.findById(id);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                post.setScore(score);
                return postRepository.save(post);
            } else {
                throw new ApiException("Không tìm thấy post cần vote", HttpStatus.NOT_FOUND);
            }
        }
    }

    public PostAggregations getDetail(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        String createdBy = post.getCreatedBy();
        User user = userService.getByUsername(createdBy).orElseThrow(() -> new ApiException("Tác giả không tồn tại", HttpStatus.NOT_FOUND));
        return PostAggregations.builder()
                .post(post)
                .user(user).build();

    }
}