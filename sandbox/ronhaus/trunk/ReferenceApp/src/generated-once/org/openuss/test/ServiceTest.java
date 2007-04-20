package org.openuss.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.openuss.lecture.Course;
import org.openuss.lecture.CourseImpl;
import org.openuss.lecture.LectureService;
import org.openuss.registration.Address;
import org.openuss.registration.AddressImpl;
import org.openuss.registration.Assistant;
import org.openuss.registration.AssistantImpl;
import org.openuss.registration.Student;
import org.openuss.registration.StudentImpl;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class ServiceTest extends AbstractTransactionalSpringContextTests {

	private LectureService lectureService;

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "applicationContext.xml" };
		
	}

	public void testPersistReadStudent() {
		
		// create 2 students
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
		this.lectureService.persist(ron);

		Student kai = new StudentImpl();
		kai.setFirstname("Kai");
		kai.setLastname("Stettner");
		kai.setMnr(300000L);
		kai.setRegistration(new Date());
		kai.setEnrollment(new GregorianCalendar(2002, Calendar.APRIL, 01).getTime());
		kai.getMainFields().add("BWL");
		kai.getMainFields().add("Finanzierung");
		Address kaiAddress = new AddressImpl();
		kaiAddress.setNumber(56);
		kaiAddress.setStreet("RHW");
		kaiAddress.setZipCode(48149L);
		kai.setAddress(kaiAddress);
		this.lectureService.persist(kai);

		// are the students in the DB?
		Student ronTest = this.lectureService.readStudent(310046L);
		Student kaiTest = this.lectureService.readStudent(300000L);
		assertNotNull(ronTest);
		assertNotNull(kaiTest);

		// are the Firstnames correct?
		assertEquals(this.lectureService.readStudent(310046L).getFirstname(), "Ron");
		assertEquals(this.lectureService.readStudent(300000L).getFirstname(), "Kai");

		// are the Streets correct?
		assertEquals(this.lectureService.readStudent(310046L).getAddress().getNumber().intValue(), 57);
		assertEquals(this.lectureService.readStudent(300000L).getAddress().getNumber().intValue(), 56);
	}

	public void testPersistReadAssistant() {
		// create 2 assistants
		Assistant ingo = new AssistantImpl();
		ingo.setFirstname("Ingo");
		ingo.setLastname("Düppe");
		ingo.setRegistration(new Date());
		ingo.setDepartment("WI Controlling");
		Address ingoAddress = new AddressImpl();
		ingoAddress.setNumber(1);
		ingoAddress.setStreet("Unbekannter Weg");
		ingoAddress.setZipCode(48149L);
		ingo.setAddress(ingoAddress);
		Long ingoId = this.lectureService.persist(ingo);

		Assistant jan = new AssistantImpl();
		jan.setFirstname("Jan");
		jan.setLastname("vom Brocke");
		jan.setRegistration(new Date());
		jan.setDepartment("WI Controlling");
		Address janAddress = new AddressImpl();
		janAddress.setNumber(22);
		janAddress.setStreet("KeinAhnung Str");
		janAddress.setZipCode(48150L);
		jan.setAddress(janAddress);
		Long janId = this.lectureService.persist(jan);

		// are the assistants in the DB?
		Assistant ingoTest = this.lectureService.readAssistant(ingoId);
		Assistant janTest = this.lectureService.readAssistant(janId);
		assertNotNull(ingoTest);
		assertNotNull(janTest);

		// are the Firstnames correct?
		assertEquals(this.lectureService.readAssistant(ingoId).getFirstname(), "Ingo");
		assertEquals(this.lectureService.readAssistant(janId).getFirstname(), "Jan");

		// are the Streets correct?
		assertEquals(this.lectureService.readAssistant(ingoId).getAddress().getNumber().intValue(), 1);
		assertEquals(this.lectureService.readAssistant(janId).getAddress().getNumber().intValue(), 22);
	}

	public void testPersistReadCourse() {
		// create 1 assistant
		Assistant ingo = new AssistantImpl();
		ingo.setFirstname("Ingo");
		ingo.setLastname("Düppe");
		ingo.setRegistration(new Date());
		ingo.setDepartment("WI Controlling");
		Address ingoAddress = new AddressImpl();
		ingoAddress.setNumber(1);
		ingoAddress.setStreet("Unbekannter Weg");
		ingoAddress.setZipCode(48149L);
		ingo.setAddress(ingoAddress);
		this.lectureService.persist(ingo);

		// create 2 courses
		Course openUSS = new CourseImpl();
		openUSS.setLecturer(ingo);
		openUSS.setMaxParticipants(10);
		openUSS.setName("OpenUSS Projektseminar");
		Long openUSSId = this.lectureService.persist(openUSS);

		Course herbie = new CourseImpl();
		herbie.setLecturer(ingo);
		herbie.setMaxParticipants(2);
		herbie.setName("Herbie Seminar");
		Long herbieId = this.lectureService.persist(herbie);

		// are the courses in the DB?
		Course openUSSTest = this.lectureService.readCourse(openUSSId);
		Course herbieTest = this.lectureService.readCourse(herbieId);
		assertNotNull(openUSSTest);
		assertNotNull(herbieTest);

		// are the Names correct?
		assertEquals(this.lectureService.readCourse(openUSSId).getName(), "OpenUSS Projektseminar");
		assertEquals(this.lectureService.readCourse(herbieId).getName(), "Herbie Seminar");

		// are the Lecturer correct?
		assertEquals(this.lectureService.readCourse(openUSSId).getLecturer().getFirstname(), "Ingo");
		assertEquals(this.lectureService.readCourse(herbieId).getLecturer().getFirstname(), "Ingo");
	}

	public void testEnrollStudentInCourse() {
		// create 1 assistant
		Assistant ingo = new AssistantImpl();
		ingo.setFirstname("Ingo");
		ingo.setLastname("Düppe");
		ingo.setRegistration(new Date());
		ingo.setDepartment("WI Controlling");
		Address ingoAddress = new AddressImpl();
		ingoAddress.setNumber(1);
		ingoAddress.setStreet("Unbekannter Weg");
		ingoAddress.setZipCode(48149L);
		ingo.setAddress(ingoAddress);
		this.lectureService.persist(ingo);

		// create 1 course
		Course openUSS = new CourseImpl();
		openUSS.setLecturer(ingo);
		openUSS.setMaxParticipants(1);
		openUSS.setName("OpenUSS Projektseminar");
		Long openUSSId = this.lectureService.persist(openUSS);

		// create 2 students
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

		Student kai = new StudentImpl();
		kai.setFirstname("Kai");
		kai.setLastname("Stettner");
		kai.setMnr(300000L);
		kai.setRegistration(new Date());
		kai.setEnrollment(new GregorianCalendar(2002, Calendar.APRIL, 01).getTime());
		kai.getMainFields().add("BWL");
		kai.getMainFields().add("Finanzierung");
		Address kaiAddress = new AddressImpl();
		kaiAddress.setNumber(56);
		kaiAddress.setStreet("RHW");
		kaiAddress.setZipCode(48149L);
		kai.setAddress(kaiAddress);

		// enroll 2 students in 1 course
		this.lectureService.enrollStudentInCourse(ron, openUSS);
		this.lectureService.enrollStudentInCourse(kai, openUSS); // Course already full

		// are the students enrolled?
		Set<Student> students = this.lectureService.readCourse(openUSSId).getParticipants();
		assertEquals(students.size(), 1);
		assertEquals(students.iterator().next().getFirstname(), "Ron");
	}

	public void testGetFullCourses() {
		// create 1 assistants
		Assistant ingo = new AssistantImpl();
		ingo.setFirstname("Ingo");
		ingo.setLastname("Düppe");
		ingo.setRegistration(new Date());
		ingo.setDepartment("WI Controlling");
		Address ingoAddress = new AddressImpl();
		ingoAddress.setNumber(1);
		ingoAddress.setStreet("Unbekannter Weg");
		ingoAddress.setZipCode(48149L);
		ingo.setAddress(ingoAddress);
		this.lectureService.persist(ingo);

		// create 2 courses
		Course openUSS = new CourseImpl();
		openUSS.setLecturer(ingo);
		openUSS.setMaxParticipants(2);
		openUSS.setName("OpenUSS Projektseminar");
		this.lectureService.persist(openUSS);

		Course herbie = new CourseImpl();
		herbie.setLecturer(ingo);
		herbie.setMaxParticipants(5);
		herbie.setName("Herbie Seminar");
		this.lectureService.persist(herbie);

		// create 2 students
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
		this.lectureService.persist(ron);

		Student kai = new StudentImpl();
		kai.setFirstname("Kai");
		kai.setLastname("Stettner");
		kai.setMnr(300000L);
		kai.setRegistration(new Date());
		kai.setEnrollment(new GregorianCalendar(2002, Calendar.APRIL, 01).getTime());
		kai.getMainFields().add("BWL");
		kai.getMainFields().add("Finanzierung");
		Address kaiAddress = new AddressImpl();
		kaiAddress.setNumber(56);
		kaiAddress.setStreet("RHW");
		kaiAddress.setZipCode(48149L);
		kai.setAddress(kaiAddress);
		this.lectureService.persist(kai);

		// enroll 2 students in 2 courses
		this.lectureService.enrollStudentInCourse(ron, openUSS);
		this.lectureService.enrollStudentInCourse(kai, openUSS);
		this.lectureService.enrollStudentInCourse(ron, herbie);
		this.lectureService.enrollStudentInCourse(kai, herbie);

		// is a course full?
		Set<Course> courses = this.lectureService.getFullCourses();
		assertEquals(courses.size(), 1);
		assertEquals(courses.iterator().next().getName(), "OpenUSS Projektseminar");
	}

}
