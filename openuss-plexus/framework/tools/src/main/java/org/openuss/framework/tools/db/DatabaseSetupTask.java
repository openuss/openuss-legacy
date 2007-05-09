package org.openuss.framework.tools.db;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.firebirdsql.management.FBManager;

/**
 * 
 * @author Ron Haus
 */
public class DatabaseSetupTask extends Task {

	public void execute() throws BuildException {

		String DB_SERVER_URL = "localhost";
		int DB_SERVER_PORT = 3050;
		String DB_NAME1 = "openuss30";
		String DB_NAME2 = "openuss30-test";
		String DB_USER = "sysdba";
		String DB_PASSWORD = "masterkey";
		
		FBManager fbManager = new FBManager();
		fbManager.setForceCreate(true);
		fbManager.setServer(DB_SERVER_URL);
		fbManager.setPort(DB_SERVER_PORT);
		
		try {
			System.out.println("INFO: Starting Firebird Database Manager...");
			fbManager.start();
			System.out.println("INFO: Firebird Database Manager started");
			try {
				System.out.println("INFO: Droping database "+DB_NAME1+"...");
				fbManager.dropDatabase(DB_NAME1, DB_USER, DB_PASSWORD);
				System.out.println("INFO: Database dropped");
			} catch(Exception e) {
				System.out.println("INFO: Database "+DB_NAME1+" couldn't be dropped. May have not existed.");
			}
			try {
				System.out.println("INFO: Droping database "+DB_NAME2+"...");
				fbManager.dropDatabase(DB_NAME2, DB_USER, DB_PASSWORD);
				System.out.println("INFO: Database dropped");
			} catch(Exception e) {
				System.out.println("INFO: Database "+DB_NAME2+" couldn't be dropped. May have not existed.");
			}
			try {
				System.out.println("INFO: Creating database "+DB_NAME1+"...");
				fbManager.createDatabase(DB_NAME1, DB_USER, DB_PASSWORD);
				System.out.println("INFO: Database created");
			} catch(Exception e) {
				System.out.println("ERROR: Couldn't create new "+DB_NAME1+" database.");
				//throw new BuildException(e);
			}
			try {
				System.out.println("INFO: Creating database "+DB_NAME2+"...");
				fbManager.createDatabase(DB_NAME2, DB_USER, DB_PASSWORD);
				System.out.println("INFO: Database created");
			} catch(Exception e) {
				System.out.println("ERROR: Couldn't create new "+DB_NAME2+" database.");
				//throw new BuildException(e);
			}
		} catch(Exception e) {
			System.out.println("ERROR: Couldn't initialize Firebird Database Manager. No database was dropped or created.");
			//throw new BuildException(e);
		} finally {
			try {
				fbManager.stop();
				System.out.println("INFO: Firebird Database Manager stopped");
			} catch(Exception e) {;}
		}
	}
}
