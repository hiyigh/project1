package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Share {
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
