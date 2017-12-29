package com.primus.profiles.model;

import com.primus.abstracts.PrimusModel;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.model.StockistVisitOrderLine;
import com.primus.externals.stockist.model.Stockist;

import java.util.List;

public class StockistProfile extends PrimusModel {

    Stockist stockist ;

    List<Appointment> appointments;
    List <StockistVisitOrderLine> stockistVisitOrderLines;

    public Stockist getStockist() {
        return stockist;
    }

    public void setStockist(Stockist stockist) {
        this.stockist = stockist;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<StockistVisitOrderLine> getStockistVisitOrderLines() {
        return stockistVisitOrderLines;
    }

    public void setStockistVisitOrderLines(List<StockistVisitOrderLine> stockistVisitOrderLines) {
        this.stockistVisitOrderLines = stockistVisitOrderLines;
    }
}
