package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class TestCasesLoginCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesLoginCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        createCourier(login, password, firstName);
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
        @DisplayName("Авторизация курьера")
        @Description("Проверяем авторизацию в систему по валидным параметрам")
        public void loginCourierValidData() {
            Response response = loginCourier(login,password);

            checkStatusCode(response, 200);
            checkCourierIDNotNull(response);
        }

    @Test
    @DisplayName("Авторизация курьера в систему по пустым параметрам")
    @Description("Проверка возможности авторизации курьера с незаполненными обязательными параметрами. Негативная проверка")
    public void loginCourierNullData() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера курьера в систему без логина")
    @Description("Проверка возможности авторизации курьера с незаполненным логином. Негативная проверка")
    public void loginCourierWithoutLogin() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера в систему без пароля")
    @Description("Проверка возможности авторизации курьера с незаполненным паролем. Негативная проверка")
    public void loginCourierWithoutPasword() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера в систему с несуществующим логином")
    @Description("Проверка возможности авторизации курьера с неверным логином. Негативная проверка")
    public void loginCourierWithIncorrectLogin() {
        Response response = loginCourier(login + "qwerty", password);

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
    @Test
    @DisplayName("Авторизация курьера в систему с несуществующим паролем")
    @Description("Проверка возможности авторизации курьера с нневерным паролем. Негативная проверка")
    public void loginCourierWithIncorrectPassword() {
        Response response = loginCourier(login, password + "qwerty");

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
}
