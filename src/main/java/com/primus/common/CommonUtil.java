package com.primus.common;

import com.primus.common.company.model.Company;
import com.primus.common.company.service.CompanyService;
import com.primus.common.filter.service.FilterService;
import com.primus.common.login.model.Login;
import com.primus.common.login.service.LoginService;
import com.primus.common.user.model.User;
import com.primus.common.user.service.UserService;
import com.primus.util.ServiceLibrary;
import com.primus.admin.role.service.RoleService;
import com.primus.admin.division.service.DivisionService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonUtil  extends  ServiceFactory {

    public static  Map<String,String> getFiniteValues(String typeCode) {
        return ServiceLibrary.services().getGeneralSQL().getFiniteValues(typeCode) ;
    }


    public static  Map<String,String> getFiniteValuesWithSelect(String typeCode) {
        Map  valuesMap = new LinkedHashMap();
        valuesMap.put("null" ,"--Select one--");
        valuesMap.putAll(ServiceLibrary.services().getGeneralSQL().getFiniteValues(typeCode)) ;
        return  valuesMap ;
    }

    public  static ProductContext generateContext(Login login)
    {
        ProductContext ctx = new ProductContext();
        ctx.setUser(login.getUsername());
        UserService userService = getUserService() ;
        User user =  (User)userService.getById(login.getUsername()) ;
        ctx.setLoggedinCompany(user.getCompany().getId());
        ctx.setLoggedinCompanyCode(user.getCompany().getCode());
        ctx.setCompany(user.getCompany());
        ctx.setUser(user.getUserId());


        return ctx;
    }
    public static String getTokenfromSession(String sessionId)
    {
        return sessionId;
    }

    public static IRadsContext generateContext(HttpServletRequest request, UIPage page) {
        ProductContext ctx = new ProductContext();
        String authToken  =  String.valueOf(request.getHeader("authToken"));
        if(Utils.isNullString(authToken))
            authToken  =  String.valueOf(request.getParameter("authToken"));
        if(Utils.isNullString(authToken))
            authToken = getTokenfromSession(request.getSession().getId());
        LoginService service = getLoginService() ;
        ctx = service.generateContext(authToken);
        CompanyService companyService = getCompanyService();
        ctx.setCompany((Company)companyService.getById(ctx.getLoggedinCompany()));
        ctx.setPageAccessCode(page.getAccessCode());
        return ctx;

    }

    public static IRadsContext generateContext(String  authToken, UIPage page) {
        ProductContext ctx = new ProductContext();
        LoginService service = getLoginService() ;
        ctx = service.generateContext(authToken);
        CompanyService companyService = getCompanyService();
        ctx.setCompany((Company)companyService.getById(ctx.getLoggedinCompany()));
        ctx.setPageAccessCode(page.getAccessCode());
        return ctx;

    }

    public  static User getUser(String userId)
    {
        UserService userService = getUserService() ;
        User user = (User)userService.getById(userId);
        return user;
    }

    public  static Company getCompany(int companyId)
    {
        CompanyService service = getCompanyService() ;
        Company company = (Company)service.getById(companyId);
        return company;
    }


    public static String getFileExtn(String fullName) {
        if (fullName.contains(".")) {
            String laterPart = fullName.substring(fullName.indexOf(".")+1,fullName.length());
            return laterPart;
        }
        return "";
    }


    public static void uploadFile (byte[] bytes, String fileName, ProductContext context, String subFolder )
    {
        if (Utils.isNullString(fileName))
            return;

        FTPClient  ftpClient = new FTPClient();
        try {
            String host = ServiceLibrary.services().getApplicationManager().getDocServerHost();
            String user = ServiceLibrary.services().getApplicationManager().getDocServerUser();
            String pass = ServiceLibrary.services().getApplicationManager().getDocServerPassword();
            String port = ServiceLibrary.services().getApplicationManager().getDocServerPort() ;
            ftpClient.setDefaultTimeout(100000);
            if (Integer.parseInt(port) > 0)
                ftpClient.connect(host,Integer.parseInt(port));
            else
                ftpClient.connect(host);
            ftpClient.enterLocalPassiveMode();
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {

                ftpClient.login(user, pass);
                //FTPFile[] files = ftpClient.listFiles(directory);

                //ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(
                        bytes);
                System.out.println("Start uploading first file");
                changeFolder(ftpClient, context.getLoggedinCompanyCode(),subFolder);
                boolean done = ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
/*				InputStream st2 = ftpClient.retrieveFileStream("/public_html/pics/MP003-a.jpg");
				if (done) {
					System.out
							.println("The first file is uploaded successfully.");
				}
*/			}

        } catch (Exception ex) {
           Logger.logException("Error in uploading", CommonUtil.class, ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                Logger.logException("Error in cosng", CommonUtil.class, ex);
            }
        }


    }

    private static boolean changeFolder(FTPClient ftpClient, String companyCode, String subFolder) throws Exception
    {
        ftpClient.changeWorkingDirectory(companyCode);
        int  returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            ftpClient.makeDirectory(companyCode) ;
            ftpClient.changeWorkingDirectory(companyCode);
        }
        ftpClient.changeWorkingDirectory(subFolder);
        returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            ftpClient.makeDirectory(subFolder) ;
            ftpClient.changeWorkingDirectory(subFolder);

        }
        return true ;
    }

}
