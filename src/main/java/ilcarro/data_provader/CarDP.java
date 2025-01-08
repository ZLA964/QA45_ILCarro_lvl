package ilcarro.data_provader;

import ilcarro.dto.CarDto;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ilcarro.utils.PropertiesReader.*;

public class CarDP {

    @DataProvider
    public Iterator<CarDto> dataProviderCarFile() {
        List<CarDto> carList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/data_car.csv"));
//            Haifa;s-123-1;man-1;model-1;2003;//option[@value='Diesel'];4;C;222.2;about;
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(";");  // [Haifa] [s-123-1] [man-1].....
                carList.add(
                        CarDto.builder()
                                .city(splitArray[0])
                                .serialNumber(splitArray[1])
                                .manufacture(splitArray[2])
                                .model(splitArray[3])
                                .year(splitArray[4])
                                .fuel(splitArray[5])
                                .seats(Integer.parseInt(splitArray[6]))
                                .carClass(splitArray[7])
                                .pricePerDay(Double.parseDouble(splitArray[8]))
                                .about(splitArray[9])
                                .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carList.iterator();
    }

    @DataProvider
    public Iterator<CarDto> dataProviderCarFileProperties() {
        List<CarDto> carList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(
                    "src/main/resources/"
                            + getProperty("login.properties", "fileNameDPCar")));

            //            Haifa;s-123-1;man-1;model-1;2003;//option[@value='Diesel'];4;C;222.2;about;
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(";");  // [Haifa] [s-123-1] [man-1].....
                carList.add(
                        CarDto.builder()
                                .city(splitArray[0])
                                .serialNumber(splitArray[1])
                                .manufacture(splitArray[2])
                                .model(splitArray[3])
                                .year(splitArray[4])
                                .fuel(splitArray[5])
                                .seats(Integer.parseInt(splitArray[6]))
                                .carClass(splitArray[7])
                                .pricePerDay(Double.parseDouble(splitArray[8]))
                                .about(splitArray[9])
                                .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carList.iterator();
    }
}
