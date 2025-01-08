package okhttp;

import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;

public class RegistrationOkHttpTest implements BaseApi {

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
        System.out.println(response);
        System.out.println(response.code());
        try {
            System.out.println(response.body() != null ? response.body().string() : "no response body!");
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
            if (responseBody != null ) {
            String responseBodyJson = responseBody.string();
///            System.out.println(responseBodyJson);
            // Define the type Map<String, Object>
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
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
}
