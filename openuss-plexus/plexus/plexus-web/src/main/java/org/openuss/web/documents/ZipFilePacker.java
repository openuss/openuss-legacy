package org.openuss.web.documents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.openuss.documents.FileInfo;

/**
 * ZipFilePacker pa
 * 
 * @author Ingo Dueppe
 */
public class ZipFilePacker {

	private static final Logger logger = Logger.getLogger(ZipFilePacker.class);

	private static final int DRAIN_BUFFER_SIZE = 1024;

	private List<FileInfo> files;
	
	public ZipFilePacker(List<FileInfo> files) {
		this.files = files;
	}

	public void writeZip(OutputStream outputStream) throws IOException {
		InputStream is = zippedFileContent();
		drain(is, outputStream);
		outputStream.flush();
		outputStream.close();
		is.close();
	}
	
	private InputStream zippedFileContent() {
		try {
			final PipedInputStream pis = new PipedInputStream();
			final PipedOutputStream pot = new PipedOutputStream(pis);

			new Thread(new Runnable() {
				public void run() {
					ZipOutputStream zos = null;
					InputStream fis = null;
					boolean empty = true;
					try {
						zos = new ZipOutputStream(pot);
						for (FileInfo file : files) {
							ZipEntry zipEntry = new ZipEntry(file.getAbsoluteName());
							zipEntry.setComment(file.getDescription());
							zipEntry.setTime(file.getModified().getTime());
							zos.putNextEntry(zipEntry);
							fis = file.getInputStream();
							drain(fis, zos);
							fis.close();
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
							if (fis != null) {
								fis.close();
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

	private void drain(InputStream input, OutputStream output) throws IOException {
		int bytesRead = 0;
		byte[] buffer = new byte[DRAIN_BUFFER_SIZE];

		while ((bytesRead = input.read(buffer, 0, DRAIN_BUFFER_SIZE)) != -1) {
			output.write(buffer, 0, bytesRead);
			output.flush();
		}
	}

}
