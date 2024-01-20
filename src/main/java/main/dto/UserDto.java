package main.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserDto {
    private String userName;
    private String userEmail;
    private LocalDateTime createdTime;
}
