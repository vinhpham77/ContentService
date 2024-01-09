package org.caykhe.contentservice.repositories;

import org.caykhe.contentservice.models.Post;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByCreatedBy(String createdBy, Pageable pageable);

    Page<Post> findByCreatedByAndIsPrivateFalse(String createdBy, Pageable pageable);

    Page<Post> findByCreatedByAndTagsName(String createdBy, String tag, Pageable pageable);

    Page<Post> findByCreatedByAndTagsNameAndIsPrivateFalse(String createdBy, String tag, Pageable pageable);

    Page<Post> findByIsPrivateFalse(Pageable pageable);

    Page<Post> findByTagsNameAndIsPrivateFalse(String tag, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.createdBy IN :usernames")
    Page<Post> findByCreatedByInAndIsPrivateFalse(@Param("usernames") List<String> usernames, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE p.createdBy IN :usernames AND t.name = :tag")
    Page<Post> findByCreatedByInAndTagsNameAndIsPrivateFalse(@Param("usernames") List<String> usernames, @Param("tag") String tag, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title% AND p.isPrivate = false")
    Page<Post> findByTitleContainingAndIsPrivateFalse(@Param("title") String title, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.createdBy LIKE %:displayName% AND p.isPrivate = false")
    Page<Post> findByCreatedByContainingAndIsPrivateFalse(@Param("displayName") String displayName, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.name LIKE %:tag% AND p.isPrivate = false")
    Page<Post> findByTagsNameContainingAndIsPrivateFalse(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:content% AND p.isPrivate = false")
    Page<Post> findByContentContainingAndIsPrivateFalse(@Param("content") String content, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE (p.title LIKE %:searchContent% OR p.createdBy LIKE %:searchContent% OR t.name LIKE %:searchContent% OR p.content LIKE %:searchContent%) AND p.isPrivate = false")
    Page<Post> findByTitleOrCreatedByOrTagsNameOrContentContainingAndIsPrivateFalse(@Param("searchContent") String searchContent,
                                                                                    Pageable pageable);

    Page<Post> findByIdIn(List<Integer> ids, Pageable pageable);

    Page<Post> findByIdInAndTagsName(List<Integer> ids, String tag, Pageable pageable);

    int countByCreatedBy(String createdBy);
    List<Post> findByCreatedByAndIdNot(@NotNull String createdBy, Integer id);
}