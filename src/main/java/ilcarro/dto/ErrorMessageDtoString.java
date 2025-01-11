package ilcarro.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder

public class ErrorMessageDtoString {

    private String timestamp;
    private int status;
    private String error;
//    private Object message;
    private JsonElement message;
    private String path;

}
