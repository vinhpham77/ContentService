package org.caykhe.contentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TagCount {
    private String tag;
    private Long count;
}
