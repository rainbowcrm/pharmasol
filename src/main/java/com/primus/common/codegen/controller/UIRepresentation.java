package com.primus.common.codegen.controller;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class UIRepresentation extends ModelObject{

    String uiControl ;
    String uiLabel ;

    boolean showInList;
    boolean showInFilter;
    int listFieldWidth;

    String lookupKey;

    public String getUiLabel() {
        return uiLabel;
    }

    public void setUiLabel(String uiLabel) {
        this.uiLabel = uiLabel;
    }

    public String getUiControl() {
        return uiControl;
    }

    public void setUiControl(String uiControl) {
        this.uiControl = uiControl;
    }

    public boolean isShowInList() {
        return showInList;
    }

    public void setShowInList(boolean showInList) {
        this.showInList = showInList;
    }

    public boolean isShowInFilter() {
        return showInFilter;
    }

    public void setShowInFilter(boolean showInFilter) {
        this.showInFilter = showInFilter;
    }

    public int getListFieldWidth() {
        return listFieldWidth;
    }

    public void setListFieldWidth(int listFieldWidth) {
        this.listFieldWidth = listFieldWidth;
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }
}
