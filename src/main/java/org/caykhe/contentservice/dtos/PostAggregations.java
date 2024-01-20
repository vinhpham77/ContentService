package org.caykhe.contentservice.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.caykhe.contentservice.models.Post;
import org.caykhe.contentservice.models.Tag;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class PostAggregations {
    private Post post;
    private User user;

}
