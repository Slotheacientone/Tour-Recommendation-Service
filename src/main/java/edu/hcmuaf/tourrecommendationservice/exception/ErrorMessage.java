package edu.hcmuaf.tourrecommendationservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
}
