package org.caykhe.contentservice.dtos;

import lombok.Builder;
import lombok.Data;
import org.caykhe.contentservice.models.Post;

@Data
@Builder
public class PostAggregations {
    private Post post;
    private User user;

}
