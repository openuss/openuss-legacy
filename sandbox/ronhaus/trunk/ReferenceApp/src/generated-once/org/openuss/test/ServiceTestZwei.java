package org.openuss.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.openuss.lecture.LectureService;
import org.openuss.registration.Address;
import org.openuss.registration.AddressImpl;
import org.openuss.registration.Student;
import org.openuss.registration.StudentImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTestZwei {

	public static void main(String[] args) {
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");		
		LectureService service = (LectureService) factory.getBean("LectureService");
		
		Student ron = new StudentImpl();
		ron.setFirstname("Ron");
		ron.setLastname("Haus");
		ron.setMnr(310046L);
		ron.setRegistration(new Date());
		ron.setEnrollment(new GregorianCalendar(2002, Calendar.AUGUST, 01).getTime());
		ron.getMainFields().add("BWL");
		ron.getMainFields().add("Informationssysteme");
		Address ronAddress = new AddressImpl();
		ronAddress.setNumber(57);
		ronAddress.setStreet("RHW");
		ronAddress.setZipCode(48149L);
		ron.setAddress(ronAddress);
		service.testTransactionManager(ron);
	}

}
