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
import java.lang.reflect.Field;
import java.util.Random;

import static ilcarro.utils.PropertiesReader.getProperty;

public class AddNewCarOkHttpTest implements BaseApi {
    private TokenDto tokenDto;
    UserDtoLombok user;

    @BeforeClass(alwaysRun = true)
    public void login() {
        user = UserDtoLombok.builder()
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
///                System.out.println(tokenDto.getAccessToken());
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
                .about("about my car")
                .image("1.6l")
                .year("2020")
                .seats(5)
                .carClass("A")
                .pricePerDay(345.5)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
//        System.out.println(tokenDto.getAccessToken());
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println(response.isSuccessful() + " code " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 200);
                String responseBodyAsString = response.body().string();
 ///               System.out.println(responseBodyAsString);
                ResponseMessageDto responseMessageDto = GSON.fromJson(responseBodyAsString, ResponseMessageDto.class);
                softAssert.assertTrue(responseMessageDto.getMessage().equals("Car added successfully"));
                softAssert.assertAll();
            } else {
                if (response.body() != null) {
                    ErrorMessageDtoString errorMessageDtoString =
                            GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
                    System.out.println(errorMessageDtoString);
                }
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewCarNegativeTest_noLocation() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number-" + i)
                .manufacture("Ford")
                .model("Focus")
                .city("")
                .fuel("Electric")
                .about("about my car")
                .image("1.6l")
                .year("2020")
                .seats(5)
                .carClass("B")
                .pricePerDay(345.25)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println(response.isSuccessful() + " code " + response.code());
            if (!response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 400);
                String responseBodyAsString = response.body().string();
///                System.out.println(responseBodyAsString);
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(responseBodyAsString, ErrorMessageDtoString.class);
///                System.out.println(errorMessageDtoString);
///                System.out.println(errorMessageDtoString.getMessage());
                CarDtoApi wrongCarDtoApi =
                        GSON.fromJson(errorMessageDtoString.getMessage(), CarDtoApi.class);
///                System.out.println(wrongCarDtoApi);
                softAssert.assertTrue(hasMustNotBeSubstring(wrongCarDtoApi));
                softAssert.assertAll();
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean hasMustNotBeSubstring(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value instanceof String && ((String) value).contains("must not be")) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
        return false;
    }

    @Test   //  BAG !!!
    public void addNewCarNegativeTest_wrongSeats() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number-" + i)
                .manufacture("") //Ford
                .model("Ford")
                .city("Haifa")
                .fuel("Electric")
                .about("about my car")
                .image("1.6l")
                .year("2020")
                .seats(-1)
                .carClass("B")
                .pricePerDay(345.25)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Is response successful -> " + response.isSuccessful() + " code " + response.code());
            if (!response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 400);
                String responseBodyAsString = response.body().string();
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(responseBodyAsString, ErrorMessageDtoString.class);
                CarDtoApi wrongCarDtoApi =
                        GSON.fromJson(errorMessageDtoString.getMessage(), CarDtoApi.class);
                softAssert.assertTrue(hasMustNotBeSubstring(wrongCarDtoApi));
                softAssert.assertAll();
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewCarNegativeTest_blankFuel() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number-" + i)
                .manufacture("Ford")
                .model("Focus")
                .city("Haifa")
                .fuel("")
                .about("about my car")
                .image("1.6l")
                .year("2020")
                .seats(5)
                .carClass("B")
                .pricePerDay(345.25)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("addNewCarNegativeTest_blankFuel-> "  +
                    response.isSuccessful() + " code " + response.code());
            if (!response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 400);
                String responseBodyAsString = response.body().string();
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(responseBodyAsString, ErrorMessageDtoString.class);
                CarDtoApi wrongCarDtoApi =
                        GSON.fromJson(errorMessageDtoString.getMessage(), CarDtoApi.class);
                softAssert.assertTrue(hasMustNotBeSubstring(wrongCarDtoApi));
                softAssert.assertAll();
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewCarNegativeTest_401() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number-" + i)
                .manufacture("Ford")
                .model("Focus")
                .city("")
                .fuel("Electric")
                .about("about my car")
                .image("1.6l")
                .year("2020")
                .seats(5)
                .carClass("B")
                .pricePerDay(345.25)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        TokenDto tokenDto401 = TokenDto.builder()
                .accessToken(tokenDto.getAccessToken() + ".")
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto401.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println(response.isSuccessful() + " code " + response.code());
            if (!response.isSuccessful() && response.body() != null) {
                softAssert.assertEquals(response.code(), 401);
                String responseBodyAsString = response.body().string();
                System.out.println(responseBodyAsString);
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(responseBodyAsString, ErrorMessageDtoString.class);
                System.out.println(errorMessageDtoString);
                System.out.println(errorMessageDtoString.getError());
                softAssert.assertEquals(errorMessageDtoString.getError(), "Unauthorized");
                softAssert.assertAll();
            } else {
                Assert.fail("response status code --> " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
