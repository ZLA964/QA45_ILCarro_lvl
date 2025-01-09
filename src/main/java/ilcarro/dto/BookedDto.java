package ilcarro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class BookedDto {
    private String email;
    private  String startDate;
    private String endDate;

}
