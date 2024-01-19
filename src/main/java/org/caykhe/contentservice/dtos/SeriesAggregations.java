package org.caykhe.contentservice.dtos;

import lombok.Builder;
import lombok.Data;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Series;

@Data
@Builder
public class SeriesAggregations {
    private Series series;
    private User user;
}
