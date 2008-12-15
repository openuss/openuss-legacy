/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.campussource.cse.lms.testclient;

import de.campussource.cse.cse.schema.datatypes.AccountType;
import de.campussource.cse.cse.schema.datatypes.CategoryType;
import de.campussource.cse.cse.schema.datatypes.CourseType;
import de.campussource.cse.cse.schema.datatypes.ResultType;
import de.campussource.cse.cse.wsdl.lmsgenericadapter.AccountFault;
import de.campussource.cse.cse.wsdl.lmsgenericadapter.CategoryFault;
import de.campussource.cse.cse.wsdl.lmsgenericadapter.CourseFault;
import de.campussource.cse.cse.wsdl.lmsgenericadapter.LMSGenericAdapterPortType;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author idueppe
 */
@Stateless
@WebService(serviceName = "LMSGenericAdapterService", portName = "LMSGenericAdapterPort", endpointInterface = "de.campussource.cse.cse.wsdl.lmsgenericadapter.LMSGenericAdapterPortType", targetNamespace = "http://cse.campussource.de/cse/wsdl/LMSGenericAdapter", wsdlLocation = "META-INF/wsdl/LMSTestClient/LMSGenericAdapterWrapper.wsdl")
public class LMSTestClient implements LMSGenericAdapterPortType {

    public ResultType createCourse(CourseType part) throws CourseFault {
        System.out.print("createCourse() for course " + part.getTitle());
        ResultType result = new ResultType();
        result.setClientId("10" + System.nanoTime());
        return result;
    }

    public ResultType updateCourse(CourseType part) throws CourseFault {
        System.out.print("updateCourse() for course " + part.getTitle());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }

    public ResultType deleteCourse(CourseType part) throws CourseFault {
        System.out.print("deleteCourse() for course " + part.getTitle());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }

    public ResultType createAccount(AccountType part) throws AccountFault {
        System.out.print("createAccount() for account " + part.getEmailAddress());
        ResultType result = new ResultType();
        result.setClientId("11" + System.nanoTime());
        return result;
    }

    public ResultType updateAccount(AccountType part) throws AccountFault {
        System.out.print("updateAccount() for account " + part.getEmailAddress());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }

    public ResultType deleteAccount(AccountType part) throws AccountFault {
        System.out.print("deleteAccount() for account " + part.getEmailAddress());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }

    public ResultType createCategory(CategoryType part) throws CategoryFault {
        System.out.print("createCategory() for category " + part.getName());
        ResultType result = new ResultType();
        result.setClientId("12" + System.nanoTime());
        return result;
    }

    public ResultType updateCategory(CategoryType part) throws CategoryFault {
        System.out.print("updateCategory() for category " + part.getName());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }

    public ResultType deleteCategory(CategoryType part) throws CategoryFault {
        System.out.print("deleteCategory() for category " + part.getName());
        ResultType result = new ResultType();
        result.setClientId(part.getClientId());
        return result;
    }
}
