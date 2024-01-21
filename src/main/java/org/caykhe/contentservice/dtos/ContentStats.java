package org.caykhe.contentservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentStats {
    private int postCount;
    private int questionCount;
    private int seriesCount;
}
