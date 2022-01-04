package schedulerdto;

//"date": {
//        "dayOfMonth": 0,
//        "dayOfWeek": "string",
//        "month": 0,
//        "year": 0
//        },

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class DateDto {
    int dayOfMonth;
    String dayOfWeek;
    int month;
    int year;
}
