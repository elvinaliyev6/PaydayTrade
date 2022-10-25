package az.company.paydaytrade.service;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.Response;
import org.springframework.web.servlet.ModelAndView;

public interface UserService {

     Response signUp(UserRequest userRequest);

    ModelAndView confirmUserAcoount(ModelAndView modelAndView, String confirmationToken);
}
