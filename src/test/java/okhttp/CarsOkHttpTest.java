package okhttp;

import ilcarro.dto.*;
import ilcarro.utils.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;


import static ilcarro.utils.PropertiesReader.getProperty;

public class CarsOkHttpTest implements BaseApi {

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
    public void getUserCars() {
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
                    System.out.println("All " + cars.length + " car owners are user");
                Assert.assertTrue(checkIn, "car owners are not user");
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}