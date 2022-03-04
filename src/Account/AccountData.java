package Account;

import java.time.LocalDateTime;

public class AccountData extends Account {

    private String typeAcc;
    private String timeModify;
    private String address;

    public AccountData(){}
    public AccountData(String userName, String password, String timeModify, String typeAcc, int idAccess, String address){
        super(userName, password, idAccess);
        this.timeModify = timeModify;
        this.typeAcc = typeAcc;
        this.address = address;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getTypeAcc() {
        return typeAcc;
    }

    public void setTypeAcc(String typeAcc) {
        this.typeAcc = typeAcc;
    }

    public String getTimeModify() {
        return timeModify;
    }

    public void setTimeModify(String timeModify) {
        this.timeModify = timeModify;
    }
}
