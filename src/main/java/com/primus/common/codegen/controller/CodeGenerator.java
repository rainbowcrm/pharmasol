package com.primus.common.codegen.controller;

import com.techtrade.rads.framework.utils.Utils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {


    public void generate(String entityXMLName, String mode)  throws Exception{

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com//primus//entities//" + entityXMLName + ".xml");
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        String theString = writer.toString();
        CodeGenEntity entity  =  (CodeGenEntity)CodeGenEntity.instantiateObjectfromXML(theString,CodeGenEntity.class.getName(),null);
        if("Model".equalsIgnoreCase(mode) ||  "AllServer".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {

            generateModel(entity);
        }
        if("Controller".equalsIgnoreCase(mode) ||  "AllServer".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {

            generateController(entity);
            generateListController(entity);
        }
        if("DAO".equalsIgnoreCase(mode) ||  "AllServer".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {

            generateDAO(entity);
        }

        if("Service".equalsIgnoreCase(mode) ||  "AllServer".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {

            generateService(entity);
        }

        if("Validator".equalsIgnoreCase(mode) ||  "AllServer".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {

            generateValidator(entity);
        }

        if("CRUDUI".equalsIgnoreCase(mode) ||  "AllUI".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {
            if("AbstractTransactionController".equalsIgnoreCase(entity.getControllerType()))
                generateUITransactionPage(entity);
            else if ("AbstractDataSheetController".equalsIgnoreCase(entity.getControllerType()))
                generateUIDataSheetPage(entity);
            else
                 generateUICRUDPage(entity);
        }

        if("ListUI".equalsIgnoreCase(mode) ||  "AllUI".equalsIgnoreCase(mode) || "All".equalsIgnoreCase(mode) )  {
            if (!"AbstractDataSheetController".equalsIgnoreCase(entity.getControllerType()))
                generateUIlistPage(entity);
        }
    }


    private void generateUIDataSheetPage(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//DataSheetCreate.vm");
        VelocityContext velocityContext = new VelocityContext();


        velocityContext.put("pageID", "new" + entity.getEntityName() );
        velocityContext.put("pageTitle",entity.getEntityName()+" | Primus Solutions");
        velocityContext.put("entityWithPackage",entity.getRootpackage() +".model." + entity.getEntityName());
        velocityContext.put("crudControllerwithPackage", entity.getRootpackage() +".controller." + entity.getEntityName() +"Controller");
        velocityContext.put("uiFilterfields",entity.getUiFilterfields());
        velocityContext.put("uiListfields",entity.getUiListfields());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getUiLocation() );
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getUiLocation() + "\\" +  entity.getEntityName().toLowerCase() + ".xml");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getUiLocation() + "\\" +  entity.getEntityName().toLowerCase() + ".xml");
        fos.write(writer.toString().getBytes());
        fos.close();
    }


    private void generateUIlistPage(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//ListPage.vm");
        VelocityContext velocityContext = new VelocityContext();


        velocityContext.put("pageList", entity.getEntityName() + "s" );
        velocityContext.put("pageTitle",entity.getEntityName()+" | Primus WFM");
        velocityContext.put("entityWithPackage",entity.getRootpackage() +".model." + entity.getEntityName());
        velocityContext.put("listControllerwithPackage", entity.getRootpackage() +".controller." + entity.getEntityName() +"ListController");
        velocityContext.put("addEditPage", "new" + entity.getEntityName().toLowerCase() );
        velocityContext.put("uiFilterfields",entity.getUiFilterfields());
        velocityContext.put("uiListfields",entity.getUiListfields());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getUiLocation() );
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getUiLocation() + "\\" +  entity.getEntityName().toLowerCase() + "List.xml");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getUiLocation() + "\\" +  entity.getEntityName().toLowerCase() + "List.xml");
        fos.write(writer.toString().getBytes());
        fos.close();
    }

    private void generateUITransactionPage(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//TransactionCreate.vm");
        VelocityContext velocityContext = new VelocityContext();


        velocityContext.put("pageID", "new" + entity.getEntityName() );
        velocityContext.put("pageTitle",entity.getEntityName()+" | Primus WFM");
        velocityContext.put("entityWithPackage",entity.getRootpackage() +".model." + entity.getEntityName());
        velocityContext.put("crudControllerwithPackage", entity.getRootpackage() +".controller." + entity.getEntityName() +"Controller");
        velocityContext.put("listPage",entity.getEntityName().toLowerCase() + "s" );
        velocityContext.put("uiDisplayfields",entity.getCodeGenFields());
        List<CodeGenEntity> subObjects = new ArrayList<CodeGenEntity>();
        velocityContext.put("collectionObjects", entity.geCollectionObjects());
        if (! Utils.isNullList(entity.geCollectionObjects()))
        {
            for (CodeGenField codeGenField : entity.geCollectionObjects()) {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com//primus//entities//" + codeGenField.getEntityDefXML() + ".xml");
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer);
                String theString = writer.toString();
                CodeGenEntity childentity = (CodeGenEntity) CodeGenEntity.instantiateObjectfromXML(theString, CodeGenEntity.class.getName(), null);
             //   codeGenField.setChildEntity(Childentity);
                codeGenField.setInnerObjects(childentity.getCodeGenFields());

            }
        }

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getUiLocation() );
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getUiLocation() + "\\new" +  entity.getEntityName().toLowerCase() + ".xml");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getUiLocation() + "\\new" +  entity.getEntityName().toLowerCase() + ".xml");
        fos.write(writer.toString().getBytes());
        fos.close();
    }

    private void generateUICRUDPage(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//CRUDCreate.vm");
        VelocityContext velocityContext = new VelocityContext();


        velocityContext.put("pageID", "new" + entity.getEntityName() );
        velocityContext.put("pageTitle",entity.getEntityName()+" | Primus WFM");
        velocityContext.put("entityWithPackage",entity.getRootpackage() +".model." + entity.getEntityName());
        velocityContext.put("crudControllerwithPackage", entity.getRootpackage() +".controller." + entity.getEntityName() +"Controller");
        velocityContext.put("listPage",entity.getEntityName().toLowerCase() + "s" );
        velocityContext.put("uiDisplayfields",entity.getCodeGenFields());
        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getUiLocation() );
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getUiLocation() + "\\new" +  entity.getEntityName().toLowerCase() + ".xml");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getUiLocation() + "\\new" +  entity.getEntityName().toLowerCase() + ".xml");
        fos.write(writer.toString().getBytes());
        fos.close();
    }

    private void generateListController(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//ListController.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("rootpackagecontroller",entity.getRootpackage() + ".controller");
        velocityContext.put("controllerClass",entity.getEntityName()+"ListController");
        velocityContext.put("serviceClass",entity.getEntityName() +"Service");
        velocityContext.put("validatorClass",entity.getEntityName() +"Validator");
        velocityContext.put("addeditpagekey", "new" +  entity.getEntityName().toLowerCase() );

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\controller");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\controller\\" + entity.getEntityName() + "ListController.java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\controller\\" + entity.getEntityName() +"ListController.java");
        fos.write(writer.toString().getBytes());
        fos.close();
    }

    private String getOptionsPopulators(CodeGenEntity entity)
    {

        if (!Utils.isNullList( entity.getCodeGenOptionsPopulator()) ) {
            StringBuffer buff = new StringBuffer( " ") ;
            entity.getCodeGenOptionsPopulator().forEach( codeGenField ->  {
                String popMethodDef =  " public Map<String, String > " + codeGenField.getOptionsPopulator() + "() ";
                String popMethodBody =  " {\n" +
                        "        Map<String , String > ans = new LinkedHashMap<>() ;\n" +
                        "\n" +
                        "        return ans;\n" +
                        "    }" ;
                buff.append("\n" + popMethodDef + "\n" + popMethodBody + "\n " ) ;

            } );
            return buff.toString();

        }
        return " ";

    }
    private void generateController(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//Controller.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("rootpackagecontroller",entity.getRootpackage() + ".controller");
        velocityContext.put("controllerClass",entity.getEntityName()+"Controller");
        velocityContext.put("superControllerClass",entity.getControllerType());
        velocityContext.put("superControllerPackage","com.primus.abstracts." + entity.getControllerType() + ";" );
        velocityContext.put("serviceClass",entity.getEntityName() +"Service");
        velocityContext.put("validatorClass",entity.getEntityName() +"Validator");
        velocityContext.put("optionsPopulators",getOptionsPopulators(entity));

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\controller");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\controller\\" + entity.getEntityName() + "Controller.java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\controller\\" + entity.getEntityName() +"Controller.java");
        fos.write(writer.toString().getBytes());
        fos.close();
    }


    private void generateValidator(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//Validator.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("entityName", entity.getEntityName());
        velocityContext.put("entityVariable", Utils.initlower(entity.getEntityName()));
        velocityContext.put("rootpackagevalidator", entity.getRootpackage() + ".validator");
        velocityContext.put("rootpackagemodel",entity.getRootpackage() + ".model." + entity.getEntityName());
        velocityContext.put("validatorClass", entity.getEntityName() + "Validator");
        velocityContext.put("mandatoryfields",entity.getMandatoryFields());

        List<CodeGenField> businessKeys = entity.getBusinessKey() ;
        if (businessKeys != null  && businessKeys.size() ==1 ) {
            velocityContext.put("businessKeyLabel", businessKeys.get(0).getUiRepresentation().getUiLabel());
        }



        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\validator");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\validator\\" + entity.getEntityName() + "Validator.java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\validator\\" + entity.getEntityName() + "Validator.java");
        fos.write(writer.toString().getBytes());
        fos.close();

    }


    private void generateService(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//Service.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("entityName", entity.getEntityName());
        velocityContext.put("rootpackageservice", entity.getRootpackage() + ".service");
        velocityContext.put("serviceClass", entity.getEntityName() + "Service");
        velocityContext.put("daoClass", entity.getEntityName() + "DAO");
        velocityContext.put("daoObject", Utils.initlower(entity.getEntityName()) + "DAO");
        velocityContext.put("daopackage",entity.getRootpackage() + ".dao." + entity.getEntityName() +"DAO");






        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\service");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\service\\" + entity.getEntityName() + "Service.java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\service\\" + entity.getEntityName() + "Service.java");
        fos.write(writer.toString().getBytes());
        fos.close();

    }


    private void generateDAO(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//DAO.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("entityName", entity.getEntityName());
        velocityContext.put("rootpackagedao", entity.getRootpackage() + ".dao");
        velocityContext.put("daoClass", entity.getEntityName() + "DAO");
        velocityContext.put("entityClass", entity.getEntityName() + ".class");
        velocityContext.put("modelpackage",entity.getRootpackage() + ".model." + entity.getEntityName());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\dao");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\dao\\" + entity.getEntityName() + "DAO.java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\dao\\" + entity.getEntityName() + "DAO.java");
        fos.write(writer.toString().getBytes());
        fos.close();

    }

    private void generateModel(CodeGenEntity entity)  throws  Exception {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.init();
        Template t = ve.getTemplate("com//primus//common/codegen//templates//ModelObject.vm");
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("entityName", entity.getEntityName());
        velocityContext.put("rootpackagemodel",entity.getRootpackage() + ".model");
        velocityContext.put("tableName",entity.getTableName());
        velocityContext.put("imports",entity.getCodeGenImports());
        velocityContext.put("fields",entity.getCodeGenFields());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        File folder = new File(entity.getFileLocation() +"\\model");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(entity.getFileLocation() +"\\model\\" + entity.getEntityName() + ".java");
        file.createNewFile() ;
        FileOutputStream fos =new FileOutputStream(entity.getFileLocation() +"\\model\\" + entity.getEntityName() + ".java");
        fos.write(writer.toString().getBytes());
        fos.close();

    }
}