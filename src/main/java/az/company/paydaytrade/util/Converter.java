package az.company.paydaytrade.util;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.RespStock;
import az.company.paydaytrade.entity.Stock;
import az.company.paydaytrade.entity.User;
import az.company.paydaytrade.enums.StatusAndAccountConfirmed;

public class Converter {

    public static User convertFromUserRequest(UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .balance(new Double(0))
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .accountConfirmed(StatusAndAccountConfirmed.DEACTIVE.getValue())
                .status(StatusAndAccountConfirmed.ACTIVE.getValue())
                .build();
    }

    public static RespStock convertFromStock(Stock stock){
        return RespStock.builder()
                .name(stock.getName())
                .price(stock.getPrice())
                .build();
    }

}
