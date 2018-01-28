package com.primus.crm.appointment.service.googleaddress;

import com.primus.crm.appointment.service.GoogleAddressCapturer;

public class GoogleResponse {


    private Result[] results ;
    private String status ;
    public Result[] getResults() {
        return results;
    }
    public void setResults(Result[] results) {
        this.results = results;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



}
