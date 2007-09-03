package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.documents.FileInfo;
import org.openuss.migration.legacy.domain.Quiz2;

/**
 * Course Quiz Import
 * 
 * @author Ingo Dueppe
 */
public class QuizImport extends DefaultImport {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(QuizImport.class);

	/** BrainContestService */
	private BrainContestService brainContestService;

	public void perform() {
		ScrollableResults results = legacyDao.loadAllQuiz();

		Quiz2 quiz = null;

		while (results.next()) {
			evict(quiz);
			quiz = (Quiz2) results.get()[0];

			logger.debug("Import quiz " + quiz.getTitle());

			Long courseId = identifierDao.getId(quiz.getEnrollmentpk());

			if (courseId != null) {
				BrainContestInfo contest = new BrainContestInfo();
				contest.setDomainIdentifier(courseId);
				contest.setTitle(quiz.getTitle());
				contest.setDescription("...");
				contest.setReleaseDate(quiz.getDdate());
				contest.setSolution(quiz.getAnswer());

				FileInfo fileInfo = new FileInfo();

				if (StringUtils.isNotBlank(quiz.getQuizfilepk())) {
					fileInfo.setContentType("application/octet-stream");
					fileInfo.setFileName(quiz.getQuizfilename());
					byte[] data = legacyDao.loadQuizFileData(quiz.getQuizfilepk());
					fileInfo.setFileSize(data.length);
					fileInfo.setInputStream(new ByteArrayInputStream(data));
					fileInfo.setCreated(quiz.getDdate());
					List<FileInfo> attachments = new ArrayList<FileInfo>();
					attachments.add(fileInfo);
					contest.setAttachments(attachments);
				}
				try {
					brainContestService.createContest(contest);
				} catch (BrainContestApplicationException e) {
					logger.error(e);
				} finally {
					IOUtils.closeQuietly(fileInfo.getInputStream());
				}
			}
		}
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

}
