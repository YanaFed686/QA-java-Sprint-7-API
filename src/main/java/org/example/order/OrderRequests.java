package org.example.order;

import io.restassured.response.Response;
import org.example.BaseRequests;
import org.example.ListOfApiHandlers;

import java.util.HashMap;
import java.util.Map;

public class OrderRequests extends BaseRequests {
    public Response createOrder(Order order) {
        return postRequest(
                ListOfApiHandlers.BASE_URI + ListOfApiHandlers.CREATE_ORDER,
                order,
                "application/json"
        );
    }

    public Response deleteOrder(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return putRequest(
                ListOfApiHandlers.BASE_URI + ListOfApiHandlers.DELETE_ORDER,
                queryParams
        );
    }

    public Response getOrdersList() {
        return getRequest(ListOfApiHandlers.BASE_URI + ListOfApiHandlers.GET_ORDERS_LIST);
    }
}
