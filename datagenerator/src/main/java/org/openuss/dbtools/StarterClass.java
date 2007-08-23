package org.openuss.dbtools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityType;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

public class StarterClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long time1 = System.currentTimeMillis();
		long time2 = System.currentTimeMillis();

		try {

			DataCreator dc = new DataCreator();

			dc.createAdminSecureContext();
			
			// Create unused Users
			List<User> unusedUsers = new ArrayList<User>();
			for (int i = 0; i < 50; i++) {
				unusedUsers.add(dc.createUniqueUsers());
			}

			// Create 3 Universities and 3 "Owner"
			List<User> universityOwner = new ArrayList<User>();
			for (int i = 0; i < 3; i++) {
				universityOwner.add(dc.createUniqueUsers());
			}
			List<Long> universitiesIds = new ArrayList<Long>();
			int uniUserNumber = 0;
			for (int i = 0; i < 2; i++) {
				universitiesIds.add(dc.createUniqueUniversity(universityOwner.get(uniUserNumber).getId(),
						UniversityType.UNIVERSITY));
				uniUserNumber++;
			}
			for (int i = 0; i < 1; i++) {
				universitiesIds.add(dc.createUniqueUniversity(universityOwner.get(uniUserNumber).getId(),
						UniversityType.COMPANY));
			}

			// Create 3 Periods for each University
			List<Long> periodIds = new ArrayList<Long>();
			// active
			for (Long universityId : universitiesIds) {
				periodIds.add(dc.createUniquePeriod(new GregorianCalendar(2007, 7, 1).getTime(), new GregorianCalendar(
						2008, 3, 1).getTime(), universityId));
			}
			// past
			for (Long universityId : universitiesIds) {
				periodIds.add(dc.createUniquePeriod(new GregorianCalendar(2006, 10, 1).getTime(),
						new GregorianCalendar(2007, 6, 30).getTime(), universityId));
			}
			// future
			for (Long universityId : universitiesIds) {
				periodIds.add(dc.createUniquePeriod(new GregorianCalendar(2008, 3, 2).getTime(), new GregorianCalendar(
						2008, 10, 1).getTime(), universityId));
			}

			// Create 5 Departments for each University
			List<Long> departmentIds = new ArrayList<Long>();
			List<Long> instituteIds = new ArrayList<Long>();
			// 3 official
			for (Long universityId : universitiesIds) {
				int j = 0;
				for (int i = 0; i < 3; i++) {
					Long departmentId = dc.createUniqueDepartment(universityOwner.get(j).getId(), universityId,
							DepartmentType.OFFICIAL);
					DepartmentInfo departmentInfo = dc.findDepartment(departmentId);
					departmentIds.add(departmentId);

					// Create 5 Institutes and 5 "Owner" for each Department (apply/accept already included)
					for (int n = 0; n < 5; n++) {
						Long userId = dc.createUniqueUsers().getId();
						Long instituteId = dc.createUniqueInstitute(userId, departmentId);
						instituteIds.add(instituteId);
					}
					// Create 1 Institutes and 1 "Owner" for each Department and only apply
					List<DepartmentInfo> departmentsNonOfficial = dc.findDepartmentsByUniversityAndType(universityId, DepartmentType.NONOFFICIAL);
					for (int n = 0; n < 1; n++) {
						Long userId = dc.createUniqueUsers().getId();
						UserInfo userInfo = dc.findUser(userId);
						Long instituteId = dc.createUniqueInstitute(userId, departmentsNonOfficial.get(0).getId());
						InstituteInfo instituteInfo = dc.findInstitute(instituteId);
						dc.applyInstituteAtDepartment(departmentInfo, instituteInfo, userInfo);
						instituteIds.add(instituteId);
					}

					// Create 1 Institutes and 1 "Owner" for each Department and don't apply
					for (int n = 0; n < 1; n++) {
						instituteIds.add(dc.createUniqueInstitute(dc.createUniqueUsers().getId(), departmentsNonOfficial.get(0).getId()));
					}
				}
				j++;
			}
			// 2 nonofficial
			for (Long universityId : universitiesIds) {
				int j = 0;
				for (int i = 0; i < 2; i++) {
					Long departmentId = dc.createUniqueDepartment(universityOwner.get(j).getId(), universityId,
							DepartmentType.NONOFFICIAL);
					departmentIds.add(departmentId);

					// Create 5 Institutes and 5 "Owner" for each Department (no Application required)
					for (int n = 0; n < 5; n++) {
						Long userId = dc.createUniqueUsers().getId();
						Long instituteId = dc.createUniqueInstitute(userId, departmentId);
						instituteIds.add(instituteId);
					}
				}
				j++;
			}

			// Create 3 CourseTypes and 3 Courses for each Period for each Institute
			List<Long> courseTypeIds = new ArrayList<Long>();
			for (Long instituteId : instituteIds) {
				for (int i = 0; i < 3; i++) {
					Long courseTypeId = dc.createUniqueCourseType(instituteId);
					courseTypeIds.add(courseTypeId);

					InstituteInfo instituteInfo = dc.findInstitute(instituteId);
					if (instituteInfo.getDepartmentId() != null) {
						DepartmentInfo departmentInfo = dc.findDepartment(instituteInfo.getDepartmentId());
						List periods = dc.findPeriodsByUniversity(departmentInfo.getUniversityId());
						Iterator iterator = periods.iterator();
						while (iterator.hasNext()) {
							dc.createUniqueCourse(((PeriodInfo) iterator.next()).getId(), courseTypeId);
						}
					}
				}
			}
			
			// Create News
			dc.createNews("Die ersten Daten sind da", "Hier sind sie nun, die ersten Daten für OpenUSS. Nun aber ran Jungs!");

			dc.destroySecureContext();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			time2 = System.currentTimeMillis();
			long time3 = time2 - time1;

			DecimalFormat format = new DecimalFormat("00");

			long hour = (time3 % 86400000) / 3600000;
			long min = (time3 % 3600000) / 60000;
			long sec = (time3 % 60000) / 1000;

			System.out.println("************************");
			System.out.println("Total Time: " + format.format(hour) + "h " + format.format(min) + "m "
					+ format.format(sec) + "s");
			System.out.println("************************");
		}

	}

}
