package okhttp;


import ilcarro.dto.UserDtoLombok;
import ilcarro.utils.BaseApi;
import okhttp3.*;

import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static ilcarro.utils.PropertiesReader.getProperty;


//@Listeners(TestNGListener.class)
public class LoginOkHttpTest implements BaseApi {


    @Test
    public void loginPositiveTest(){
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("login.properties", "email"))
                .password(getProperty("login.properties", "password"))
///                .email("user837@mail.com")
///                .password("Pass-837-word!")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestLoginBody = RequestBody.create(GSON.toJson(user), JSON);
///        System.out.println(requestLoginBody);
        Request requestLogin = new Request.Builder()
                .url(BASE_URL + LOGIN)
                .post(requestLoginBody)
                .build();
///        System.out.println(requestLogin);
        try (Response responseLogin = OK_HTTP_CLIENT.newCall(requestLogin).execute()){
///            System.out.println(responseLogin);
            softAssert.assertTrue(responseLogin.isSuccessful());
            softAssert.assertEquals(responseLogin.code(), 200);
            ResponseBody responseLoginBody = responseLogin.body();
            softAssert.assertNotNull( responseLoginBody, "Response Login body should not be null" );
            String jsonAccessToken = responseLoginBody.string();
            String expected = "{\"accessToken\":\"";
            softAssert.assertTrue(jsonAccessToken.contains(expected));
            String accessToken = jsonAccessToken.substring(expected.length(),jsonAccessToken.length()-2);
///            System.out.println("accessToken-> " + accessToken);
///            System.out.println("jsonAccessToken-> " + jsonAccessToken);

                Files.writeString(Path.of("src/test/resources/properties/accessToken.properties"), accessToken, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_wrongPassword(){
        UserDtoLombok user = UserDtoLombok.builder()
                .username(getProperty("login.properties", "email"))
                .password("1passwordD!")
///                .email("user837@mail.com")
                .build();
        String userJson = GSON.toJson(user);
        System.out.println("userJson ->" + userJson);
        RequestBody requestLoginBody = RequestBody.create(GSON.toJson(user), JSON);
///        System.out.println(requestLoginBody);
        Request requestLogin = new Request.Builder()
                .url(BASE_URL + LOGIN)
                .post(requestLoginBody)
                .build();
///        System.out.println(requestLogin);
        try (Response responseLogin = OK_HTTP_CLIENT.newCall(requestLogin).execute()){
///            System.out.println(responseLogin);
            softAssert.assertFalse(responseLogin.isSuccessful());
            softAssert.assertEquals(responseLogin.code(), 401);
            ResponseBody responseLoginBody = responseLogin.body();
            softAssert.assertNotNull( responseLoginBody, "Response Login body should not be null" );
            String responseBodyAsString = responseLoginBody.string();
            System.out.println(responseBodyAsString);
            String expected = "{\"accessToken\":\"";
            softAssert.assertFalse(responseBodyAsString.contains(expected));
            softAssert.assertTrue(responseBodyAsString.contains("Unauthorized"));
            softAssert.assertTrue(responseBodyAsString.contains("Login or Password incorrect"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }

}
