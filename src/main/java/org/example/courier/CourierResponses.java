package org.example.courier;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierResponses {

    private final CourierRequests courierRequests = new CourierRequests();

    private boolean isCreated = false;

    @Step("Отправка запроса на создание курьера")
    public Response createCourier(String login, String password, String firstName) {
        return courierRequests.createCourier(new org.example.apitests.entities.requestentities.Courier(login, password, firstName));
    }

    @Step("Отправка запроса на логин курьера")
    public Response loginCourier(String login, String password) {
        return courierRequests.loginCourier(new org.example.apitests.entities.requestentities.Courier(login, password));
    }
    @Step("Получение ID курьера")
    public Integer getIdCourier(Response response) {

        return response.body().as(CourierAssertions.class).getId();
    }
    @Step("Отправка запроса на удаление курьера")
    public Response deleteCourier(Integer idCourier) {

        return courierRequests.deleteCourier(idCourier);
    }
    @Step("Проверка кода ответа")
    public void checkStatusCode(Response response, int code) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(code);
    }
    @Step("Проверка тела ответа")
    public void checkMessage(Response response, String label, Object body) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body(label, equalTo(body));
    }
    @Step("Проверка, что ID курьера вернулся")
    public void checkCourierIDNotNull(Response response) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body("id", notNullValue());
    }
    public boolean isCourierCreated(Response response, int code) {
        if (response.getStatusCode() != code) return false;

        this.isCreated = true;
        return true;
    }
    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }
    public boolean isCourierCreated() {
        return this.isCreated;
    }
}

