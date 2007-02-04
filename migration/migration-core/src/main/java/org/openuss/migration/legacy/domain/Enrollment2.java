package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Enrollment2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -7778414866446228466L;
	private String id;
	private Subject2 subject2;
	private Semester2 semester2;
	private Faculty2 faculty2;
	private Character lecturematerials;
	private Character chat;
	private Character discussion;
	private Character mailinglist;
	private Character empty;
	private Character forpublic;
	private Character quiz;
	private Character webaccess;
	private Character fslinstall;
	private Character consultancy;
	private Character vofi;
	private Set<Enrollmentaccesslist2> enrollmentaccesslist2s = new HashSet<Enrollmentaccesslist2>(0);
	private Set<Studentenrollment2> studentenrollment2s = new HashSet<Studentenrollment2>(0);
	private Set<Lecture2> lecture2s = new HashSet<Lecture2>(0);
	private Set<Consultancy2> consultancy2s = new HashSet<Consultancy2>(0);
	private Set<Mailinglistmessage2> mailinglistmessage2s = new HashSet<Mailinglistmessage2>(0);
	private Set<Quiz2> quiz2s = new HashSet<Quiz2>(0);
	private Set<Discussionitem2> discussionitem2s = new HashSet<Discussionitem2>(0);
	private Set<Assistantenrollment2> assistantenrollment2s = new HashSet<Assistantenrollment2>(0);
	private Set<Mailinglist2> mailinglist2s = new HashSet<Mailinglist2>(0);

	// Constructors

	/** default constructor */
	public Enrollment2() {
	}

	/** minimal constructor */
	public Enrollment2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Enrollment2(String id, Subject2 subject2, Semester2 semester2, Faculty2 faculty2, Character lecturematerials,
			Character chat, Character discussion, Character mailinglist, Character empty, Character forpublic,
			Character quiz, Character webaccess, Character fslinstall, Character consultancy, Character vofi,
			Set<Enrollmentaccesslist2> enrollmentaccesslist2s, Set<Studentenrollment2> studentenrollment2s,
			Set<Lecture2> lecture2s, Set<Consultancy2> consultancy2s, Set<Mailinglistmessage2> mailinglistmessage2s,
			Set<Quiz2> quiz2s, Set<Discussionitem2> discussionitem2s, Set<Assistantenrollment2> assistantenrollment2s,
			Set<Mailinglist2> mailinglist2s) {
		this.id = id;
		this.subject2 = subject2;
		this.semester2 = semester2;
		this.faculty2 = faculty2;
		this.lecturematerials = lecturematerials;
		this.chat = chat;
		this.discussion = discussion;
		this.mailinglist = mailinglist;
		this.empty = empty;
		this.forpublic = forpublic;
		this.quiz = quiz;
		this.webaccess = webaccess;
		this.fslinstall = fslinstall;
		this.consultancy = consultancy;
		this.vofi = vofi;
		this.enrollmentaccesslist2s = enrollmentaccesslist2s;
		this.studentenrollment2s = studentenrollment2s;
		this.lecture2s = lecture2s;
		this.consultancy2s = consultancy2s;
		this.mailinglistmessage2s = mailinglistmessage2s;
		this.quiz2s = quiz2s;
		this.discussionitem2s = discussionitem2s;
		this.assistantenrollment2s = assistantenrollment2s;
		this.mailinglist2s = mailinglist2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Subject2 getSubject() {
		return this.subject2;
	}

	public void setSubject(Subject2 subject2) {
		this.subject2 = subject2;
	}

	public Semester2 getSemester() {
		return this.semester2;
	}

	public void setSemester(Semester2 semester2) {
		this.semester2 = semester2;
	}

	public Faculty2 getFaculty() {
		return this.faculty2;
	}

	public void setFaculty(Faculty2 faculty2) {
		this.faculty2 = faculty2;
	}

	public Character getLecturematerials() {
		return this.lecturematerials;
	}

	public void setLecturematerials(Character lecturematerials) {
		this.lecturematerials = lecturematerials;
	}

	public Character getChat() {
		return this.chat;
	}

	public void setChat(Character chat) {
		this.chat = chat;
	}

	public Character getDiscussion() {
		return this.discussion;
	}

	public void setDiscussion(Character discussion) {
		this.discussion = discussion;
	}

	public Character getMailinglist() {
		return this.mailinglist;
	}

	public void setMailinglist(Character mailinglist) {
		this.mailinglist = mailinglist;
	}

	public Character getEmpty() {
		return this.empty;
	}

	public void setEmpty(Character empty) {
		this.empty = empty;
	}

	public Character getForpublic() {
		return this.forpublic;
	}

	public void setForpublic(Character forpublic) {
		this.forpublic = forpublic;
	}

	public Character getQuiz() {
		return this.quiz;
	}

	public void setQuiz(Character quiz) {
		this.quiz = quiz;
	}

	public Character getWebaccess() {
		return this.webaccess;
	}

	public void setWebaccess(Character webaccess) {
		this.webaccess = webaccess;
	}

	public Character getFslinstall() {
		return this.fslinstall;
	}

	public void setFslinstall(Character fslinstall) {
		this.fslinstall = fslinstall;
	}

	public Character getConsultancy() {
		return this.consultancy;
	}

	public void setConsultancy(Character consultancy) {
		this.consultancy = consultancy;
	}

	public Character getVofi() {
		return this.vofi;
	}

	public void setVofi(Character vofi) {
		this.vofi = vofi;
	}

	public Set<Enrollmentaccesslist2> getEnrollmentaccesslists() {
		return this.enrollmentaccesslist2s;
	}

	public void setEnrollmentaccesslists(Set<Enrollmentaccesslist2> enrollmentaccesslist2s) {
		this.enrollmentaccesslist2s = enrollmentaccesslist2s;
	}

	public Set<Studentenrollment2> getStudentenrollments() {
		return this.studentenrollment2s;
	}

	public void setStudentenrollments(Set<Studentenrollment2> studentenrollment2s) {
		this.studentenrollment2s = studentenrollment2s;
	}

	public Set<Lecture2> getLectures() {
		return this.lecture2s;
	}

	public void setLectures(Set<Lecture2> lecture2s) {
		this.lecture2s = lecture2s;
	}

	public Set<Consultancy2> getConsultancies() {
		return this.consultancy2s;
	}

	public void setConsultancies(Set<Consultancy2> consultancy2s) {
		this.consultancy2s = consultancy2s;
	}

	public Set<Mailinglistmessage2> getMailinglistmessages() {
		return this.mailinglistmessage2s;
	}

	public void setMailinglistmessages(Set<Mailinglistmessage2> mailinglistmessage2s) {
		this.mailinglistmessage2s = mailinglistmessage2s;
	}

	public Set<Quiz2> getQuizs() {
		return this.quiz2s;
	}

	public void setQuizs(Set<Quiz2> quiz2s) {
		this.quiz2s = quiz2s;
	}

	public Set<Discussionitem2> getDiscussionitems() {
		return this.discussionitem2s;
	}

	public void setDiscussionitems(Set<Discussionitem2> discussionitem2s) {
		this.discussionitem2s = discussionitem2s;
	}

	public Set<Assistantenrollment2> getAssistantenrollments() {
		return this.assistantenrollment2s;
	}

	public void setAssistantenrollments(Set<Assistantenrollment2> assistantenrollment2s) {
		this.assistantenrollment2s = assistantenrollment2s;
	}

	public Set<Mailinglist2> getMailinglists() {
		return this.mailinglist2s;
	}

	public void setMailinglists(Set<Mailinglist2> mailinglist2s) {
		this.mailinglist2s = mailinglist2s;
	}

}
