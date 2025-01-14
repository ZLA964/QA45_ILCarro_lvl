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
    private String firstName;
    private String lastName;
    private String username;
    private String password;

/*
username*	string
password*	string
pattern: ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$#^&*!])(?=.*[a-zA-Z]).{8,}$
firstName*	string
lastName*	string
*/

}
