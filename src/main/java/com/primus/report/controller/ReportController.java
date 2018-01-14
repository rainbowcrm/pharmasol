package com.primus.report.controller;

import com.primus.abstracts.AbstractGeneralController;
import com.primus.common.CommonUtil;
import com.primus.common.Logger;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.report.model.ReportModel;
import com.primus.report.service.ReportService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class ReportController extends AbstractGeneralController {

    HttpServletResponse resp;
    HttpServletRequest request;

    @Override
    public IRadsContext generateContext(HttpServletRequest request, HttpServletResponse response, UIPage page) {
        resp = response ;
        this.request= request;
        return CommonUtil.generateContext(request,page);
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        try {
            ReportService service = ServiceFactory.getReportService();
            byte[] byteArray = service.getVisitReport((ReportModel) object, (ProductContext) getContext());
            resp.setContentType("application/xls");
            resp.setHeader("Content-Disposition","attachment; filename=dreport.pdf" );

            OutputStream responseOutputStream = resp.getOutputStream();
            responseOutputStream.write(byteArray);
            responseOutputStream.close();
            PageResult result = new PageResult();
            result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
            return result;
        }catch(Exception ex) {
            Logger.logException("error",this.getClass(),ex);
        }
        return null;
    }

    @Override
    public PageResult submit(ModelObject modelObject) {
        return new PageResult();
    }
}
