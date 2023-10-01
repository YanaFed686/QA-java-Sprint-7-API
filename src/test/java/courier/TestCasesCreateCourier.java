package courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class TestCasesCreateCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesCreateCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    public void cleanTestData() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера по валидным параметрам.Позитивная проверка")
    public void createCourierValidData() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка возможности создания двух курьеров с одинаковыми данными. Негативная проверка")

    public void createTwoCouriersWithTheSameData() {

        /** Step("Создаем нового курьера")
         */
            Response response = createCourier(login, password, firstName);
            setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '201' и сообщение ''ok:true")
         */
            checkStatusCode(response, 201);
            checkMessage(response, "ok", true);

        /** Step("Создаем второго курьера")
         */
           response = createCourier(login, password, firstName);

        /** Step("Проверяем код ответа '409' и сообщение об ошибке")
         */
            checkStatusCode(response, 409);
            checkMessage(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание курьера по пустым параметрам")
    @Description("Проверка возможности создания курьера с незаполненными обязательными параметрами. Негативная проверка")
    public void createCourierNullData() {
        /** Step("Создаем нового курьера с пустыми входными данными")
         */
        Response response = createCourier("", "", "");
        setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '400' и сообщение об ошибке")
         */
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка возможности создания курьера без логина. Негативная проверка")
    public void createCourierWithoutLogin() {
        /** Step("Создаем нового курьера без ввода логина")
         */
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

    /** Step("Проверяем код ответа '400' и сообщение об ошибке")
    */
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка возможности создания курьера без пароля. Негативная проверка")
    public void createCourierWithoutPassword() {
        /** Step("Создаем нового курьера без ввода пароля")
         */
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '400' и сообщение об ошибке")
         */
        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без имени")
    @Description("Проверка возможности создания курьера без имени. Позитивная проверка")
    public void createCourierWithoutFirstName() {
        /** Step("Создаем нового курьера без ввода имени (Данное поле не обязательное")
         */
        Response response = createCourier(login, password, "");
        setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '201' и сообщение 'ok:true'")
         */
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }
}