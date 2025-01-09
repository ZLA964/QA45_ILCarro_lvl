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
import java.util.Random;

import static ilcarro.utils.PropertiesReader.getProperty;

public class AddNewCarOkHttpTest implements BaseApi {
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
            if (response.isSuccessful()) {
                tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
                System.out.println(tokenDto.getAccessToken());
            } else {
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
                System.out.println(errorMessageDtoString);
                Assert.fail("Status code response on Login --> " + response.code());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail("Login created exception");
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewCarPositiveTest() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number-" + i)
                .manufacture("Ford")
                .model("Focus")
                .city("Haifa")
                .fuel("Electric")
                .about("aabout my car")
                .image("1.6l")
                .year("2020")
                .seats(5)
                .carClass("A")
                .pricePerDay(345.5)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println(response.isSuccessful() + " code " + response.code());
            if (response.isSuccessful()) {
                softAssert.assertEquals(response.code(), 200);
                String responseBodyAsString = response.body().string();
                System.out.println(responseBodyAsString);
                ResponseMessageDto responseMessageDto = GSON.fromJson(responseBodyAsString, ResponseMessageDto.class);
                softAssert.assertTrue(responseMessageDto.getMessage().equals("Car added successfully"));
                softAssert.assertAll();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
