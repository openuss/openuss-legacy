package org.openuss.migration.from20to30;

import java.util.HashMap;
import java.util.Map;

import org.openuss.security.acl.ObjectIdentity;


/**
 * Convenient methods for data import 
 * @author Ingo Dueppe
 */
public class ImportUtil {

	/**
	 * Transform character to boolean
	 * @param c - character
	 * @return true if character == '1'
	 */
	public static boolean toBoolean(Character c) {
		return c != null && c == '1';
	}
	
	public static ObjectIdentity createObjectIdentity(Long id, ObjectIdentity parent) {
		ObjectIdentity objId = ObjectIdentity.Factory.newInstance();
		objId.setId(id);
		objId.setParent(parent);
		return objId;
	}

	
	/**
	 * Recalculate hashcode values after entity got persitent.
	 * @param map
	 */
	public static void refresh(Map map) {
		Map x = new HashMap(map);
		map.clear();
		map.putAll(x);
	}

	public static String extension(String fileName) {
		if (fileName != null) {
			int index = fileName.lastIndexOf('.');
			return fileName.substring(index+1);
		} else {
			return "";
		}
	}
}
