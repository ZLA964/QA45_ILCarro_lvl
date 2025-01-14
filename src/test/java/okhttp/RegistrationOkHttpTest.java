package okhttp;

import com.google.gson.reflect.TypeToken;
import ilcarro.dto.ErrorMessageDtoString;
import ilcarro.dto.TokenDto;
import ilcarro.dto.UserDtoLombok;
import ilcarro.utils.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;

public class RegistrationOkHttpTest implements BaseApi {

//    SoftAssert softAssert = new SoftAssert();

    @Test(invocationCount = 1)
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000) + 2000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("Doe")
                .username(i + "bob_doe@mail.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
///        System.out.println(response.isSuccessful());
///        System.out.println(response);
///        System.out.println(response.code());
        try {
            System.out.println(response.body() != null ? response.body().string() : "no response body!");
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        int i = new Random().nextInt(1000) + 1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("Doe")
                .username(i + "bob_doeemail.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
//          Response response;
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
///            System.out.println(response);
///            System.out.println(response.isSuccessful());
            softAssert.assertFalse(response.isSuccessful());
///            System.out.println(response.code());
            softAssert.assertEquals(response.code(), 400);

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyJson = responseBody.string();
///            System.out.println(responseBodyJson);
                // Define the type Map<String, Object>
                Type mapType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Map<String, Object> responseMap = GSON.fromJson(responseBodyJson, mapType);

///            System.out.println(responseMap.get("error"));
                softAssert.assertEquals(responseMap.get("error"), "Bad Request");
///            System.out.println(responseMap.get("message"));
                softAssert.assertTrue(responseMap.get("message").toString().contains("well-formed email"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_emptyLastName() {
        int i = new Random().nextInt(1000) + 1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("")
                .username(i + "bob_doe@email.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertFalse(response.isSuccessful());
            softAssert.assertEquals(response.code(), 400);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyJson = responseBody.string();
///               System.out.println(responseBodyJson);
                // Define the type Map<String, Object>
                Type mapType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Map<String, Object> responseMap = GSON.fromJson(responseBodyJson, mapType);
                softAssert.assertEquals(responseMap.get("error"), "Bad Request");
                softAssert.assertTrue(responseMap.get("message").toString().contains("must not be blank"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_lastNameBlank() {
        int i = new Random().nextInt(1000) + 1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName(" ")
                .username(i + "bob_doe@email.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertFalse(response.isSuccessful());
            softAssert.assertEquals(response.code(), 400);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyJson = responseBody.string();
 /*               // Define the type Map<String, Object>
                Type mapType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Map<String, Object> responseMap = GSON.fromJson(responseBodyJson, mapType);
                softAssert.assertEquals(responseMap.get("error"), "Bad Request");
                softAssert.assertTrue(responseMap.get("message").toString().contains("must not be blank"));
 */
                softAssert.assertTrue(responseBodyJson.contains("Bad Request"));
                softAssert.assertTrue(responseBodyJson.contains("must not be blank"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_duplicateUser() {
        int i = new Random().nextInt(1000) + 1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("Doe")
                .username(i + "bob_doe@mail.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                response = OK_HTTP_CLIENT.newCall(request).execute();
///                System.out.println(response.isSuccessful());
///                System.out.println(response);
///                System.out.println(response.code());
                softAssert.assertEquals(response.code(), 400);
                String responseBodyString = response.body().string();
                System.out.println(responseBodyString);
                softAssert.assertTrue(responseBodyString.contains("Bad Request"));
                softAssert.assertTrue(responseBodyString.contains("User already exists"));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }


    @Test // (invocationCount = 2)
    public void registrationPositiveTestValidateToken() {
        int i = new Random().nextInt(1000) + 2000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("Doe")
                .username(i + "bob_doe@mail.com")
                .password("Pass123!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
//        Response response;
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                TokenDto tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
                System.out.println(tokenDto);
                Assert.assertFalse(tokenDto.getAccessToken().isBlank());
            } else {
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
///                System.out.println(errorMessageDtoString);
                Assert.fail("Status code response --> " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Created exception");
        }
    }

    @Test
    public void registrationNegative_wrongPassword_Test() {
        int i = new Random().nextInt(1000) + 1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .firstName("Bob")
                .lastName("Doe")
                .username(i + "bob_doe@mail.com")
                .password("Pass123qw")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                ErrorMessageDtoString errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
                System.out.println(errorMessageDto);
                softAssert.assertEquals(response.code(), 400);
                softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
                softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("At least 8 characters"));
                softAssert.assertAll();
            } else {
                ErrorMessageDtoString errorMessageDtoString =
                        GSON.fromJson(response.body().string(), ErrorMessageDtoString.class);
                System.out.println(errorMessageDtoString);
                Assert.fail("Status code response --> " + response.code());
            }

        } catch (IOException e) {
            Assert.fail("Created exception");
            e.printStackTrace();
        }

    }
}

