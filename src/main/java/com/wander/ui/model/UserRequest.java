package com.wander.ui.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRequest {
    private String name;
    private String email;
    private String password;
}
