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

	private String dbServerUrl = "localhost";

	private int dbServerPort = 3050;

	private String dbName = "";

	private String dbUSer = "sysdba";

	private String dbPassword = "masterkey";

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setDbServerPort(int dbServerPort) {
		this.dbServerPort = dbServerPort;
	}

	public void setDbServerUrl(String dbServerUrl) {
		this.dbServerUrl = dbServerUrl;
	}

	public void setDbUSer(String dbUser) {
		this.dbUSer = dbUser;
	}

	public void execute() throws BuildException {
		
		if (dbServerUrl == null || dbServerUrl.trim().length() == 0) {
			throw new BuildException("ERROR: The property DB_SERVER_URL is undefined.");
		}
		if (dbServerPort == 0) {
			throw new BuildException("ERROR: The property DB_SERVER_PORT is undefined.");
		}
		if (dbName == null || dbName.trim().length() == 0) {
			throw new BuildException("ERROR: The property DB_NAME is undefined.");
		}
		if (dbUSer == null || dbName.trim().length() == 0) {
			throw new BuildException("ERROR: The property DB_USER is undefined.");
		}
		if (dbPassword == null || dbName.trim().length() == 0) {
			throw new BuildException("ERROR: The property DB_PASSWORD is undefined.");
		}
		
		FBManager fbManager = new FBManager();
		fbManager.setForceCreate(true);
		fbManager.setServer(dbServerUrl);
		fbManager.setPort(dbServerPort);

		try {
			log("INFO: Starting Firebird Database Manager...");
			fbManager.start();
			log("INFO: Firebird Database Manager started");
			try {
				log("INFO: Droping database " + dbName + "...");
				fbManager.dropDatabase(dbName, dbUSer, dbPassword);
				log("INFO: Database dropped");
			} catch (Exception e) {
				log("INFO: Database " + dbName + " couldn't be dropped. May have not existed.");
			}
			try {
				log("INFO: Creating database " + dbName + "...");
				fbManager.createDatabase(dbName, dbUSer, dbPassword);
				log("INFO: Database created");
			} catch (Exception e) {
				log("ERROR: Couldn't create new " + dbName + " database.");
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
