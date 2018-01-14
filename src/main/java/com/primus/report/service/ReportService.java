package com.primus.report.service;


import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.admin.region.model.Region;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.report.model.ReportModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportService extends AbstractService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public AbstractDAO getDAO() {
        return null;
    }


    public byte[] getVisitReport(ReportModel report, ProductContext context) throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fromDate", report.getFromDate());
        parameters.put("toDate", report.getToDate());
        parameters.put("companyId", context.getLoggedinCompany());
        parameters.put("companyName", context.getLoggedInUser().getCompany().getName());
        parameters.put("RegionId",report.getRegion().getId());
        Region region =  (Region)CommonUtil.getRegionService().getById(report.getRegion().getId()) ;
        parameters.put("Region", region.getName());
        URL resource = null;
        resource = this.getClass().getResource("/jaspertemplates/DailyVisits.jrxml");
    JasperDesign jasperDesign = JRXmlLoader.load(resource.getPath());
    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jdbcTemplate.getDataSource().getConnection());
    byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
      return  output;
    }



}
