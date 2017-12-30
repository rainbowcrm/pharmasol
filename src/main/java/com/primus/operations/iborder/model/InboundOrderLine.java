package com.primus.operations.iborder.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.merchandise.item.model.Sku;
import com.primus.merchandise.item.model.UOM;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "INBOUND_ORDER_LINES")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})
public class InboundOrderLine extends PrimusBusinessModel {

    InboundOrder inboundOrder;
    Sku sku;
    double qty;
    UOM  uom;

    double rate;
    double taxPercent;
    double taxAmount;
    double discountPercent;
    double discountAmount;
    double lineGrossTotal ;
    double lineNetTotal;
    String description ;

    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true ,isBK = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ORDER_ID")
    public InboundOrder getInboundOrder() {
        return inboundOrder;
    }

    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true ,isBK = true)
    public void setInboundOrder(InboundOrder inboundOrder) {
        this.inboundOrder = inboundOrder;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "UOM_ID")
    public UOM getUom() {
        return uom;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setUom(UOM uom) {
        this.uom = uom;
    }

    @Column(name = "QTY")
    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @Column(name = "RATE")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Column(name = "TAX_PERCENT")
    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    @Column(name = "TAX_AMOUNT")
    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Column(name = "DISCOUNT_PERCENT")
    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Column(name = "DISCOUNT_AMOUNT")
    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Column(name = "LINE_GROSS_TOTAL")
    public double getLineGrossTotal() {
        return lineGrossTotal;
    }

    public void setLineGrossTotal(double lineGrossTotal) {
        this.lineGrossTotal = lineGrossTotal;
    }

    @Column(name = "LINE_NET_TOTAL")
    public double getLineNetTotal() {
        return lineNetTotal;
    }

    public void setLineNetTotal(double lineNetTotal) {
        this.lineNetTotal = lineNetTotal;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
