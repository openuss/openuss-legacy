package org.openuss.docmanagement;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.apache.jackrabbit.core.nodetype.compact.ParseException;
import org.apache.log4j.Logger;
import org.apache.jackrabbit.name.QName;

import org.openuss.docmanagement.RepositoryAccess;
import java.io.File;

public class RepositoryStartup {

	public Repository repository;

	public static final Logger logger = Logger
			.getLogger(RepositoryStartup.class);

	public void init() {
		logger
				.debug("RepositoryStartup initialized, Registering namespace and nodetypes!");
		try {
			Session session = repository.login(new SimpleCredentials(
					RepositoryAccess.USERNAME, RepositoryAccess.PASSWORD
							.toCharArray()));
			NamespaceRegistry nsr = session.getWorkspace()
					.getNamespaceRegistry();
			try{
				nsr.registerNamespace(DocConstants.NAMESPACE_PREFIX,
					DocConstants.NAMESPACE_URI);
			} catch (NamespaceException e){
				//do nothing
			}
			NodeTypeManagerImpl ntm = (NodeTypeManagerImpl) session
					.getWorkspace().getNodeTypeManager();
		
			//TODO find out how to use pathvariables
			FileReader fileReader = new FileReader("ntd.cnd");

			// Create a CompactNodeTypeDefReader
			CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(
					fileReader, "ntd.cnd");

			// Get the List of NodeTypeDef objects
			List ntdList = cndReader.getNodeTypeDefs();

			// Acquire the NodeTypeRegistry
			NodeTypeRegistry ntreg = ntm.getNodeTypeRegistry();

			// Loop through the prepared NodeTypeDefs
			for (Iterator i = ntdList.iterator(); i.hasNext();) {

				// Get the NodeTypeDef...
				NodeTypeDef ntd = (NodeTypeDef) i.next();

				// ...and register it
				ntreg.registerNodeType(ntd);
			}
			// ntr.registerNodeType(arg0)
		} catch (RepositoryException e) {
			logger.error(e);
		} catch (InvalidNodeTypeDefException e) {
			logger.error(e);
		} catch (FileNotFoundException e) {
			logger.error(e);			
		} catch (ParseException e) {
			logger.error("ParseError: ", e);
		}
		logger.debug("RepositoryStartup finished");
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}