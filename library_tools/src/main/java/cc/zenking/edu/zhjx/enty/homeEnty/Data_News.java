package cc.zenking.edu.zhjx.enty.homeEnty;

import java.io.Serializable;

public class Data_News implements Serializable {
    public int currentPage;
    public RotaryPlantingEnty[] data;
    public int amount;
    public int status;
    public String reason;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public RotaryPlantingEnty[] getData() {
        return data;
    }

    public void setData(RotaryPlantingEnty[] data) {
        this.data = data;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
