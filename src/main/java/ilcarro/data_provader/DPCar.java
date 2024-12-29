package ilcarro.data_provader;

import ilcarro.dto.CarDto;
import ilcarro.utils.Fuel;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ilcarro.utils.RandomUtils.*;


public class DPCar {
    static Random random = new Random();

    @DataProvider
    public CarDto[] carsDtoForAddtests() {
        int countCar = 3;
        String city = generateString(1);
        List<CarDto> carDtos = Stream.generate(() -> {
                    return CarDto.builder()
                            .serialNumber(random.nextInt(1000) + "-055")
                            .city("Haifa")
                            .manufacture(generateString(6))
                            .model(generateString(7))
                            .year(Integer.toString(1990 + random.nextInt(34)))
                            .fuel(Fuel.DIESEL.getLocator())
                            .seats(2 + random.nextInt(9))
                            .carClass("A")
                            .pricePerDay(123.99)
                            .about("About my car" + generateStringTextOnly_stream(30))
                            .build();
                })
                .limit(countCar)
                .collect(Collectors.toList());
        return carDtos.toArray(new CarDto[0]);
    }

    @DataProvider
    public Iterator<CarDto> carsAddFileDP() {
        List<CarDto> carsList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(
                    "src/main/resources/data_provider/cars.csv"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                String fuelType = fields[4].toUpperCase();
                carsList.add(CarDto.builder()
                        .serialNumber(fields[0])
                        .manufacture(fields[1])
                        .model(fields[2])
                        .year(fields[3])
                        .fuel(Fuel.valueOf(fuelType).getLocator())
                        .seats(Integer.parseInt(fields[5]))
                        .carClass(fields[6])
                        .pricePerDay(Double.parseDouble(fields[7]))
                        .about(fields[8])
                        .city(fields[9])
                        .image(fields[10])
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carsList.listIterator();
    }

/*
 CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("Mazda")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
 */


}
