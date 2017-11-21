package com.primus.framework.nextup;


import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.primus.util.ServiceLibrary;
import com.primus.admin.division.model.Division;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;


public class NextUpGenerator {

	public static final String CONSTANT= "CONSTANT";
	public static final String YEARCOMP= "YEARCOMP";
	public static final String DIVCODE= "DIVCODE";
	public static final String COMPCODE= "COMPCODE";
	public static final String MONTHCOMP= "MONTHCOMP";
	public static final String DAYCOMP= "DAYCOMP";
	public static final String DATECOMP= "DATECOMP";
	public static final String SEQCOMP= "SEQCOMP";
	
	public static String getNextNumber(String program, ProductContext context , Division division  ) {
		int companyCode = context.getLoggedinCompany() ;
		StringBuffer ansString = new StringBuffer();
		NextUPSQL nextUPSQL = ServiceLibrary.services().getNextUPSQL() ;
		NextUpConfig config = nextUPSQL.getNextUpConfig(companyCode, program);
		Company company = CommonUtil.getCompany(context.getLoggedinCompany());
		int seq =nextUPSQL.getNextPKValue(program,(division == null)?-1: division.getId(), companyCode);
		String part1 = makeCompString(config.getComponent1(), division, company, seq);
		if (!Utils.isNull(part1)) {
			ansString.append(part1);
		}
		String part2 = makeCompString(config.getComponent2(), division,company, seq);
		if (!Utils.isNull(part2)) {
			ansString.append(part2);
		}
		String part3 = makeCompString(config.getComponent3(), division,company, seq);
		if (!Utils.isNull(part3)) {
			ansString.append(part3);
		}
		String part4 = makeCompString(config.getComponent4(), division,company, seq);
		if (!Utils.isNull(part4)) {
			ansString.append(part4);
		}
		return ansString.toString();
	}

	private static String prefix(int width, int number)
	{
		int noDigits = String.valueOf(number).length();
		if (width > noDigits) {
			StringBuffer buff = new StringBuffer("") ;
			for (int i = noDigits+1 ; i <=width ; i ++)
				buff.append("0");
			buff.append(String.valueOf(number)) ;
			return buff.toString() ;
		}
		return String.valueOf(number);
	}
	private static String makeCompString(NextUpConfig.NextUpComponent component , Division division, Company company, int sequence) {
		StringBuffer buff = new StringBuffer();
		if (component == null || component.isNull()) return null ;
		int width =  component.getFieldWidth() ;
		if(component.getFieldType().equals(CONSTANT)) {
			return component.getFieldValue();
		} else if(component.getFieldType().equals(YEARCOMP)) {
			return String.valueOf(new java.util.Date().getYear() );
		} else if(component.getFieldType().equals(MONTHCOMP)) {
			return String.valueOf(new java.util.Date().getMonth() );
		} else if(component.getFieldType().equals(DAYCOMP)) {
			return String.valueOf(new java.util.Date().getMonth() );
		} else if(component.getFieldType().equals(DATECOMP)) {
			return String.valueOf(new java.util.Date().toLocaleString());
		} else if(component.getFieldType().equals(SEQCOMP)) {
			return prefix(width,sequence);
		} else if(component.getFieldType().equals(DIVCODE)) {
			return String.valueOf(division.getCode());
		}else if(component.getFieldType().equals(COMPCODE)) {
			return String.valueOf(company.getCode());
		}
		
		return null;
	}
	
	
	
	
}
