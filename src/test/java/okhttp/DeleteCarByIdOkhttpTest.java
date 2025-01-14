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

import static ilcarro.utils.PropertiesReader.getProperty;

public class DeleteCarByIdOkhttpTest implements BaseApi {
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
    public void deleteCarByIDPositiveTest() {
        String serialNumberFirstElement = "";
        CarDtoApi[] arrayCar = getAllUserCars();
        if (arrayCar != null) {
            serialNumberFirstElement = arrayCar[0].getSerialNumber();
            System.out.println("--> " + serialNumberFirstElement);
        } else
            Assert.fail("method get return null");

        Request request = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CAR+"/"+serialNumberFirstElement)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if(response.isSuccessful()) {
                System.out.println("delete successful");
            } else
                Assert.fail("nethod delete is not successfull --> " + response.code());

        } catch (IOException e) {
            Assert.fail("method delete created exception");

        }


    }

    private CarDtoApi[] getAllUserCars() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_USER_CARS)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                CarsDto carsDto = GSON.fromJson(response.body().string(), CarsDto.class);
                return carsDto.getCars();
            } else {
                System.out.println("Wrong get request --> " + response.code());
                return null;
            }

        } catch (IOException e) {
            System.out.println("Created exeption get user cars");
            e.printStackTrace();
            return null;
        }
    }


}
