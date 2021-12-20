package dto;

//{
//        "token": "string"
//        }

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class AuthResponceDto {
    String token;
}
