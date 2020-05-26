package lt.pauliusk.warehousesystem.bean.account.constant;

import lombok.Getter;

@Getter
public enum AccessLevelConst {
    ACCESS("access"),
    STOCKPILE("stockpile");

    private String code;

    AccessLevelConst(String code) {
        this.code = code;
    }
}
