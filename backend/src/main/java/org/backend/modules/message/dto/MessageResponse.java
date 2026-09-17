package org.backend.modules.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.modules.user.dto.UserSummaryResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MessageResponse {

    private Long id;
    private String content;
    private LocalDateTime sentAt;
    private UserSummaryResponse sender;
}