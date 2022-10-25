package az.company.paydaytrade.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusAndAccountConfirmed {

    ACTIVE(1),
    DEACTIVE(0);

    private int value;

    public int getValue(){
        return value;
    }


}
