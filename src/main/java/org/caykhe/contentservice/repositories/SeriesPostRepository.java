package org.caykhe.contentservice.repositories;


import org.caykhe.contentservice.models.SeriesPost;
import org.caykhe.contentservice.models.SeriesPostId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesPostRepository extends JpaRepository<SeriesPost, SeriesPostId> {
    List<SeriesPost> findAllBySeriesId(Integer id);
}