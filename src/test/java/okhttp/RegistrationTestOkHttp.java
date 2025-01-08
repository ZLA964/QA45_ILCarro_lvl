package okhttp;

import ilcarro.dto.UserDtoLombok;
import ilcarro.utils.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class RegistrationTestOkHttp implements BaseApi {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest() {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.isSuccessful());
        System.out.println(response.toString());
        System.out.println(response.code());
        try {
            System.out.println(response.body().string());
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
                .username(i + "bob_doemail.com")
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
///        System.out.println(response.toString());
///        System.out.println(response.code());
        try (ResponseBody responseBody = response.body()) {
            String responseBodyJson = responseBody.string();
            Map<String, Object> responceMap = GSON.fromJson(responseBodyJson, Map.class);
///            System.out.println(responseBodyJson);
///            System.out.println(responceMap.get("error"));
///            System.out.println(responceMap.get("message"));
            softAssert.assertFalse(response.isSuccessful());
            softAssert.assertEquals(response.code(), 400);
            softAssert.assertEquals(responceMap.get("error"), "Bad Request");
            softAssert.assertTrue(responceMap.get("message").toString().contains("well-formed email"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertAll();
    }
}
