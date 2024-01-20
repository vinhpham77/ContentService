package org.caykhe.contentservice.repositories;

import org.caykhe.contentservice.dtos.TagCount;
import org.caykhe.contentservice.models.PostTag;
import org.caykhe.contentservice.models.PostTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {

    @Query("SELECT new org.caykhe.contentservice.dtos.TagCount(t.name, COUNT(t.id)) " +
            "FROM PostTag pt " +
            "JOIN pt.post p " +
            "JOIN pt.tag t " +
            "WHERE p.createdBy = :createdBy " +
            "GROUP BY t.id")
    List<TagCount> countTagsByPost_CreatedBy(@Param("createdBy") String createdBy);

    int countByPost_CreatedByAndTag_Name(String createdBy, String tagName);
}