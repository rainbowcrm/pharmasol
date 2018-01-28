package com.primus.crm.appointment.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primus.admin.region.model.Location;
import com.primus.common.Logger;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.service.googleaddress.GoogleResponse;
import com.primus.crm.appointment.service.googleaddress.Result;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GoogleAddressCapturer {




    public GoogleResponse getAddress(String lattitude, String longitude)
    {
        String finalURL = "http://maps.googleapis.com/maps/api/geocode/json";

        if(lattitude !=null && longitude != null ) {
            try {
                URL url = new URL(finalURL + "?latlng="
                        + URLEncoder.encode(lattitude + "," + longitude, "UTF-8") + "&sensor=false");

                URLConnection conn = url.openConnection();
                InputStream in = conn.getInputStream() ;
                ObjectMapper mapper = new ObjectMapper();
                GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
                in.close();
                return response ;


            }catch(Exception ex) {
                Logger.logException("getting google Address Failed",this.getClass(),ex);

            }

        }
        return null;

    }

   public static  void setCapturedAddress(Appointment appointment) {
        if (appointment.getLongitude() != null && appointment.getLattitude() != null ) {
            GoogleAddressCapturer capturer = new GoogleAddressCapturer();
            GoogleResponse response = capturer.getAddress(appointment.getLattitude(), appointment.getLongitude());
            StringBuffer buffer = new StringBuffer();
            if (response.getResults() != null) {
                for (int i = 0; i < response.getResults().length; i++) {
                    Result result = response.getResults()[i];
                    buffer.append(result.getFormatted_address());
                    if (i < response.getResults().length - 1) buffer.append(" | ");

                }
            }
            appointment.setGoogleAddress(buffer.toString());
        }

   }

}
