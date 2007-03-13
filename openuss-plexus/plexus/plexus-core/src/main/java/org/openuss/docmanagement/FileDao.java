package org.openuss.docmanagement;

import javax.jcr.Node;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.ConstraintViolationException;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FileDao extends ResourceDao {

	private Repository repository;

	public static Logger logger = Logger.getLogger(FileDao.class);

	public File getFile(String path) throws Exception{
		Session session;
		FileImpl file;
		try {
			session = login(repository);
			Node node = session.getRootNode();
			if (!(path == ""))
				node = node.getNode(path);
			if (!node.isNodeType(DocConstants.NT_FILE))
				throw new Exception ("Not a file");
			file = new FileImpl(new Timestamp(node.getProperty(
					DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTime()
					.getTime()), node.getUUID(), new Timestamp(node.getNode(
					DocConstants.NT_RESOURCE).getProperty(
					DocConstants.JCR_LASTMODIFIED).getDate().getTime()
					.getTime()), 0, node.getProperty(
					DocConstants.PROPERTY_MESSAGE).getString(), node.getNode(
					DocConstants.NT_RESOURCE).getProperty(
					DocConstants.JCR_MIMETYPE).getString(), node.getName(),
					node.getPath(), null, 1, ((int) node.getProperty(
							DocConstants.PROPERTY_VISIBILITY).getLong()));
			logout(session);
			return file;
		} catch (LoginException e) {
			logger.error("Login Exception: ", e);	
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ", e);
		}
		return null;
	}

	public BigFile getFile(File file) {
		Session session;
		try {
			session = login(repository);
			Node node = session.getNodeByUUID(file.getId());
			FileImpl pred = new FileImpl();

			BigFileImpl fi = new BigFileImpl(new Timestamp(node.getProperty(
					DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTime()
					.getTime()), node.getUUID(), new Timestamp(node.getNode(
					DocConstants.NT_RESOURCE).getProperty(
					DocConstants.JCR_LASTMODIFIED).getDate().getTime()
					.getTime()), 0, node.getProperty(
					DocConstants.PROPERTY_MESSAGE).getString(), node.getNode(
					DocConstants.NT_RESOURCE).getProperty(
					DocConstants.JCR_MIMETYPE).getString(), node.getName(),
					node.getPath(), pred, 1, ((int) node.getProperty(
							DocConstants.PROPERTY_VISIBILITY).getLong()), node
							.getNode(DocConstants.NT_RESOURCE).getNode(
									DocConstants.JCR_CONTENT).getProperty(
									DocConstants.JCR_DATA).getStream());
			logout(session);
			return fi;
		} catch (ConstraintViolationException e) {
			logger.error("ConstraintViolationException: ", e);
		} catch (LoginException e) {
			// should never happen (repository access through master account)
			logger.error("Login error in FileDao", e);
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ", e);
		}
		return null;
	}

	public void setFile(BigFile file) throws Exception {
		try {
			Session session = login(repository);
			Node node = session.getRootNode().getNode(file.getPath());
			String areaType = getAreaType(node);
			if (areaType == DocConstants.DISTRIBUTION) {
				setDistributionFile(node, file);
			}
			if (areaType == DocConstants.EXAMAREA) {
			}
			if (areaType == DocConstants.WORKINGPLACE) {
			}

			logout(session);
		} catch (LoginException e) {
			// should never happen (repository access through master account)
			logger.error("Login error in FileDao", e);
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ", e);
		}
	}

	private void setDistributionFile(Node node, BigFile file) throws Exception {
		try {
			Node test = node.getNode(file.getName());
			// only reached if Node with same name exists
			// TODO change to self-written exception
			throw new Exception("File already exists");
		} catch (PathNotFoundException e) {
			// should occur!
			// TODO use DocConstants

			// nt:File Knoten
			node.addNode(file.getName(), DocConstants.NT_FILE);
			node = node.getNode(file.getName());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(file.getDistributionTime().getTime());
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, file
					.getVisibility());
			node.addMixin(DocConstants.MIX_REFERENCEABLE);

			// nt:resource Knoten, der die eigentlich Datei enthaelt
			node.addNode(DocConstants.JCR_CONTENT, DocConstants.NT_RESOURCE);
			node = node.getNode(DocConstants.JCR_CONTENT);
			node.addMixin(DocConstants.MIX_REFERENCEABLE); // perhaps not
															// needed
			node.setProperty(DocConstants.JCR_DATA, file.getFile());
			node.setProperty(DocConstants.JCR_MIMETYPE, file.getMimeType());
			c.setTimeInMillis(file.getLastModification().getTime());
			node.setProperty(DocConstants.JCR_LASTMODIFIED, c);

		} catch (RepositoryException e) {
			logger.error("RepositoryException: ", e);
		}
	}

	private String getAreaType(Node node) {
		// TODO implement me
		// possible methods: recursive, path analysing, ...?
		return DocConstants.DISTRIBUTION;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
