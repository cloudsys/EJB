package com.kylin.ejb.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kylin.ejb.security.SecuredEJB;

/**
 * A simple secured Servlet which calls a secured EJB. Upon successful authentication and authorization the
 * EJB will return the principal's name. Servlet security is implemented using annotations.
 * 
 * @author Kylin Soong
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/SecuredEJBServlet")
@ServletSecurity(@HttpConstraint(rolesAllowed="guest"))
public class SecuredEJBServlet extends HttpServlet {

   private static String PAGE_HEADER = "<html><head/><body>";

   private static String PAGE_FOOTER = "</body></html>";

   // Inject the Secured EJB
   @EJB
   private SecuredEJB securedEJB;

   /**
    * Servlet entry point method which calls securedEJB.getSecurityInfo()
    */
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      PrintWriter writer = resp.getWriter();
      String principal = null;
      String authType = null;
      String remoteUser = null;

      // Get security principal
      principal = securedEJB.getSecurityInfo();
      // Get user name from login principal
      remoteUser = req.getRemoteUser();
      // Get authentication type
      authType = req.getAuthType();

      writer.println(PAGE_HEADER);
      writer.println("<h1>" + "Successfully called Secured EJB " + "</h1>");
      writer.println("<p>" + "Principal  : " + principal + "</p>");
      writer.println("<p>" + "Remote User : " + remoteUser + "</p>");
      writer.println("<p>" + "Authentication Type : " + authType + "</p>");
      writer.println(PAGE_FOOTER);
      writer.close();
   }

}
