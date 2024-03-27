package com.md.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Response",
        description = "This schema holds the response details."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code of the response",
            example = "201"
    )
    private String statusCode;

    @Schema(
            description = "Status message of the response",
            example = "Account created successfully"
    )
    private String statusMessage;
}
