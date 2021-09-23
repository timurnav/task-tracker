package com.attlasiam.tracker.repository.users;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;
    private String name;
    private String email;
    private boolean deleted;
}
