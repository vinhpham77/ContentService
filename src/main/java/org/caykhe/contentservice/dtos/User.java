package org.caykhe.contentservice.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private LocalDate birthdate;
    private String displayName;
    private String bio;
    private Role role;
    private Boolean gender;
}