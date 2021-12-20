package dto;


//{
//        "code": 0,
//        "details": "string",
//        "message": "string",
//        "timestamp": "2021-12-19T17:14:13.378Z"
//        }

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class ErrorDto {

    int code;
    String details;
    String message;

}
