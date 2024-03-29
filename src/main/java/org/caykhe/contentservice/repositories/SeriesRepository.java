package org.caykhe.contentservice.repositories;


import org.caykhe.contentservice.models.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Integer> {

    Page<Series> findByCreatedBy(String createdBy, Pageable pageable);

    Page<Series> findByCreatedByAndIsPrivateFalse(String createdBy, Pageable pageable);

    Page<Series> findByIsPrivateFalse(Pageable pageable);

    @Query("SELECT s FROM Series s WHERE s.createdBy IN :usernames")
    Page<Series> findByCreatedByInAndIsPrivateFalse(@Param("usernames") List<String> usernames, Pageable pageable);

    @Query("SELECT s FROM Series s WHERE s.title LIKE %:title% AND s.isPrivate = false")
    Page<Series> findByTitleContainingAndIsPrivateFalse(@Param("title") String title, Pageable pageable);

    @Query("SELECT s FROM Series s WHERE s.content LIKE %:content% AND s.isPrivate = false")
    Page<Series> findByContentContainingAndIsPrivateFalse(@Param("content") String content, Pageable pageable);

    @Query("SELECT s FROM Series s WHERE s.createdBy LIKE %:createdBy% AND s.isPrivate = false")
    Page<Series> findByCreatedByContainingAndIsPrivateFalse(@Param("createdBy") String username, Pageable pageable);

    @Query("SELECT s FROM Series s WHERE (s.title LIKE %:searchContent% OR s.createdBy LIKE %:searchContent% OR s.content LIKE %:searchContent%) AND s.isPrivate = false")
    Page<Series> findByTitleOrCreatedByOrContentContainingAndIsPrivateFalse(@Param("searchContent") String searchContent,
                                                                            Pageable pageable);

    Page<Series> findByIdIn(List<Integer> ids, Pageable pageable);

    List<Series> findByIdIn(List<Integer> ids);

    int countByCreatedBy(String createdBy);
}