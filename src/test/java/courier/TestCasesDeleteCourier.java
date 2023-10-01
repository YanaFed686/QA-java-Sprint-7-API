package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.CourierResponses;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;



public class TestCasesDeleteCourier extends CourierResponses {
    private String login;
    private String password;
    private String firstName;

    public TestCasesDeleteCourier() {
    }

    @Before
    public void testData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @Test
    @DisplayName("Удаление курьера")
    @Description("Проверка удаления курьера по валидным параметрам.Позитивная проверка")
    public void deleteCourierValidData() {
        /** Step("Создаем нового курьера")
         */
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '201' и сообщение ''ok:true")
         */
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        /** Step("Удаляем курьера")
         */
        Integer idCourier = getIdCourier(loginCourier(login, password));

        response = deleteCourier(idCourier);


        /** Step(Проверяем код ответа '201' и сообщение ''ok:true'")
         */
        checkStatusCode(response, 200);
        checkMessage(response, "ok", true);

    }

    @Test
    @DisplayName("Удаление курьера с неверным id")
    @Description("Проверка возможности удаления курьера с несуществующим id. Негативная проверка")
    public void deleteCourierWithWrongId() {
        /** Step("Создаем нового курьера")
         */
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        /** Step("Проверяем код ответа '201' и сообщение ''ok:true")
         */
        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        /** Step("Удаляем курьера, подставляя несуществующий id")
         */
        Integer idCourier = 0;

        response = deleteCourier(idCourier);

        /** Step("Проверяем код ответа '404' и сообщение об ошибке")
         */
        checkStatusCode(response, 404);
        checkMessage(response, "message", "Курьера с таким id нет.");
    }
}