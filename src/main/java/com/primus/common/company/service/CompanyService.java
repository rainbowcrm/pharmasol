package com.primus.common.company.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.Logger;
import com.primus.common.ProductContext;
import com.primus.common.company.dao.CompanyDAO;
import com.primus.common.company.model.Company;
import com.primus.externals.doctor.model.Doctor;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.util.List;

@Component
@Transactional
public class CompanyService extends AbstractService {

    @Autowired
    CompanyDAO companyDAO;

    @Override
    public AbstractDAO getDAO() {
        return companyDAO;
    }


    public Company findByCode(String code) {
        return ((CompanyDAO)getDAO()).findByCode(code);
    }


    public Company findByName(String name) {
        return ((CompanyDAO)getDAO()).findByName(name);
    }


    @Override
    public List<? extends PrimusModel> listData(String className, int from, int to, String whereCondition, String orderBy, ProductContext context, SortCriteria sortCriteria) {
        return  getDAO().listData(className ,from, to, whereCondition , orderBy);
    }



    public List<? extends PrimusModel> fetchAllActive(String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false  ");
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false ");
        }
        return getDAO().fetchAllActive(getDAO().getEntityClassName(), additionalCondition.toString(), orderBy);

    }

    public List<? extends PrimusModel> fetchAllActive(String className,
                                                      String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false ");
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false ");
        }
        return getDAO().fetchAllActive(className, additionalCondition.toString(), orderBy);

    }

    private boolean uploadFile(Company object, ProductContext context)
    {
        if(Utils.isNullString(object.getFileName())) {
            if(!Utils.isNullString(object.getFileWithoutLink()))
                object.setLogo(object.getFileWithoutLink());
            return false;
        }
        String fileExtn = CommonUtil.getFileExtn(object.getFileName());
        String fileName =  new String("logo" + object.getCode());
        fileName.replace(" ", "_")    ;
        //	doc.setDocName(fileName +  "."  + fileExtn);
        object.setLogo( "//" + object.getCode()  +  "//logo//" + fileName +  "."  + fileExtn );
        //customer.setPhotoFile(fileName +  "."  + fileExtn );
        if(object.getImage() != null)
            CommonUtil.uploadFile(object.getImage(), fileName +  "."  + fileExtn  , context, "logo",object.getCode());
        else{
            byte[] imageByte;
            try  {
                BASE64Decoder decoder = new BASE64Decoder();
                imageByte = decoder.decodeBuffer(object.getBase64Image());
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                bis.close();
                CommonUtil.uploadFile(imageByte, fileName +  "."  + fileExtn  , context, "logo");
            }catch(Exception ex) {
                Logger.logException("Error in uploading",this.getClass(),ex);
            }
        }

        return true;
    }


    @Override
    public TransactionResult create(PrimusModel object, ProductContext productContext) {
        uploadFile((Company) object,productContext);
        return super.create(object, productContext);
    }

    @Override
    public TransactionResult update(PrimusModel object, ProductContext productContext) {
        uploadFile((Company) object,productContext);
        return super.update(object, productContext);
    }


}
