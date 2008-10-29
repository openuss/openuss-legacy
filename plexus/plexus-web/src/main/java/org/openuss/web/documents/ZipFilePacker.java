package org.openuss.web.documents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.openuss.documents.FileInfo;
import org.openuss.repository.RepositoryService;

/**
 * ZipFilePacker 
 * 
 * @author Ingo Dueppe
 */
public class ZipFilePacker {

	private static final Logger logger = Logger.getLogger(ZipFilePacker.class);
	
	protected List<FileInfo> files;
	protected RepositoryService repositoryService;
	
	public ZipFilePacker(List<FileInfo> files, RepositoryService repositoryService) {
		this.files = files;
		this.repositoryService = repositoryService;
	}

	public void writeZip(OutputStream outputStream) throws IOException {
		InputStream is = zippedFileContent();
		IOUtils.copyLarge(is, outputStream);
		outputStream.flush();
		IOUtils.closeQuietly(outputStream);
		IOUtils.closeQuietly(is);
		outputStream.close();
	}
	
	private InputStream zippedFileContent() {
		try {
			final PipedInputStream pis = new PipedInputStream();
			final PipedOutputStream pot = new PipedOutputStream(pis);
			
			final SecurityContext securityContext = SecurityContextHolder.getContext();
			
			new Thread(new Runnable() {
				public void run() {
					SecurityContextHolder.setContext(securityContext);
					ZipOutputStream zos = null;
					InputStream is = null;
					boolean empty = true;
					try {
						zos = new ZipOutputStream(pot);
						zos.setEncoding("cp850");
						for (FileInfo file : files) {
							ZipEntry zipEntry = new ZipEntry(file.getAbsoluteName());
							zipEntry.setComment(file.getDescription());
							zipEntry.setTime(file.getModified().getTime());
							zos.putNextEntry(zipEntry);
							logger.debug("obtaining input stream for "+file.getName());
							is = repositoryService.loadContent(file.getId());
							IOUtils.copyLarge(is, zos);
							is.close();
							zos.closeEntry();
							empty = false;
							zos.flush();
						}
						if (empty) {
							zos.putNextEntry(new ZipEntry("readme.txt"));
							zos.write("No contents available".getBytes());
							zos.closeEntry();
						}
						zos.close();
					} catch (Exception e) {
						logger.error("Can't pipe output to input", e);
						try {
							if (is != null) {
								is.close();
							}
							if (zos != null) {
								zos.close();
							}
							if (pis != null) {
								pis.close();
							}
						} catch (IOException e1) {
							logger.error("Can't pipe output to input", e1);
						}
					}
				}
			}).start();
			return pis;
		} catch (Exception e) {
			logger.error("Can't create input-stream", e);
			return new ByteArrayInputStream(new byte[0]);
		}
	}
}
