package ilcarro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder

public class UserDtoLombok {
    private String name;
    private String lastName;
    private String email;
    private String password;


}
