package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class AuthResponceDto {
    boolean registration;
    String status;
    String token;
}
