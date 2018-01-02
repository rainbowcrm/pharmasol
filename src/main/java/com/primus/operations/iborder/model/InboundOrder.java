package com.primus.operations.iborder.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.admin.division.model.Division;
import com.primus.admin.region.model.Region;
import com.primus.common.FiniteValue;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "INBOUND_ORDERS")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})
public class InboundOrder extends PrimusBusinessModel {

    Division division ;
    Region region ;
    String docNo ;
    Date requestedDate  ;
    Date requiredDate ;
    FiniteValue orderStatus;
    Double headerDiscountPercent;
    Double headerDiscountAmount;
    Double lineDiscountTotal ;
    Double transportationCharges;
    Double totalTaxPercent;
    Double totalTaxAmount;
    Double grossTotal;
    Double netTotal;
    String description ;
    Collection<InboundOrderLine> inboundOrderLines ;


    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DIVISION_ID")
    public Division getDivision() {
        return division;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setDivision(Division division) {
        this.division = division;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "REGION_ID")
    public Region getRegion() {
        return region;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setRegion(Region region) {
        this.region = region;
    }

    @RadsPropertySet(isBK = true)
    @Column(name = "DOC_NO")
    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    @Column(name = "REQST_DATE")
    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    @Column(name = "REQD_DATE")
    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ORDER_STATUS")
    public FiniteValue getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(FiniteValue orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "HEADER_DISCOUNT_PERCENT")
    public Double getHeaderDiscountPercent() {
        return headerDiscountPercent;
    }

    public void setHeaderDiscountPercent(Double headerDiscountPercent) {
        this.headerDiscountPercent = headerDiscountPercent;
    }

    @Column(name = "HEADER_DISCOUNT_AMOUNT")
    public Double getHeaderDiscountAmount() {
        return headerDiscountAmount;
    }

    public void setHeaderDiscountAmount(Double headerDiscountAmount) {
        this.headerDiscountAmount = headerDiscountAmount;
    }

    @Column(name = "LINE_DISCOUNT")
    public Double getLineDiscountTotal() {
        return lineDiscountTotal;
    }

    public void setLineDiscountTotal(Double lineDiscountTotal) {
        this.lineDiscountTotal = lineDiscountTotal;
    }

    @Column(name = "TRANSPORTATION_CHARGES")
    public Double getTransportationCharges() {
        return transportationCharges;
    }

    public void setTransportationCharges(Double transportationCharges) {
        this.transportationCharges = transportationCharges;
    }

    @Column(name = "TOTAL_TAX_PERCENT")
    public Double getTotalTaxPercent() {
        return totalTaxPercent;
    }

    public void setTotalTaxPercent(Double totalTaxPercent) {
        this.totalTaxPercent = totalTaxPercent;
    }

    @Column(name = "TOTAL_TAX_AMOUNT")
    public Double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(Double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    @Column(name = "GROSS_TOTAL")
    public Double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(Double grossTotal) {
        this.grossTotal = grossTotal;
    }

    @Column(name = "NET_TOTAL")
    public Double getNetTotal() {
        return netTotal;

    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }


    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "inboundOrder")
    public Collection<InboundOrderLine> getInboundOrderLines() {
        return inboundOrderLines;
    }

    public void setInboundOrderLines(Collection<InboundOrderLine> inboundOrderLines) {
        this.inboundOrderLines = inboundOrderLines;
    }
}
