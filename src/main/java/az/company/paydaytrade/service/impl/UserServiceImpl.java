package az.company.paydaytrade.service.impl;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.RespStatus;
import az.company.paydaytrade.dto.response.Response;
import az.company.paydaytrade.enums.EnumAvailableStatus;
import az.company.paydaytrade.exception.ExceptionConstants;
import az.company.paydaytrade.exception.PaydayTradeException;
import az.company.paydaytrade.repository.UserRepository;
import az.company.paydaytrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Response signUp(UserRequest userRequest) {
        Response response = new Response();
        try {
            if (userRequest.getName() == null || userRequest.getSurname() == null || userRequest.getUsername()==null
            || userRequest.getEmail()==null || userRequest.getPassword()==null) {
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }

            if(!isValidPassword(userRequest.getPassword())){
                throw new PaydayTradeException(ExceptionConstants.INVALID_PASSWORD,"Invalid password!");
            }

            if(userRepository.findByEmailAndStatus(userRequest.getEmail(), EnumAvailableStatus.ACTIVE.getValue())!=null){
                throw new PaydayTradeException(ExceptionConstants.USER_EMAIL_ALREADY_REGISTERED,"Email already registered!")
            }

            if(userRepository.findByUsernameAndStatus(userRequest.getEmail(), EnumAvailableStatus.ACTIVE.getValue())!=null){
                throw new PaydayTradeException(ExceptionConstants.USERNAME_ALREADY_REGISTERED,"Email already registered!")
            }




        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }

        return null;
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=_-])"
                + "(?=\\S+$).{6,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }

}
