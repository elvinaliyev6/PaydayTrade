package az.company.paydaytrade.service;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.Response;

public interface UserService {

    public Response signUp(UserRequest userRequest);

}
