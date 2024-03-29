package org.caykhe.contentservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.ApiException;
import org.caykhe.contentservice.dtos.ResultCount;
import org.caykhe.contentservice.dtos.SeriesDto;
import org.caykhe.contentservice.dtos.UserLatestPageable;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Series;
import org.caykhe.contentservice.models.SeriesPost;
import org.caykhe.contentservice.models.SeriesPostId;
import org.caykhe.contentservice.repositories.SeriesPostRepository;
import org.caykhe.contentservice.repositories.SeriesRepository;
import org.caykhe.contentservice.utils.ConverterUtils;
import org.caykhe.contentservice.utils.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final PostService postService;
    private final SeriesPostRepository seriesPostRepository;

    private static List<SeriesPost> createSeriesPosts(Series series, List<Post> posts) {
        return posts.stream()
                .map(post -> SeriesPost
                        .builder()
                        .id(SeriesPostId.builder().postId(post.getId()).seriesId(series.getId()).build())
                        .series(series)
                        .post(post)
                        .build()
                ).toList();
    }

    public Series get(Integer id) {
        var series = seriesRepository.findById(id)
                .orElseThrow(() -> new ApiException("Series không tồn tại", HttpStatus.NOT_FOUND));

        String requester = SecurityContextHolder.getContext().getAuthentication().getName();
        String createdBy = series.getCreatedBy();

        if (series.getIsPrivate() && !createdBy.equals(requester)) {
            throw new ApiException("Series tạm thời không có sẵn với mọi người", HttpStatus.FORBIDDEN);
        }

        return series;
    }

    public SeriesDto getDto(Integer id) {
        return convertToDto(get(id));
    }

    @Transactional
    public ResultCount<SeriesDto> getByUser(String createdBy, Integer page, Integer size) {
        Page<Series> seriesPage;
        UserLatestPageable result = PaginationUtils.getUserLatestPageable(page, size);

        if (result.requester().equals(createdBy)) {
            seriesPage = seriesRepository.findByCreatedBy(createdBy, result.pageable());
        } else {
            seriesPage = seriesRepository.findByCreatedByAndIsPrivateFalse(createdBy, result.pageable());
        }

        List<SeriesDto> seriesDtos = seriesPage.getContent().stream()
                .map(this::convertToDto)
                .toList();

        long count = seriesPage.getTotalElements();

        return new ResultCount<>(seriesDtos, count);
    }

    @Transactional
    public Series create(SeriesDto seriesDto) {
        var requester = SecurityContextHolder.getContext().getAuthentication().getName();

        Series series = Series
                .builder()
                .title(seriesDto.getTitle())
                .content(seriesDto.getContent())
                .isPrivate(seriesDto.getIsPrivate())
                .createdBy(requester)
                .build();

        try {
            return seriesRepository.save(series);
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi tạo series. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Series update(Integer id, SeriesDto seriesDto) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ApiException("Series không tồn tại", HttpStatus.NOT_FOUND));

        String createdBy = series.getCreatedBy();
        String requester = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!createdBy.equals(requester)) {
            throw new ApiException("Bạn không có quyền chỉnh sửa series này", HttpStatus.FORBIDDEN);
        }

        series.setTitle(seriesDto.getTitle());
        series.setContent(seriesDto.getContent());
        series.setIsPrivate(seriesDto.getIsPrivate());
        series.setUpdatedAt(new Date().toInstant());

        List<Post> posts = seriesDto.getPostIds().stream()
                .map(postService::get)
                .toList();

        try {
            Series savedSeries = seriesRepository.save(series);
            List<SeriesPost> newSeriesPosts = createSeriesPosts(savedSeries, posts);
            List<SeriesPost> currentSeriesPosts = seriesPostRepository.findAllBySeriesId(id);

            List<SeriesPost> seriesPostsToDelete = new ArrayList<>(currentSeriesPosts);
            seriesPostsToDelete.removeAll(newSeriesPosts);

            List<SeriesPost> seriesPostsToAdd = new ArrayList<>(newSeriesPosts);
            seriesPostsToAdd.removeAll(currentSeriesPosts);

            seriesPostRepository.deleteAll(seriesPostsToDelete);
            seriesPostRepository.saveAll(seriesPostsToAdd);

            return savedSeries;
        } catch (Exception e) {
            throw new ApiException(
                    "Có lỗi xảy ra khi cập nhật series. Vui lòng thử lại!",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void delete(Integer id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ApiException("Series không tồn tại", HttpStatus.NOT_FOUND));

        String createdBy = series.getCreatedBy();
        String requester = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!createdBy.equals(requester)) {
            throw new ApiException("Bạn không có quyền xóa series này", HttpStatus.FORBIDDEN);
        }

        try {
            seriesRepository.delete(series);
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi xóa series. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SeriesDto convertToDto(Series series) {
        return ConverterUtils.convertSeriesDto(series, seriesPostRepository);
    }


    @Transactional
    public ResultCount<SeriesDto> getSeries(Integer page, Integer size) {
        Page<Series> seriesPage;
        Pageable pageable = (page == null || size == null || page < 1 || size <= 1)
                ? Pageable.unpaged()
                : PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"));

        seriesPage = seriesRepository.findByIsPrivateFalse(pageable);

        List<SeriesDto> seriesDtos = seriesPage.getContent().stream()
                .map(this::convertToDto)
                .toList();

        long count = seriesPage.getTotalElements();

        return new ResultCount<>(seriesDtos, count);
    }

    public List<Post> getListPost(Integer seriesId) {
        Optional<Series> seriesOptional = seriesRepository.findById(seriesId);
        List<Post> postList = new ArrayList<>();
        if (seriesOptional.isPresent()) {
            List<SeriesPost> seriesPostList = seriesPostRepository.findAllBySeriesId(seriesId);
            for (SeriesPost seriesPost : seriesPostList) {
                postList.add(Post.builder()
                        .isPrivate(seriesPost.getPost().getIsPrivate())
                        .tags(seriesPost.getPost().getTags())
                        .score(seriesPost.getPost().getScore())
                        .updatedAt(seriesPost.getPost().getUpdatedAt())
                        .id(seriesPost.getPost().getId())
                        .commentCount(seriesPost.getPost().getCommentCount())
                        .createdBy(seriesPost.getPost().getCreatedBy())
                        .title(seriesPost.getPost().getTitle())
                        .content(seriesPost.getPost().getContent())
                        .build());
            }
            return postList;
        } else {
            throw new ApiException("Không tìm thấy series", HttpStatus.NOT_FOUND);
        }

    }

    public Series updateScore(Integer id, int score) {
        {
            Optional<Series> seriesOptional = seriesRepository.findById(id);
            if (seriesOptional.isPresent()) {
                Series series = seriesOptional.get();
                series.setScore(score);
                return seriesRepository.save(series);
            } else {
                throw new ApiException("Không tìm thấy post cần vote", HttpStatus.NOT_FOUND);
            }
        }
    }

    public int countSeriesCreateBy(String username) {
        return seriesRepository.countByCreatedBy(username);
    }

    @Transactional
    public ResultCount<SeriesDto> getSerieByUsername(List<String> usernames, Integer page, Integer size) {
        Page<Series> seriesPage;
        Pageable pageable = (page == null || size == null || page < 1 || size <= 1)
                ? Pageable.unpaged()
                : PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"));

        seriesPage = seriesRepository.findByCreatedByInAndIsPrivateFalse(usernames, pageable);

        List<SeriesDto> seriesDtos = seriesPage.getContent().stream()
                .map(this::convertToDto)
                .toList();

        long count = seriesPage.getTotalElements();

        return new ResultCount<>(seriesDtos, count);
    }

    @Transactional
    public ResultCount<SeriesDto> search(String fieldSearch, String searchContent, String sort, String sortField, Integer page, Integer limit) {
        Page<Series> seriesPage;
        sortField = sortField.isEmpty() ? "updatedAt" : sortField;
        Pageable pageable = (page == null || limit == null || page < 1 || limit < 1)
                ? Pageable.unpaged()
                : PageRequest.of(page - 1, limit, Sort.by("ASC".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));

        seriesPage = switch (fieldSearch) {
            case "title" -> seriesRepository.findByTitleContainingAndIsPrivateFalse(searchContent, pageable);
            case "content" -> seriesRepository.findByContentContainingAndIsPrivateFalse(searchContent, pageable);
            case "username" -> seriesRepository.findByCreatedByContainingAndIsPrivateFalse(searchContent, pageable);
            case "" ->
                    seriesRepository.findByTitleOrCreatedByOrContentContainingAndIsPrivateFalse(searchContent, pageable);
            default ->
                    throw new ApiException("Lỗi! Không thể tìm kiếm theo trường " + fieldSearch, HttpStatus.BAD_REQUEST);
        };

        try {
            List<SeriesDto> seriesDtos = seriesPage.getContent().stream()
                    .map(this::convertToDto)
                    .toList();

            long count = seriesPage.getTotalElements();

            return new ResultCount<>(seriesDtos, count);
        } catch (Exception e) {
            throw new ApiException("Lỗi! không thể tim kiếm", HttpStatus.FORBIDDEN);
        }
    }

    public void updateCommentCount(Integer id, int commentCount) {
        {
            Optional<Series> seriesOptional = seriesRepository.findById(id);
            if (seriesOptional.isPresent()) {
                Series series = seriesOptional.get();
                series.setCommentCount(commentCount);
                seriesRepository.save(series);
            } else {
                throw new ApiException("Không tìm thấy series cần cập nhật comment count", HttpStatus.NOT_FOUND);
            }
        }
    }

    @Transactional
    public List<SeriesDto> getSeriesByIds(List<Integer> ids) {
        List<Series> series = seriesRepository.findByIdIn(ids);
        List<SeriesDto> seriesDtos = series.stream()
                .map(this::convertToDto)
                .toList();
        return seriesDtos;
    }
}
