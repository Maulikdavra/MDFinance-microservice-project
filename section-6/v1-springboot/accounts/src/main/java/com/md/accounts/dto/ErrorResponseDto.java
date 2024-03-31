package com.md.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "This schema holds the error response details."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path where the error occurred",
            example = "/api/md/accounts"
    )
    private String apiPath;

    @Schema(
            description = "HTTP status code of the error",
            example = "400"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message",
            example = "Mobile number must be 10 digits"
    )
    private String errorMessage;

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2021-09-15T10:15:30"
    )
    private LocalDateTime errorTimeStamp;
}
