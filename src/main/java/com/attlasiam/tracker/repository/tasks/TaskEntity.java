package com.attlasiam.tracker.repository.tasks;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private Long id;
    private String title;
    private String description;
    private Long reporterId;
    private Long assigneeId;
}
