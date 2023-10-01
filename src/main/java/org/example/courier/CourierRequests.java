package org.example.courier;
import org.example.BaseRequests;
import org.example.apitests.entities.requestentities.Courier;
import io.restassured.response.Response;
import org.example.ListOfApiHandlers;

public class CourierRequests extends BaseRequests {
    public Response createCourier(Courier courier) {
        return postRequest(
                ListOfApiHandlers.BASE_URI + ListOfApiHandlers.CREATE_COURIER,
                courier,
                "application/json"
        );
    }

    public Response loginCourier(Courier courier) {
        return postRequest(
                ListOfApiHandlers.BASE_URI + ListOfApiHandlers.LOGIN_COURIER,
                courier,
                "application/json"
        );
    }

    public Response deleteCourier(Integer idCourier) {
        return deleteRequest(ListOfApiHandlers.BASE_URI + ListOfApiHandlers.DELETE_COURIER + idCourier);
    }
}
