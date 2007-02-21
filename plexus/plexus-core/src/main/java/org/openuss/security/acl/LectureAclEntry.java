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
 * Additional permissions of faculties:
 * <ul>
 * <li><b>MANAGE_SUBJECTS</b> - may create, read, update, and delete subjects
 * of the faculty</li>
 * <li><b>MANAGE_PERIODS</b> - may create, read, update, and delete periods of
 * the faculty</li>
 * <li><b>MANAGE_ENROLLEMNTS</b> - may create, read, update, and delete
 * enrollments of the faculty</li>
 * <li><b>MANAGE_NEWS</b> - may create, read, update, and delete news of the
 * faculty</li>
 * </ul>
 * </p>
 * <p>
 * Additional permissions of enrollments:
 * <ul>
 * <li><b>PARTICIPATE</b> - may participate on enrollment</li>
 * <li><b>ASSIST</b> - may assist enrollment</li>
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

	// Additional permissions to faculties
	public static final int MANAGE_SUBJECTS 	= 1 << 7;
	public static final int MANAGE_PERIODS 		= 1 << 8;
	public static final int MANAGE_ENROLLMENTS 	= 1 << 9;
	public static final int MANAGE_NEWS 		= 1 << 10;

	// Additional permissions to enrollments
	public static final int PARTICIPATE 		= 1 << 11;
	public static final int ASSIST 				= 1 << 12;

	// Default combinations of base permissions
	public static final int FACULTY_TUTOR = PARTICIPATE | ASSIST | READ;
	public static final int FACULTY_ASSIST = CREATE | MANAGE_NEWS | MANAGE_SUBJECTS | MANAGE_PERIODS | MANAGE_ENROLLMENTS | FACULTY_TUTOR;
	public static final int FACULTY_ADMINISTRATION = GRANT | UPDATE | DELETE | FACULTY_ASSIST;
	public static final int FACULTY_OWN = DELETE | FACULTY_ADMINISTRATION;
	
	// Combinations of base permissions we permit
	public static final int RU = READ | UPDATE;
	public static final int CRU = CREATE | RU;
	public static final int CRUD = CRU | DELETE;
	public static final int GCRUD = GRANT | CRUD;
	public static final int OGCRUD = OWN | GCRUD;
	
	private static final int[] validPermissions = {
		GRANT, CREATE, READ, UPDATE, DELETE, ASSIST, PARTICIPATE, 
		RU, CRU, CRUD, GCRUD, OGCRUD,
		FACULTY_TUTOR, FACULTY_ASSIST, FACULTY_ADMINISTRATION, FACULTY_OWN
	};
	

	@Override
	public int[] getValidPermissions() {
		return validPermissions;
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
		sb.append(((mask & MANAGE_SUBJECTS) == MANAGE_SUBJECTS) ? "MS" : "--");
		sb.append(((mask & MANAGE_PERIODS) == MANAGE_PERIODS) ? "MP" : "--");
		sb.append(((mask & MANAGE_ENROLLMENTS) == MANAGE_ENROLLMENTS) ? "ME" : "--");
		sb.append(((mask & MANAGE_NEWS) == MANAGE_NEWS) ? "MN" : "--");
		sb.append("|");
		sb.append(((mask & PARTICIPATE) == PARTICIPATE) ? "P" : "-");
		sb.append(((mask & ASSIST) == ASSIST) ? "A" : "-");
		return sb.toString();
	}

	@Override
    protected boolean isPermitted(int maskToCheck, int permissionToCheck) {
		if (logger.isDebugEnabled()) {
			logger.debug("mask = "+printPermissionsBlock(maskToCheck)+ " permission = " +printPermissionsBlock(permissionToCheck));
			logger.debug("mask = "+Integer.toBinaryString(maskToCheck)+ " permission = "+Integer.toBinaryString(permissionToCheck));
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

	public int getFACULTY_ADMINISTRATION() {
		return FACULTY_ADMINISTRATION;
	}

	public int getFACULTY_ASSIST() {
		return FACULTY_ASSIST;
	}

	public int getFACULTY_OWN() {
		return FACULTY_OWN;
	}

	public int getFACULTY_TUTOR() {
		return FACULTY_TUTOR;
	}

	public int getGCRUD() {
		return GCRUD;
	}

	public int getGRANT() {
		return GRANT;
	}

	public int getMANAGE_ENROLLMENTS() {
		return MANAGE_ENROLLMENTS;
	}

	public int getMANAGE_NEWS() {
		return MANAGE_NEWS;
	}

	public int getMANAGE_PERIODS() {
		return MANAGE_PERIODS;
	}

	public int getMANAGE_SUBJECTS() {
		return MANAGE_SUBJECTS;
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
	
	
	
}
