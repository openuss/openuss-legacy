package org.openuss.security.acl;

import org.apache.log4j.Logger;

import org.acegisecurity.acl.basic.AbstractBasicAclEntry;

/**
 * Defines openuss primary permissions:
 * 
 * <p>
 * Base permissions:
 * <ul>
 * <li><b>GRANT</b> - may provide permissions to other users</li>
 * <li><b>CREATE</b> - may create new child objects</li>
 * <li><b>READ</b> - may read the domain object</li>
 * <li><b>UPDATE</b> - may update properties of the domain object</li>
 * <li><b>DELETE</b> - may delete the domain object</li>
 * </ul>
 * </p>
 * <p>
 * Additional permissions of institutes:
 * <ul>
 * <li><b>MANAGE_COURSE_TYPES</b> - may create, read, update, and delete courseTypes
 * of the institute</li>
 * <li><b>MANAGE_PERIODS</b> - may create, read, update, and delete periods of
 * the institute</li>
 * <li><b>MANAGE_ENROLLEMNTS</b> - may create, read, update, and delete
 * courses of the institute</li>
 * <li><b>MANAGE_NEWS</b> - may create, read, update, and delete news of the
 * institute</li>
 * </ul>
 * </p>
 * <p>
 * Additional permissions of courses:
 * <ul>
 * <li><b>PARTICIPATE</b> - may participate on course</li>
 * <li><b>ASSIST</b> - may assist course</li>
 * </ul>
 * </p>
 * 
 * @author Ingo Dueppe
 */
public class LectureAclEntry extends AbstractBasicAclEntry {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LectureAclEntry.class);

	private static final long serialVersionUID = -3990272388367808370L;

	// Base permissions
	public static final int NOTHING 			= 1 << 0;
		
	public static final int OWN 				= 1 << 1;
	public static final int GRANT 				= 1 << 2;
	public static final int CREATE 				= 1 << 3;
	public static final int READ 				= 1 << 4;
	public static final int UPDATE 				= 1 << 5;
	public static final int DELETE 				= 1 << 6;
	
	// Additional permissions to institutes
	public static final int MANAGE_COURSE_TYPES 	= 1 << 7;
	public static final int MANAGE_COURSES 			= 1 << 8;
	public static final int MANAGE_NEWS 			= 1 << 9;

	// Additional permissions to courses
	public static final int PARTICIPATE 		= 1 << 10;
	public static final int ASSIST 				= 1 << 11;
	
	// Additional permissions to universities
	public static final int MANAGE_UNIVERSITY		= 1 << 12;
	public static final int MANAGE_PERIODS 			= 1 << 13;
	
	// Additional permissions to departments
	public static final int MANAGE_DEPARTMENT		= 1 << 14;

	// Default combinations of base permissions
	public static final int INSTITUTE_TUTOR = PARTICIPATE | ASSIST | READ;
	public static final int INSTITUTE_ASSIST = CREATE | UPDATE | DELETE | MANAGE_NEWS | MANAGE_COURSE_TYPES | MANAGE_PERIODS | MANAGE_COURSES | INSTITUTE_TUTOR;
	public static final int INSTITUTE_ADMINISTRATION = GRANT | INSTITUTE_ASSIST;
	public static final int INSTITUTE_OWN = DELETE | INSTITUTE_ADMINISTRATION;
	
	public static final int DEPARTMENT_ADMINISTRATION = PARTICIPATE | ASSIST | READ | CREATE | UPDATE | DELETE | MANAGE_NEWS | MANAGE_DEPARTMENT | GRANT;
	public static final int UNIVERSITY_ADMINISTRATION = MANAGE_UNIVERSITY | MANAGE_PERIODS | DEPARTMENT_ADMINISTRATION;
	
	// Default combinations of base course permissions
	public static final int COURSE_PARTICIPANT = READ | PARTICIPATE;
	
	// Default combinations of base group permissions
	public static final int GROUP_MEMBER = READ | PARTICIPATE | ASSIST | UPDATE;
	public static final int GROUP_MODERATOR = CREATE | DELETE | MANAGE_NEWS | GROUP_MEMBER;
	
	
	// Combinations of base permissions we permit
	public static final int RU = READ | UPDATE;
	public static final int CRU = CREATE | RU;
	public static final int CRUD = CRU | DELETE;
	public static final int GCRUD = GRANT | CRUD;
	public static final int OGCRUD = OWN | GCRUD;
	
	private static final int[] validPermissions = {
		GRANT, CREATE, READ, UPDATE, DELETE, ASSIST, PARTICIPATE, 
		RU, CRU, CRUD, GCRUD, OGCRUD,
		UNIVERSITY_ADMINISTRATION, MANAGE_UNIVERSITY,
		DEPARTMENT_ADMINISTRATION, MANAGE_DEPARTMENT,
		INSTITUTE_TUTOR, INSTITUTE_ASSIST, INSTITUTE_ADMINISTRATION, INSTITUTE_OWN
	};
	

	@Override
	public int[] getValidPermissions() {
		return validPermissions.clone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String printPermissionsBlock(int mask) {
		StringBuffer sb = new StringBuffer();
		sb.append(((mask & OWN) == OWN) ? "O" : "-");
		sb.append(((mask & GRANT) == GRANT) ? "G" : "-");
		sb.append(((mask & CREATE) == CREATE) ? "C" : "-");
		sb.append(((mask & READ) == READ) ? "R" : "-");
		sb.append(((mask & UPDATE) == UPDATE) ? "U" : "-");
		sb.append(((mask & DELETE) == DELETE) ? "D" : "-");
		sb.append("|");
		sb.append(((mask & MANAGE_COURSE_TYPES) == MANAGE_COURSE_TYPES) ? "MS" : "--");
		sb.append(((mask & MANAGE_PERIODS) == MANAGE_PERIODS) ? "MP" : "--");
		sb.append(((mask & MANAGE_COURSES) == MANAGE_COURSES) ? "ME" : "--");
		sb.append(((mask & MANAGE_NEWS) == MANAGE_NEWS) ? "MN" : "--");
		sb.append("|");
		sb.append(((mask & PARTICIPATE) == PARTICIPATE) ? "P" : "-");
		sb.append(((mask & ASSIST) == ASSIST) ? "A" : "-");
		return sb.toString();
	}

	@Override
    protected boolean isPermitted(int maskToCheck, int permissionToCheck) {
		if (logger.isDebugEnabled()) {
			logger.debug("user priveledge = "+printPermissionsBlock(maskToCheck)+ " needed permission = " +printPermissionsBlock(permissionToCheck));
			logger.debug("user priveledge = "+Integer.toBinaryString(maskToCheck)+ " needed permission = "+Integer.toBinaryString(permissionToCheck));
		}
        return ((maskToCheck & permissionToCheck) == permissionToCheck);
    }
	
	/* ---------- getters for static constants to accessed by el expressions ------- */

	public int getASSIST() {
		return ASSIST;
	}

	public int getCREATE() {
		return CREATE;
	}

	public int getCRU() {
		return CRU;
	}

	public int getCRUD() {
		return CRUD;
	}

	public int getDELETE() {
		return DELETE;
	}

	public int getDEPARTMENT_ADMINISTRATION() {
		return DEPARTMENT_ADMINISTRATION;
	}

	public int getMANAGE_DEPARTMENT() {
		return MANAGE_DEPARTMENT;
	}

	public int getMANAGE_UNIVERSITY() {
		return MANAGE_UNIVERSITY;
	}

	public int getUNIVERSITY_ADMINISTRATION() {
		return UNIVERSITY_ADMINISTRATION;
	}

	public int getINSTITUTE_ADMINISTRATION() {
		return INSTITUTE_ADMINISTRATION;
	}

	public int getINSTITUTE_ASSIST() {
		return INSTITUTE_ASSIST;
	}

	public int getINSTITUTE_OWN() {
		return INSTITUTE_OWN;
	}

	public int getINSTITUTE_TUTOR() {
		return INSTITUTE_TUTOR;
	}

	public int getGCRUD() {
		return GCRUD;
	}

	public int getGRANT() {
		return GRANT;
	}

	public int getMANAGE_COURSES() {
		return MANAGE_COURSES;
	}

	public int getMANAGE_NEWS() {
		return MANAGE_NEWS;
	}

	public int getMANAGE_PERIODS() {
		return MANAGE_PERIODS;
	}

	public int getMANAGE_COURSE_TYPES() {
		return MANAGE_COURSE_TYPES;
	}

	public int getNOTHING() {
		return NOTHING;
	}

	public int getOGCRUD() {
		return OGCRUD;
	}

	public int getOWN() {
		return OWN;
	}

	public int getPARTICIPATE() {
		return PARTICIPATE;
	}

	public int getREAD() {
		return READ;
	}

	public int getRU() {
		return RU;
	}

	public int getUPDATE() {
		return UPDATE;
	}

	public static int getGROUP_MEMBER() {
		return GROUP_MEMBER;
	}

	public static int getGROUP_MODERATOR() {
		return GROUP_MODERATOR;
	}
}
