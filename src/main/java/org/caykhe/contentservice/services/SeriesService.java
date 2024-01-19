package org.caykhe.contentservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.ApiException;
import org.caykhe.contentservice.dtos.User;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Series;
import org.caykhe.contentservice.models.SeriesPost;
import org.caykhe.contentservice.repositories.SeriesPostRepository;
import org.caykhe.contentservice.repositories.SeriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final TagService tagService;
    private final UserService userService;
    private final SeriesPostRepository seriesPostRepository;

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
            // commentService.deleteComment(series.getId(), true);
            seriesRepository.delete(series);
        } catch (Exception e) {
            throw new ApiException("Có lỗi xảy ra khi xóa series. Vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    public Series upDateScore(Integer id, int score) {
        {
            Optional<Series> seriesOptional = seriesRepository.findById(id);
            if (seriesOptional.isPresent()) {
                Series series = seriesOptional.get();
                series.setScore(score);
                return seriesRepository.save(series);
            } else {
                throw new ApiException("Không tìm thấy series cần vote", HttpStatus.NOT_FOUND);
            }
        }
    }

    public int countSeriesCreateBy(String username) {
        User user = userService.getByUsername(username)
                .orElseThrow(() -> new ApiException("Tác giả không tồn tại", HttpStatus.NOT_FOUND));
        if (user != null) {
            return seriesRepository.countByCreatedBy(username);
        } else {
            throw new ApiException("User không tồn tại", HttpStatus.NOT_FOUND);
        }
    }


}
