package az.company.paydaytrade.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaydayTradeException extends RuntimeException {

    private Integer code;

    public PaydayTradeException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
