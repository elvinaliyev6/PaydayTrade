package az.company.paydaytrade.service.impl;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.RespStatus;
import az.company.paydaytrade.dto.response.Response;
import az.company.paydaytrade.entity.ConfirmationToken;
import az.company.paydaytrade.entity.User;
import az.company.paydaytrade.enums.StatusAndAccountConfirmed;
import az.company.paydaytrade.exception.ExceptionConstants;
import az.company.paydaytrade.exception.PaydayTradeException;
import az.company.paydaytrade.repository.ConfirmationTokenRepository;
import az.company.paydaytrade.repository.UserRepository;
import az.company.paydaytrade.service.UserService;
import az.company.paydaytrade.util.Converter;
import az.company.paydaytrade.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailService mailService;

    @Override
    public Response signUp(UserRequest userRequest) {
        Response response = new Response();
        try {
            if (userRequest.getName() == null || userRequest.getSurname() == null || userRequest.getUsername() == null
                    || userRequest.getEmail() == null || userRequest.getPassword() == null) {
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }

            if (!isValidPassword(userRequest.getPassword())) {
                throw new PaydayTradeException(ExceptionConstants.INVALID_PASSWORD, "Invalid password!");
            }

            if (userRepository.findByEmailAndStatus(userRequest.getEmail(), StatusAndAccountConfirmed.ACTIVE.getValue()) != null) {
                throw new PaydayTradeException(ExceptionConstants.USER_EMAIL_ALREADY_REGISTERED, "Email already registered!");
            }

            if (userRepository.findByUsernameAndStatus(userRequest.getEmail(), StatusAndAccountConfirmed.ACTIVE.getValue()) != null) {
                throw new PaydayTradeException(ExceptionConstants.USERNAME_ALREADY_REGISTERED, "Email already registered!");
            }

            response.setStatus(RespStatus.getSuccessMessage());

            User user = Converter.convertFromUserRequest(userRequest);
            userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            String text = "To confirm your account please click here: "
                    + "http://localhost:8082/paydaytrade/user/confirm-account?token=" + confirmationToken.getConfirmationToken();

            mailService.sendEmail(user.getEmail(), "Complete Registration!", "test.expressbank@gmail.com", text);

            confirmationTokenRepository.save(confirmationToken);

        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public ModelAndView confirmUserAcoount(ModelAndView modelAndView, String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        try {
            if (token == null) {
                modelAndView.addObject("message", "The link is invalid or broken!");
                modelAndView.setViewName("error");
            } else {
                User user = userRepository.findByEmailAndStatus(token.getUser().getEmail(), StatusAndAccountConfirmed.ACTIVE.getValue());
                user.setAccountConfirmed(StatusAndAccountConfirmed.ACTIVE.getValue());
                userRepository.save(user);
                modelAndView.setViewName("accountVerified");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return modelAndView;
    }

    @Override
    public Response checkLogin(String identity, String password, String status) {


        Response response = new Response();
        try {
            Integer statusCode = Integer.valueOf(status);
            if (identity == null || password == null || status == null)
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            if (statusCode.equals(1)) {
                if (userRepository.findByEmailAndAccountConfirmedAndStatus(identity, 0, 1) != null)
                    throw new PaydayTradeException(ExceptionConstants.ACCOUNT_NOT_CONFIRMED, "Account hasn't been confirmed yet!");

                if (userRepository.findByEmailAndAccountConfirmedAndStatus(identity, 1, 1) == null)
                    throw new PaydayTradeException(ExceptionConstants.EMAIL_NOT_FOUND, "Wrong email!");

                if (userRepository.findByEmailAndPasswordAndAccountConfirmedAndStatus(identity, password, 1, 1) == null)
                    throw new PaydayTradeException(ExceptionConstants.EMAIL_NOT_FOUND, "Wrong password!");

                response.setStatus(RespStatus.getSuccessMessage());

            } else if (statusCode.equals(2)) {
                if (userRepository.findByUsernameAndAccountConfirmedAndStatus(identity, 0, 1) != null)
                    throw new PaydayTradeException(ExceptionConstants.ACCOUNT_NOT_CONFIRMED, "Account hasn't been confirmed yet!");

                if (userRepository.findByUsernameAndStatus(identity, 1) == null)
                    throw new PaydayTradeException(ExceptionConstants.USERNAME_NOT_FOUND, "Wrong username!");

                if (userRepository.findByUsernameAndPasswordAndAccountConfirmedAndStatus(identity, password, 1, 1) == null)
                    throw new PaydayTradeException(ExceptionConstants.USERNAME_NOT_FOUND, "Wrong password!");

                response.setStatus(RespStatus.getSuccessMessage());

            } else {
                throw new PaydayTradeException(ExceptionConstants.INVALID_STATUS, "Invalid status!");
            }

        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            response.setStatus(new RespStatus(ExceptionConstants.VALIDATION_ERROR, "Validation error!"));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response addMoney(String username, String amount) {

        Response response = new Response();

        try {
            if (username.isEmpty() || amount.isEmpty())
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            User user = userRepository.findByUsernameAndStatus(username,StatusAndAccountConfirmed.ACTIVE.getValue());

            user.setBalance(user.getBalance() + Double.valueOf(amount));
            userRepository.save(user);

            response.setStatus(RespStatus.getSuccessMessage());
        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
        }

        return response;
    }

    @Override
    public Response withdrawMoney(String username, String amount) {
        Response response = new Response();

        try {
            if (username == null || amount == null)
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            User user = userRepository.findByUsernameAndStatus(username,StatusAndAccountConfirmed.ACTIVE.getValue());

            if (user.getBalance()-Double.valueOf(amount) < 0)
                throw new PaydayTradeException(ExceptionConstants.INSUFFICIENT_FOUND, "Insufficient found!");

            user.setBalance(user.getBalance() - Double.valueOf(amount));
            userRepository.save(user);

            response.setStatus(RespStatus.getSuccessMessage());
        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            response.setStatus(new RespStatus(ExceptionConstants.VALIDATION_ERROR, "Validation error!"));
            ex.printStackTrace();
        }

        return response;

    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])" +
                "(?=.*[a-zA-Z])([a-zA-Z0-9]+){6,16}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }

}
