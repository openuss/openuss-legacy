package org.openuss.framework.tools.db;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.firebirdsql.management.FBManager;

/**
 * 	Dropes and creates a Firebird database. 
 * 
 * @version 1.0
 * @author Ron Haus
 */
public class DatabaseSetupTask extends Task {

	private String DB_SERVER_URL = "";

	private int DB_SERVER_PORT = 0;

	private String DB_NAME = "";

	private String DB_USER = "";

	private String DB_PASSWORD = "";

	public void setDB_NAME(String db_name) {
		DB_NAME = db_name;
	}

	public void setDB_PASSWORD(String db_password) {
		DB_PASSWORD = db_password;
	}

	public void setDB_SERVER_PORT(int db_server_port) {
		DB_SERVER_PORT = db_server_port;
	}

	public void setDB_SERVER_URL(String db_server_url) {
		DB_SERVER_URL = db_server_url;
	}

	public void setDB_USER(String db_user) {
		DB_USER = db_user;
	}

	public void execute() throws BuildException {
		
		if (DB_SERVER_URL == "") {
			throw new BuildException("ERROR: The property DB_SERVER_URL is undefined.");
		}
		if (DB_SERVER_PORT == 0) {
			throw new BuildException("ERROR: The property DB_SERVER_PORT is undefined.");
		}
		if (DB_NAME == "") {
			throw new BuildException("ERROR: The property DB_NAME is undefined.");
		}
		if (DB_USER == "") {
			throw new BuildException("ERROR: The property DB_USER is undefined.");
		}
		if (DB_PASSWORD == "") {
			throw new BuildException("ERROR: The property DB_PASSWORD is undefined.");
		}
		
		FBManager fbManager = new FBManager();
		fbManager.setForceCreate(true);
		fbManager.setServer(DB_SERVER_URL);
		fbManager.setPort(DB_SERVER_PORT);

		try {
			log("INFO: Starting Firebird Database Manager...");
			fbManager.start();
			log("INFO: Firebird Database Manager started");
			try {
				log("INFO: Droping database " + DB_NAME + "...");
				fbManager.dropDatabase(DB_NAME, DB_USER, DB_PASSWORD);
				log("INFO: Database dropped");
			} catch (Exception e) {
				log("INFO: Database " + DB_NAME + " couldn't be dropped. May have not existed.");
			}
			try {
				log("INFO: Creating database " + DB_NAME + "...");
				fbManager.createDatabase(DB_NAME, DB_USER, DB_PASSWORD);
				log("INFO: Database created");
			} catch (Exception e) {
				log("ERROR: Couldn't create new " + DB_NAME + " database.");
				throw new BuildException(e);
			}
		} catch (Exception e) {
			log("ERROR: Couldn't initialize Firebird Database Manager. No database was dropped or created.");
			throw new BuildException(e);
		} finally {
			try {
				fbManager.stop();
				log("INFO: Firebird Database Manager stopped");
			} catch (Exception e) {
				;
			}
		}
	}

}
