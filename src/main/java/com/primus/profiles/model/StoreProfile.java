package com.primus.profiles.model;

import com.primus.abstracts.PrimusModel;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.model.CompetitorSalesLine;
import com.primus.crm.appointment.model.PrescriptionSurvey;
import com.primus.crm.appointment.model.StoreVisitOrderLine;
import com.primus.externals.store.model.Store;

import java.util.List;

public class StoreProfile extends PrimusModel {

    Store store ;


    List<PrescriptionSurvey> prescriptionSurveys;
    List<Appointment> appointments;
    List <StoreVisitOrderLine> storeVisitOrderLines ;
    List<CompetitorSalesLine> competitorSalesLines ;


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<PrescriptionSurvey> getPrescriptionSurveys() {
        return prescriptionSurveys;
    }

    public void setPrescriptionSurveys(List<PrescriptionSurvey> prescriptionSurveys) {
        this.prescriptionSurveys = prescriptionSurveys;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<StoreVisitOrderLine> getStoreVisitOrderLines() {
        return storeVisitOrderLines;
    }

    public void setStoreVisitOrderLines(List<StoreVisitOrderLine> storeVisitOrderLines) {
        this.storeVisitOrderLines = storeVisitOrderLines;
    }

    public List<CompetitorSalesLine> getCompetitorSalesLines() {
        return competitorSalesLines;
    }

    public void setCompetitorSalesLines(List<CompetitorSalesLine> competitorSalesLines) {
        this.competitorSalesLines = competitorSalesLines;
    }
}
