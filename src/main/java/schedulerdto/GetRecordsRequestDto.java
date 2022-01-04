package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class GetRecordsRequestDto {
    int monthFrom;
    int monthTo;
    int yearFrom;
    int yearTo;
}
