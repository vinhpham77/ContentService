package org.caykhe.contentservice.repositories;

import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Integer> {
    int countByCreatedBy(String user);
}
