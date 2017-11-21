package com.primus.common.codegen.controller;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeGenEntity extends ModelObject{

    String entityName;
    String rootpackage ;
    String controllerType;
    String tableName;
    String fileLocation ;
    String uiLocation;


    public String getUiLocation() {
        return uiLocation;
    }

    public void setUiLocation(String uiLocation) {
        this.uiLocation = uiLocation;
    }

    public List<CodeGenField> getMandatoryFields()
    {
        return codeGenFields.stream().filter(line ->  line.isMandatory()).collect(Collectors.toList());

    }

     public List<CodeGenField> getBusinessKey()
    {
        return codeGenFields.stream().filter(line ->  line.isBk()).collect(Collectors.toList());

    }

    List<CodeGenField> codeGenFields;


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getRootpackage() {
        return rootpackage;
    }

    public void setRootpackage(String rootpackage) {
        this.rootpackage = rootpackage;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CodeGenField> getCodeGenFields() {
        return codeGenFields;
    }

    public void setCodeGenFields(List<CodeGenField> codeGenFields) {
        this.codeGenFields = codeGenFields;
    }


    public List<CodeGenField> geCollectionObjects()  {
        List<CodeGenField>  ans = new ArrayList<CodeGenField>( );
        for (CodeGenField field : codeGenFields) {
            if(field.getDataType().contains("Collection")) {
                ans.add(field) ;
            }
        }
        return ans;
        //return codeGenFields.stream().filter( field ->  field.getDataType().equalsIgnoreCase("Collection")).collect(Collectors.toList()) ;
    }


    public List<CodeGenField> getUiFilterfields()  {
        return codeGenFields.stream().filter( field ->  field.getUiRepresentation().isShowInFilter() ).collect(Collectors.toList()) ;
    }

    public List<CodeGenField> getUiListfields()  {
        return codeGenFields.stream().filter( field ->  field.getUiRepresentation().isShowInList() ).collect(Collectors.toList()) ;
    }

    public int getListColSize()
    {
        return codeGenFields.stream().filter( field ->  field.getUiRepresentation().isShowInList() ).collect(Collectors.toList()).size() ;
    }

    public List<CodeGenField> getCodeGenImports() {
        return codeGenFields.stream().filter( field -> !Utils.isNullString(field.getImportPackage())).collect(Collectors.toList()) ;
    }

    public List<CodeGenField> getCodeGenOptionsPopulator() {
        return codeGenFields.stream().filter( field -> !Utils.isNullString(field.getOptionsPopulator())).collect(Collectors.toList()) ;
    }


    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
