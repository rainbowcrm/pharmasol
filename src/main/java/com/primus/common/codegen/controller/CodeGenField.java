package com.primus.common.codegen.controller;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class CodeGenField extends ModelObject {

    String fieldName ;
    String dbFieldName;
    boolean isPrimitiveDataType;
    String dataType;
    String finiteValueCategory;
    String optionsPopulator;
    String importPackage;
    boolean isCollection;
    String collectionType;
    boolean isPk;
    boolean isBk ;
    boolean isMandatory;


    UIRepresentation uiRepresentation ;

    CodeGenField childObject ;

    List<CodeGenField> innerObjects;


    String entityDefXML ;

    public UIRepresentation getUiRepresentation() {
        return uiRepresentation;
    }

    public void setUiRepresentation(UIRepresentation uiRepresentation) {
        this.uiRepresentation = uiRepresentation;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAjaxFilterResponse()
    {
        return "<responseElement key = \""+ fieldName + "\" >" + "txt" + fieldName.replace('.','_')  + "</responseElement>";

    }

    private String getUIControl() {
       return Utils.isNullString(uiRepresentation.getUiControl())?"UIText":uiRepresentation.getUiControl();
    }
    public  String getUiFilterdef()
    {
        return  "  <FilterNode> \n <Element label = \""+ uiRepresentation.getUiLabel() + "\"   type = \""  + getUIControl() +
                "\" Id = \"txt" + fieldName.replace('.','_')    +  " \"  property =\"" + fieldName  + "\"  /> \n  </FilterNode> ";

    }

    public int getListColWidth()
    {
       return uiRepresentation.getListFieldWidth()>0?uiRepresentation.getListFieldWidth():20;

    }

    public  String getUiColumn()
    {
        return   " <Column title =\""+ uiRepresentation.getUiLabel() + "\"   sortField=\""+ dbFieldName  + "\" width=\"" + getListColWidth() + "%\"   >  " +
                "  <Element label = \"\"  type = \"UINote\" Id = \"idIdNote\"  property =\""+ fieldName +"\" /> " +
                  "  </Column> ";

    }

    public String getColWrappedUielementdef ()
    {
        if (uiRepresentation != null) {
            String firstPart = " <Element type =\"UITableCol\" width = \"" + uiRepresentation.getListFieldWidth() + "%\" > ";
            String secondPart = getUielementdef() + "</Element>";
            return firstPart + secondPart;
        }else
            return  "";

    }
    public  String getUielementdef()
    {
        //<Element label = "Division_Code"  type = "UIText" Id = "txtCode"  isMandatory="true" size="10" property ="Code" />
        if (uiRepresentation != null) {
            if (Utils.isNullString(uiRepresentation.getUiControl()) || "UIText".equalsIgnoreCase(uiRepresentation.getUiControl())) {
                return "<Element label = \"" + uiRepresentation.getUiLabel() + "\" type = \"UIText\" isMandatory=\"" + isMandatory + "\" Id = \"txt" + fieldName.replace('.', '_') +
                        "\" property =\"" + fieldName + "\" />";
            } else if ("UIDate".equalsIgnoreCase(uiRepresentation.getUiControl())) {
                return "<Element label = \"" + uiRepresentation.getUiLabel() + "\" type = \"UIDate\" isMandatory=\"" + isMandatory + "\" Id = \"txt" + fieldName.replace('.', '_') +
                        "\" property =\"" + fieldName + "\" />";
            }else if ("UITextArea".equalsIgnoreCase(uiRepresentation.getUiControl())) {
                String firstPart =  "<Element label = \"" + uiRepresentation.getUiLabel() + "\" type = \"UITextArea\" isMandatory=\"" + isMandatory + "\" Id = \"txt" + fieldName.replace('.', '_') +
                        "\" property =\"" + fieldName + "\" >";
                String secPart = "<rows>5</rows> <cols>30</cols>";
                String thirdPart  = "</Element>" ;
                return firstPart + secPart + thirdPart ;

            } else if ("UIBooleanCheckBox".equalsIgnoreCase(uiRepresentation.getUiControl())) {
                return "<Element label = \"" + uiRepresentation.getUiLabel() + "\" type = \"UIBooleanCheckBox\" hiddenControlId =\"hdn" + fieldName.replace('.','_') + "\" "  +
                        " isMandatory=\"" + isMandatory + "\" Id = \"txt" + fieldName.replace('.', '_') +
                        "\" property =\"" + fieldName + "\" />";
            } else if ("UILookupDataList".equalsIgnoreCase(uiRepresentation.getUiControl())) {
                return "<Element label = \"" + uiRepresentation.getUiLabel() + "\"  type = \"UILookupDataList\"  listId=\"lst"+ fieldName.replace('.','_') +"\""
                        + " Id = \"txt"+fieldName.replace('.','_') +"\"   "  +
                        " isMandatory=\""+ isMandatory + "\" property =\""+ fieldName +"." + uiRepresentation.getLookupKey() +"\"> \n" +
                        " <lookupType>"+ Utils.initlower(dataType )+ "</lookupType>\n" +
                         " </Element>";
            }else if ("UIList".equalsIgnoreCase(uiRepresentation.getUiControl())) {

                String fieldSub = "";
                if ("FiniteValue".equalsIgnoreCase(dataType)) {
                    fieldSub =".code" ;
                } else if ("String".equalsIgnoreCase(dataType)) {
                    fieldSub="";
                }else
                    fieldSub =".id" ;

                String header = "<Element label = \"" + uiRepresentation.getUiLabel() + "\" type = \"UIList\" isMandatory=\"" + isMandatory + "\" Id = \"lst" + fieldName.replace('.', '_') +
                        "\" property =\"" + fieldName +  fieldSub +"\""  + " >";
                String footer = "</Element>";
                if ("FiniteValue".equalsIgnoreCase(dataType)) {
                    String options = "<options  populator = \"getFiniteValues\" populatorParam = \"" + finiteValueCategory + "\">  \n " +
                            "</options>";
                    return header + "\n" + options + "\n" + footer;
                } else {
                    String options = "<options  populator = \"" + optionsPopulator + "\"   \n  >" +
                            "</options>";
                    return header + "\n" + options + "\n" + footer;
                }
            }
        }
        return  "";
    }

    public  String getInitCapfieldName() {
        return Utils.initupper(fieldName);
    }

    public  String getGetterMethod() {
        return "get" + Utils.initupper(fieldName);
    }

    public  String getSetterMethod() {
        return "set" + Utils.initupper(fieldName);
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJPAAnnotation ()
    {
        if(!"String".equalsIgnoreCase(dataType) && !"Date".equalsIgnoreCase(dataType)  && !"int".equalsIgnoreCase(dataType)
          && !"Integer".equalsIgnoreCase(dataType) && !"double".equalsIgnoreCase(dataType)  && !"Double".equalsIgnoreCase(dataType) && !"Boolean".equalsIgnoreCase(dataType)
          && !"boolean".equalsIgnoreCase(dataType)  && !"float".equalsIgnoreCase(dataType) && !"Float".equalsIgnoreCase(dataType)   &&
                !"Collection".equalsIgnoreCase(dataType) && !"Set".equalsIgnoreCase(dataType)  && !"List".equalsIgnoreCase(dataType)     ) {

            String str1 = "@ManyToOne(cascade=CascadeType.DETACH)\n";
            return str1 +  "\t@JoinColumn(name  =\""+ dbFieldName + "\")" ;
        }else if ("Collection".equalsIgnoreCase(dataType) || "Set".equalsIgnoreCase(dataType)  || "List".equalsIgnoreCase(dataType) && childObject != null ) {
            String childField = childObject.getFieldName() ;
           return "@OneToMany(cascade= CascadeType.ALL, mappedBy = \""+ childField +"\")";
        }else
            return  "@Column(name  =\""+ dbFieldName + "\")" ;
    }

    public String getRadsAnnotation ()
    {
        if(isBk()) {
            return "@RadsPropertySet(isBK =  true )" ;
        } else  if(!"String".equalsIgnoreCase(dataType) && !"Date".equalsIgnoreCase(dataType)  && !"int".equalsIgnoreCase(dataType)
                && !"Integer".equalsIgnoreCase(dataType) && !"double".equalsIgnoreCase(dataType)  && !"Double".equalsIgnoreCase(dataType) && !"Boolean".equalsIgnoreCase(dataType)
                && !"boolean".equalsIgnoreCase(dataType)  && !"float".equalsIgnoreCase(dataType) && !"Float".equalsIgnoreCase(dataType)   &&
                !"Collection".equalsIgnoreCase(dataType) && !"Set".equalsIgnoreCase(dataType)  && !"List".equalsIgnoreCase(dataType)     ) {

            return "@RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)" ;
        }

        return  "";



    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public boolean isPrimitiveDataType() {
        return isPrimitiveDataType;
    }

    public void setPrimitiveDataType(boolean primitiveDataType) {
        isPrimitiveDataType = primitiveDataType;
    }

    public String getDataType() {
        if(("Collection".equalsIgnoreCase(dataType) ||  "List".equalsIgnoreCase(dataType) ||  "Set".equalsIgnoreCase(dataType))  && childObject != null ) {
            return dataType + "<" + childObject.getDataType() + ">" ;

        }else
            return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getImportPackage() {
        return importPackage;
    }

    public void setImportPackage(String importPackage) {
        this.importPackage = importPackage;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }


    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean pk) {
        isPk = pk;
    }

    public boolean isBk() {
        return isBk;
    }

    public void setBk(boolean bk) {
        isBk = bk;
    }



    public String getCollectionType() {
        return collectionType;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getFiniteValueCategory() {
        return finiteValueCategory;
    }

    public void setFiniteValueCategory(String finiteValueCategory) {
        this.finiteValueCategory = finiteValueCategory;
    }

    public String getOptionsPopulator() {
        return optionsPopulator;
    }

    public void setOptionsPopulator(String optionsPopulator) {
        this.optionsPopulator = optionsPopulator;
    }

    public CodeGenField getChildObject() {
        return childObject;
    }

    public void setChildObject(CodeGenField childObject) {
        this.childObject = childObject;
    }

    public String getEntityDefXML() {
        return entityDefXML;
    }

    public void setEntityDefXML(String entityDefXML) {
        this.entityDefXML = entityDefXML;
    }

    public List<CodeGenField> getInnerObjects() {
        return innerObjects;
    }

    public int getInnerObjectsSize() {
        if (innerObjects != null)
            return innerObjects.size();
        else
            return 0;
    }

    public void setInnerObjects(List<CodeGenField> innerObjects) {
        this.innerObjects = innerObjects;
    }

    public String getSubObjectColHeader()
    {
       //" <Element type =\"UITableCol\" width = \"10%\" ><Element label = \"\"  type = \"UILabel\" Id = \"lblCode\" value =\"Code\" /></Element>";
        if (uiRepresentation != null) {
            String firstPart = " <Element type =\"UITableCol\" width = \"" + uiRepresentation.getListFieldWidth() + "%\" > ";
            String secondPart = "<Element label = \"\"  type = \"UILabel\" Id = \"lblCode\" value =\""+ uiRepresentation.getUiLabel() + "\" /></Element>";
            return firstPart + secondPart;
        }else
            return  "";


    }

}
