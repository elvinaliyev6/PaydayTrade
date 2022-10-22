package az.company.paydaytrade.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespStatus {

    Integer code;
    String message;

    private static final Integer SUCCESS_CODE=1;
    private static final String SUCCESS="success";

    public static RespStatus getSuccessMessage(){
        return new RespStatus(SUCCESS_CODE,SUCCESS);
    }

}
