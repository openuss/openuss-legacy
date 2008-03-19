// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @author ingo dueppe
 * @author sebastian roeken
 * @see org.openuss.braincontest.BrainContestService
 */
public class BrainContestServiceImpl extends BrainContestServiceBase {

	private static final Logger logger = Logger.getLogger(BrainContestServiceImpl.class);

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContests(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	protected List handleGetContests(DomainObject domainObject) throws java.lang.Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null");
		Validate.notNull(domainObject.getId(), "Parameter domainObject must provide an id");

		List<BrainContest> contests = getBrainContestDao().findByDomainObject(
				BrainContestDao.TRANSFORM_BRAINCONTESTINFO, domainObject.getId());

		if (contests == null) {
			return new ArrayList<BrainContestInfo>();
		} else {
			verifiyPermissionsOnEntries(domainObject, contests);
			return contests;
		}
	}

	private void verifiyPermissionsOnEntries(DomainObject domainObject, List<BrainContest> contests) {
		logger.debug("verify permissions on entries");
		if (!AcegiUtils.hasPermission(domainObject, new Integer[] { LectureAclEntry.ASSIST })) {
			CollectionUtils.filter(contests, new ReleasedPredicate());
		}
	}

	private static final class ReleasedPredicate implements Predicate {
		public boolean evaluate(Object object) {
			if (object instanceof BrainContestInfo) {
				return ((BrainContestInfo) object).isReleased();
			} else if (object instanceof BrainContest) {
				return ((BrainContest) object).isReleased();
			} else {
				return false;
			}
		}
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContest(org.openuss.braincontest.BrainContestInfo)
	 */
	@SuppressWarnings("unchecked")
	protected BrainContestInfo handleGetContest(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest.getId(), "Parameter contest must provide an identifier.");
		BrainContestInfo bci = (BrainContestInfo) getBrainContestDao().load(BrainContestDao.TRANSFORM_BRAINCONTESTINFO,
				contest.getId());
		if (bci!=null) {
			List<FileInfo> attachments = getAttachments(bci);
			bci.setAttachments(attachments);
		}
		return bci;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#createContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected void handleCreateContest(BrainContestInfo contest) throws Exception {
		validateBrainContestInfo(contest);
		Validate.isTrue(contest.getId() == null, "contest must not have a identifier, call save method instead");

		logger.debug("Creating new braincontest.");
		
		BrainContest brainContest = getBrainContestDao().brainContestInfoToEntity(contest);

		getBrainContestDao().create(brainContest);
		getSecurityService().createObjectIdentity(brainContest, contest.getDomainIdentifier());

		if (contest.getAttachments() != null && !contest.getAttachments().isEmpty()) {
			logger.debug("found "+contest.getAttachments().size()+" attachments.");
			FolderInfo folder = getDocumentService().getFolder(brainContest);
			for (FileInfo attachment : contest.getAttachments()) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}

		getBrainContestDao().toBrainContestInfo(brainContest, contest);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#saveContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected void handleSaveContest(BrainContestInfo contest) throws Exception {
		validateBrainContestInfo(contest);

		BrainContest brainContest = getBrainContestDao().brainContestInfoToEntity(contest);
		getBrainContestDao().update(brainContest);
		
		getDocumentService().diffSave(brainContest, contest.getAttachments());
		
		getBrainContestDao().toBrainContestInfo(brainContest, contest);
	}

	private void validateBrainContestInfo(BrainContestInfo contest) {
		Validate.notNull(contest, "contest must not be null");
		Validate.notNull(contest.getTitle(), "contest title must not be null");
		Validate.notNull(contest.getDescription(), "contest description must not be null");
		Validate.notNull(contest.getReleaseDate(), "contest release date must not be null");
		Validate.notNull(contest.getSolution(), "contest solution must not be null");
		Validate.notNull(contest.getDomainIdentifier(), "contest domain identifier must not bei null");
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getAttachments(org.openuss.braincontest.BrainContestInfo)
	 */
	@SuppressWarnings("unchecked")
	protected java.util.List handleGetAttachments(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest, "Parameter contest must not be null");
		Validate.notNull(contest.getId(), "Parameter contest must provide an identifier.");
		List<FileInfo> attachments = getDocumentService().getFileEntries(contest);
		return attachments;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#addAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleAddAttachment(BrainContestInfo contest, FileInfo fileInfo) throws Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");
		FolderInfo parent = getDocumentService().getFolder(contest);
		getDocumentService().createFileEntry(fileInfo, parent);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#removeAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleRemoveAttachment(BrainContestInfo contest, FileInfo fileInfo) throws Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");

		getDocumentService().removeFolderEntry(fileInfo.getId());
	}

	/**
	 * @throws BrainContestApplicationException 
	 * @see org.openuss.braincontest.BrainContestService#answer(java.lang.String,
	 *      org.openuss.security.User,
	 *      org.openuss.braincontest.BrainContestInfo, boolean)
	 */
	@SuppressWarnings("unchecked")
	protected boolean handleAnswer(String answer, UserInfo user, BrainContestInfo contest, boolean topList) throws BrainContestApplicationException {
		Validate.notNull(answer, "Answer must not be null");
		Validate.notNull(user, "User must not be null");
		Validate.notNull(contest, "Contest must not be null");

		BrainContest brainContest = getBrainContestDao().load(contest.getId());

		// TODO findByContestAndSolver should only return one instance
		// There fore the answer id is a composite id of contest and solver
		List<Answer> checkIfAnswered = getAnswerDao().findByContestAndSolver(getSecurityService().getUserObject(user), brainContest);
		if (!checkIfAnswered.isEmpty()) {
			throw new BrainContestApplicationException("braincontest_message_user_correct_answer");
		}

		boolean valid = brainContest.validateAnswer(answer);

		if (valid && topList) {
			Answer answerObject = Answer.Factory.newInstance();
			answerObject.setAnsweredAt(new Date(System.currentTimeMillis()));
			answerObject.setSolver(getSecurityService().getUserObject(user));
			brainContest.addAnswer(answerObject);
		}
		getBrainContestDao().update(brainContest);

		// refresh given object
		getBrainContestDao().toBrainContestInfo(brainContest, contest);
		return valid;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getAnswers(org.openuss.braincontest.BrainContestInfo)
	 */
	@SuppressWarnings("unchecked")
	protected List<AnswerInfo> handleGetAnswers(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest, "Contest must not be null");

		BrainContest brainContest = getBrainContestDao().load(contest.getId());

		List<?> answers = new ArrayList<Answer>(brainContest.getAnswers());
		getAnswerDao().toAnswerInfoCollection(answers);
		return (List<AnswerInfo>)answers;
	}

	@Override
	protected void handleRemoveContest(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest, "Contest must not be null");

		BrainContest brainContest = getBrainContestDao().load(contest.getId());
		getBrainContestDao().remove(brainContest);
		getSecurityService().removeObjectIdentity(contest);
	}

}