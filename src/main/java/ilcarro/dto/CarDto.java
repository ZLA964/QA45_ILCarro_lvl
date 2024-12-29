package ilcarro.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC) // Makes the constructor public
@NoArgsConstructor // Adds a constructor with no arguments (default constructor)

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
