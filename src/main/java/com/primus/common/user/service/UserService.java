package com.primus.common.user.service;

import com.primus.abstracts.*;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.common.CommonUtil;
import com.primus.common.Logger;
import com.primus.common.ProductContext;
import com.primus.common.user.dao.UserDAO;
import com.primus.common.user.model.User;
import com.primus.common.user.model.UserRegion;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class UserService extends AbstractService {


    @Autowired
    UserDAO userDAO ;

    protected TransactionUpdateDelta formRegionDelta(Collection<UserRegion> oldList, Collection<UserRegion> newList) {

        Collection<PrimusBusinessModel> deletedRecords = new ArrayList<PrimusBusinessModel>();
        Collection<PrimusBusinessModel> newRecords = new ArrayList<PrimusBusinessModel>();
        TransactionUpdateDelta transactionUpdateDelta = new TransactionUpdateDelta();
        if (!Utils.isNullCollection(oldList)) {
            oldList.forEach(oldRecord -> {
                UserRegion findMatchByRegion = newList.stream().filter(newRecord -> oldRecord.getRegion().getId() == newRecord.getRegion().getId()).findFirst().orElse(null);
                if (findMatchByRegion == null) {
                    oldRecord.setDeleted(true);
                    oldRecord.setAccessAlowed(false);
                    deletedRecords.add(oldRecord);

                }else  {
                    findMatchByRegion.setId(oldRecord.getId());
                    findMatchByRegion.setVersion(oldRecord.getVersion());
                }

            });
        }
        if (!Utils.isNullCollection(newList)) {
            newList.forEach(newRecord -> {
                if (newRecord.getId() <= 0) {
                    newRecords.add(newRecord);
                }
            });
        }

        transactionUpdateDelta.setDeletedRecords(deletedRecords);
        transactionUpdateDelta.setNewRecords(newRecords);

        return transactionUpdateDelta;
    }

    @Override
    protected void collateBeforUpdate(PrimusModel newObject, PrimusModel oldObject) {
        User newObj = (User) newObject;
        User oldObj = (User) oldObject;
        TransactionUpdateDelta delta = formRegionDelta(oldObj.getUserRegions(),  newObj.getUserRegions()) ;
        if(!Utils.isNullCollection(newObj.getUserRegions()))
            newObj.getUserRegions().addAll((List<UserRegion>)delta.getDeletedRecords());

    }

    @Override
    public AbstractDAO getDAO() {
        return userDAO;
    }

    public List<? extends PrimusModel> listData(String className, int from, int to,
                                                String whereCondition, String orderBy, ProductContext context, SortCriteria sortCriteria) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append( " " );
        if (context.getLoggedinCompany() == 1)  {
            if (!Utils.isNullString(whereCondition)) {
                additionalCondition = additionalCondition.append(whereCondition);
            }
        }else {
            if (Utils.isNullString(whereCondition)) {
                additionalCondition = additionalCondition.append(" where company.id = " + context.getLoggedinCompany());
            } else {
                additionalCondition = additionalCondition.append(whereCondition + " and company.id= " + context.getLoggedinCompany());
            }
        }
        return  getDAO().listData(className ,from, to, additionalCondition.toString() , orderBy);

    }

    private boolean uploadFile(User object, ProductContext context)
    {
        if(Utils.isNullString(object.getFileName())) {
            if(!Utils.isNullString(object.getFileWithoutLink()))
                object.setPhoto(object.getFileWithoutLink());
            return false;
        }
        String fileExtn = CommonUtil.getFileExtn(object.getFileName());
        String fileName =  new String("usr" + object.getPrefix());
        fileName.replace(" ", "_")    ;
        //	doc.setDocName(fileName +  "."  + fileExtn);
        object.setPhoto( "//" +  context.getLoggedinCompanyCode() +  "//usr//" + fileName +  "."  + fileExtn );
        //customer.setPhotoFile(fileName +  "."  + fileExtn );
        if(object.getImage() != null)
            CommonUtil.uploadFile(object.getImage(), fileName +  "."  + fileExtn  , context, "usr");
        else{
            byte[] imageByte;
            try  {
                BASE64Decoder decoder = new BASE64Decoder();
                imageByte = decoder.decodeBuffer(object.getBase64Image());
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                bis.close();
                CommonUtil.uploadFile(imageByte, fileName +  "."  + fileExtn  , context, "usr");
            }catch(Exception ex) {
                Logger.logException("Error in uploading",this.getClass(),ex);
            }
        }

        return true;
    }

    @Override
    public TransactionResult create(PrimusModel object, ProductContext productContext) {
        uploadFile((User) object,productContext);
        return super.create(object, productContext);
    }

    @Override
    public TransactionResult update(PrimusModel object, ProductContext productContext) {
        uploadFile((User) object,productContext);
        return super.update(object, productContext);
    }

    public List<User> getAllDirectReportees(User manager , ProductContext context)
    {
        return ( (UserDAO)getDAO()).getAllDirectReportees(manager,context.getLoggedinCompany());
    }

}

