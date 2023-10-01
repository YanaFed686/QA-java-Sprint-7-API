package org.example;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;


public abstract class BaseRequests {

    public Response postRequest(String url, Object requestBody, String contentType) {
        return given(this.baseRequest(contentType)).log().all()
                .body(requestBody)
                .when()
                .post(url);

    }

    public Response getRequest(String url) {
        return given(this.baseRequest()).log().all()
                .get(url);
    }

    public Response getRequest(String url, Map<String, Object> queryParams) {
        return given(this.baseRequest()).log().all()
                .queryParams(queryParams)
                .when()
                .get(url);
    }

    public Response deleteRequest(String url) {
        return given(this.baseRequest()).log().all()
                .delete(url);
    }

    public Response putRequest(String url, Map<String, Object> queryParams) {
        return given(this.baseRequest()).log().all()
                .queryParams(queryParams)
                .when()
                .put(url);
    }

    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
}
