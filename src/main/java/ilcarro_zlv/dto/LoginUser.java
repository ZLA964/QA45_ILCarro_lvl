package ilcarro_zlv.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString

public class LoginUser {
    private String email;
    private String password;
}
