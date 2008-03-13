// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openuss.security.User;

/**
 * @see org.openuss.seminarpool.SeminarpoolAllocationService
 * @author Stefan Thiemann
 */
public class SeminarpoolAllocationServiceImpl extends
		org.openuss.seminarpool.SeminarpoolAllocationServiceBase {

	int subgroups;
	int maxprio;
	int a, b, c;
	int variables;
	int sideconditions;
	List<SeminarUserRegistrationInfo> registrations;
	Collection<CourseSeminarpoolAllocation> seminars;
	List<CourseGroup> courseGroupList;
	double[][] coursegroups;
	double[] coursegroups2;
	double[] capacity;
	double[] neededSeminars;
	double[] endfunction;
	double[][] result;
	int conflicts = 0;
	long[][] timeconflict;
	Simplex simplex;
	long[][] table;
	Seminarpool sp;
	double[] sc1 = new double[variables + 1];

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAllocationService#generateAllocation(java.lang.Long)
	 */
	protected void handleGenerateAllocation(java.lang.Long seminarpoolId)
			throws java.lang.Exception {
		sp = getSeminarpoolDao().load(seminarpoolId);
		registrations = getSeminarpoolAdministrationService().getRegistrations(
				seminarpoolId);
		maxprio = sp.getPriorities();

		courseGroupList = new ArrayList<CourseGroup>();

		seminars = sp.getCourseSeminarpoolAllocation();

		// Identity number of groups
		subgroups = 0;

		for (CourseSeminarpoolAllocation cspa : seminars) {
			subgroups += cspa.getCourseGroup().size();
		}

		generateGroupTable();

		// Copy 2-dim array to 1-dim array for later binary search
		for (int i = 0; i < subgroups; i++) {
			coursegroups2[i] = coursegroups[i][0];
		}

		neededSeminars = new double[registrations.size()];

		// Save needed seminars for each user in array
		c = 0;
		for (SeminarUserRegistrationInfo suri : registrations) {
			neededSeminars[c] = suri.getNeededSeminars();
			c++;
		}

		identifyTimeConflicts();
		initSimplex();
		generateUserTable();
		generateEndfunction();
		generateSideconditions();
		
		// Calculate
		result = simplex.getResult();
		
		identifyAllocatedCourses();
		
		sp.setSeminarpoolStatus(SeminarpoolStatus.REVIEWPHASE);
	}

	@SuppressWarnings("deprecation")
	private boolean timeconflict(CourseGroup cg1, CourseGroup cg2) {
		boolean conflict = false;
		for (CourseSchedule cs1 : cg1.getCourseSchedule()) {
			for (CourseSchedule cs2 : cg2.getCourseSchedule()) {
				if (cs1.getDayOfWeek() == cs2.getDayOfWeek()
						&& ((cs1.getStartTime().getHours() >= cs2
								.getStartTime().getHours()
								&& cs1.getStartTime().getMinutes() >= cs2
										.getStartTime().getMinutes()
								&& cs1.getStartTime().getHours() <= cs2
										.getEndTime().getHours() && cs1
								.getStartTime().getMinutes() <= cs2
								.getEndTime().getMinutes()) || (cs2
								.getStartTime().getHours() >= cs1
								.getStartTime().getHours()
								&& cs2.getStartTime().getMinutes() >= cs1
										.getStartTime().getMinutes()
								&& cs2.getStartTime().getHours() <= cs1
										.getEndTime().getHours() && cs2
								.getStartTime().getMinutes() <= cs1
								.getEndTime().getMinutes()))) {
					conflict = true;
					break;
				}
			}
		}
		return conflict;
	}

	private void generateGroupTable() {
		// Generate table, where can be seen, which groups belong to one course
		coursegroups = new double[subgroups][2];
		coursegroups2 = new double[subgroups];
		b = 1;
		c = 0;
		for (CourseSeminarpoolAllocation cspa : seminars) {
			for (CourseGroup cg : cspa.getCourseGroup()) {
				courseGroupList.add(cg);
				coursegroups[c][0] = cg.getId();
				coursegroups[c][1] = b;
				c++;
			}
			b++;
		}

		// Sort table by groupId
		for (int i = 0; i < coursegroups.length - 1; i++) {
			int minPos = i;
			for (int j = i + 1; j < coursegroups.length; j++) {
				if (coursegroups[j][0] < coursegroups[minPos][0])
					minPos = j;
			}
			double temp[] = coursegroups[minPos];
			coursegroups[minPos] = coursegroups[i];
			coursegroups[i] = temp;
		}
	}

	private void identifyTimeConflicts() {
		conflicts = 0;
		timeconflict = new long[subgroups * subgroups][2];
		// Time overlaps

		for (int i = 0; i < courseGroupList.size() - 1; i++) {
			for (int j = i + 1; j < courseGroupList.size(); j++) {
				if (courseGroupList.get(i).getCourseSchedule().size() > 0
						&& courseGroupList.get(j).getCourseSchedule().size() > 0
						&& timeconflict(courseGroupList.get(i), courseGroupList
								.get(j))) {
					timeconflict[conflicts][0] = courseGroupList.get(i).getId();
					timeconflict[conflicts][1] = courseGroupList.get(j).getId();
					conflicts++;
				}
			}
		}
	}

	private void initSimplex() {
		// Initialize simplex
		variables = subgroups * registrations.size();
		sideconditions = subgroups + registrations.size() + 2 * variables
				+ registrations.size() * seminars.size() + conflicts
				* registrations.size();
		simplex = new Simplex(variables, sideconditions, false);
		sc1 = new double[variables + 1];
	}

	private void generateUserTable() {
		table = new long[variables][3];
		capacity = new double[subgroups];
		c = 0;
		a = 0;

		// Build table and save capacity for each user in array

		for (SeminarUserRegistration sur : sp.getSeminarUserRegistration()) {
			a = 0;
			for (SeminarPriority sprio : sur.getSeminarPriority()) {
				for (CourseGroup cg : sprio.getCourseSeminarPoolAllocation()
						.getCourseGroup()) {
					table[c][0] = sprio.getSeminarUserRegistration().getUser()
							.getId();
					table[c][1] = cg.getId();
					table[c][2] = maxprio - sprio.getPriority();
					c++;
					capacity[a] = cg.getCapacity();
					a++;
				}
			}

			for (int i = 0; i < subgroups - a; i++) {
				table[c][0] = sur.getUser().getId();
				table[c][1] = Long.MAX_VALUE;
				table[c][2] = -1;
				c++;
				a++;
			}
		}

		// Sort table by groupId for each user
		for (int k = 0; k < registrations.size(); k++) {
			for (int i = k * subgroups; i < k * subgroups + subgroups - 1; i++) {
				int minPos = i;
				for (int j = i + 1; j < k * subgroups + subgroups; j++) {
					if (table[j][1] < table[minPos][1])
						minPos = j;
				}
				long temp[] = table[minPos];
				table[minPos] = table[i];
				table[i] = temp;
			}
		}
	}
	
	private void generateEndfunction() throws Exception{
		endfunction = new double[variables];
		for (c = 0; c < variables; c++) {
			endfunction[c] = (double) table[c][2];
		}

		simplex.newEF(endfunction);
	}
	
	private void generateSideconditions() throws Exception{
		generateGroupRestrictions();
		generateParticipantsRestrictions();
		generateParticipantsCourseRestrictions();
		generateTimeRestrictions();
		generateVariablesRestrictions();	
	}
	
	private void generateVariablesRestrictions() throws Exception{
		// The variables must be between zero and one
		for (int i = 0; i < variables; i++) {
			java.util.Arrays.fill(sc1, 0);
			sc1[i] = 1;
			sc1[variables] = 0;
			simplex.newSC(sc1, ">=");
			sc1[variables] = 1;
			simplex.newSC(sc1, "<=");
		}	
	}

	private void generateParticipantsCourseRestrictions() throws Exception{
		// Each participant may only have one group of one course
		for (a = 0; a < registrations.size(); a++) {
			for (b = 1; b <= seminars.size(); b++) {
				java.util.Arrays.fill(sc1, 0);
				for (c = 0; c < subgroups; c++) {
					double value = 0.0;
					if (coursegroups[c][1] == b) {
						value = 1.0;
					}
					sc1[subgroups * a + c] = value;
				}
				sc1[variables] = 1.0;
				simplex.newSC(sc1, "<=");
			}
		}	
	}

	private void generateTimeRestrictions() throws Exception{
		// Time restrictions
		for (a = 0; a < registrations.size(); a++) {
			for (b = 0; b < conflicts; b++) {
				java.util.Arrays.fill(sc1, 0);
				int index1 = java.util.Arrays.binarySearch(coursegroups2,
						timeconflict[b][0]);
				int index2 = java.util.Arrays.binarySearch(coursegroups2,
						timeconflict[b][1]);
				for (c = 0; c < subgroups; c++) {
					double value = 0.0;
					if (c == index1 || c == index2) {
						value = 1.0;
					}
					sc1[subgroups * a + c] = value;
				}
				sc1[variables] = 1.0;
				simplex.newSC(sc1, "<=");
			}
		}	
	}

	private void generateParticipantsRestrictions() throws Exception{
		// Participants
		for (a = 0; a < registrations.size(); a++) {
			for (b = 0; b < variables; b += subgroups) {
				double value = 0.0;
//				if (b == a * subgroups) {
//					value = 1.0;
//				}
				for (c = 0; c < subgroups; c++) {
					 if(b == a * subgroups && table[a*subgroups+c][2]!=-1){
					 value=1.0;
					 }
					 else{
						 value=0.0;
					 }
					sc1[b + c] = value;
				}
			}
			sc1[variables] = neededSeminars[a];
			simplex.newSC(sc1, "<=");
		}	
	}

	private void generateGroupRestrictions() throws Exception{
		// Participants restrictions for subgroups
		for (c = 0; c < subgroups; c++) {
			for (int i = 0; i < variables; i += subgroups) {
				for (int j = 0; j < subgroups; j++) {
					double value = 0.0;
					if (j == c) {
						value = 1.0;
					}
					sc1[i + j] = value;
				}
			}
			sc1[variables] = capacity[c];
			simplex.newSC(sc1, "<=");
		}
	}

	private void identifyAllocatedCourses(){
		// Identify allocated courses
		for (int i = 0; i < result.length-1; i++) {
			if (result[i][0] <= variables && result[i][1] == 1.0) {
				CourseGroup cg = getCourseGroupDao().load(
						table[(int) result[i][0] - 1][1]);
				User user = getUserDao().load(table[(int) result[i][0] - 1][0]);
				cg.addUser(user);
			}
		}
	}

}