package ilcarro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder

public class CarDto {
    String serialNumber;
    String manufacture;
    String model;
    String year;
    String fuel;
    int seats;
    String carClass;
    double pricePerDay;
    String about;
    String city;
    String image;

}
