package okhttp;

import ilcarro.dto.*;
import ilcarro.utils.BaseApi;
import ilcarro.utils.TestNGListener;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


import static ilcarro.utils.PropertiesReader.getProperty;


@Listeners(TestNGListener.class)
public class CarsOkHttpTest implements BaseApi {
    Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    TokenDto tokenDto;

    @BeforeClass
    public void login() {
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("login.properties", "email"))        // "user837@mail.com")
                .password(getProperty("login.properties", "password"))     // "Pass-837-word!")
                .build();
        RequestBody requestLoginBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN)
                .post(requestLoginBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
                System.out.println("login success");
                logger.info("\n\t\t == login success");
            } else {
                if (response.body() != null) {
                    ErrorMessageDtoString errorMessageDtoString =
                            GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
                    System.out.println(errorMessageDtoString);
                }
                Assert.fail("Status code response on Login --> " + response.code());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            Assert.fail("Login created exception");
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getUserCarsTest() {
        String user = getProperty("login.properties", "email");
        Request requestGetUserCars = new Request.Builder()
                .url(BASE_URL + GET_USER_CARS)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(requestGetUserCars).execute()) {
///            System.out.println(requestGetUserCars);
            if (response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 200);
                String responseBodyAsString = response.body().string();
///                System.out.println(responseBodyAsString);
                CarsDto userCars = GSON.fromJson(responseBodyAsString, CarsDto.class);
                CarDtoApi[] cars = userCars.getCars();  // it is not good ))
                boolean checkIn = Arrays.stream(cars).allMatch(carDtoApi -> carDtoApi.getOwner().equals(user));
                System.out.println(checkIn);
                if (checkIn)
///                    System.out.println("All " + cars.length + " car owners are user");
                    logger.info("\n\t\t == All " + cars.length + " car owners are user");
                Assert.assertTrue(checkIn, "car owners are not user");
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<String> getUserCarNumbers() {
        Request requestGetUserCars = new Request.Builder()
                .url(BASE_URL + GET_USER_CARS)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(requestGetUserCars).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 200);
                String responseBodyAsString = response.body().string();
                CarsDto userCars = GSON.fromJson(responseBodyAsString, CarsDto.class);
                CarDtoApi[] cars = userCars.getCars();
                ///                System.out.println(numbers);
                return Arrays.stream(cars).map(CarDtoApi::getSerialNumber)
                        .collect(Collectors.toCollection(ArrayList::new));
            } else {
                System.out.println("No cars! \n response status code --> " + response.code());
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteCarTest() {
        ArrayList<String> numbers = getUserCarNumbers();
        if (numbers != null) {
            int countOfCars = numbers.size();
///            System.out.println("countOfCars -> " + countOfCars);
            logger.info("\n\t\t == countOfCars -> " + countOfCars);
            if (countOfCars > 0) {
                String lastNumber = numbers.get(countOfCars - 1);
                Request requestGetUserCars = new Request.Builder()
                        .url(BASE_URL + DELETE_CAR + lastNumber)
                        .addHeader(AUTH, tokenDto.getAccessToken())
                        .delete()
                        .build();
                try (Response response = OK_HTTP_CLIENT.newCall(requestGetUserCars).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        softAssert.assertEquals(response.code(), 200);
                        String responseBodyAsString = response.body().string();
///                    System.out.println(responseBodyAsString);
                        ResponseMessageDto responseMessageDto =
                                GSON.fromJson(responseBodyAsString, ResponseMessageDto.class);
                        String expectedMessage = "Car deleted successfully";
                        softAssert.assertEquals(responseMessageDto.getMessage(), expectedMessage);
///                        System.out.println(expectedMessage);
                        logger.info("\n\t\t == "+expectedMessage);
                        softAssert.assertEquals(getUserCarNumbers().size(), countOfCars - 1);
///                        System.out.println("new countOfCars -> " + countOfCars - 1);
                        logger.info("\n\t\t == new countOfCars -> " + (countOfCars - 1));
                        softAssert.assertAll();
                    } else {
                        Assert.fail("response status code --> " + response.code());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Assert.fail("No cars for delete!");
            }
        } else {
            Assert.fail("All is bad! Could not take list numbers of cars");
        }
    }

}
