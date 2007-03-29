// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.Validate;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.framework.web.jsf.util.AcegiUtils;

/**
 * @see org.openuss.braincontest.BrainContestService
 */
public class BrainContestServiceImpl extends
		org.openuss.braincontest.BrainContestServiceBase {

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContests(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	protected java.util.List handleGetContests(java.lang.Object domainObject) throws java.lang.Exception {
		Validate.notNull(domainObject, "domainObject must not be null");

		Long domainIdentifier = DomainObjectUtility.identifierFromObject(domainObject);
		if (domainIdentifier == null) {
			throw new BrainContestApplicationException("no_domain_object_identifier_found");
		}

		List<BrainContest> contests = getBrainContestDao().findByDomainObject(domainIdentifier);
		Iterator i = contests.iterator();
		List<BrainContestInfo> bciList = new ArrayList<BrainContestInfo>();
		if (contests != null) {
			verifiyPermissionsOnEntries(domainObject, contests);
			getBrainContestDao().toBrainContestInfoCollection(contests);
			return contests;
		} else if (contests == null) {
			bciList = new ArrayList<BrainContestInfo>();
		}
		return bciList;
	}

	private void verifiyPermissionsOnEntries(java.lang.Object domainObject, List<BrainContest> contests) {
		if (!AcegiUtils.hasPermission(domainObject, new Integer[]{ LectureAclEntry.ASSIST})) {
			CollectionUtils.filter(contests, new Predicate() {
				public boolean evaluate(Object object) {
					return ((BrainContest)object).isReleased();
				}});
		}
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected org.openuss.braincontest.BrainContestInfo handleGetContest(
			org.openuss.braincontest.BrainContestInfo contest)
			throws java.lang.Exception {
		return privGetContest(contest);

	}

	private org.openuss.braincontest.BrainContestInfo privGetContest(
			BrainContestInfo contest) {
		Validate.notNull(contest.getId());
		BrainContest brainContest = getBrainContestDao().load(contest.getId());
		BrainContestInfo bci = getBrainContestDao().toBrainContestInfo(
				brainContest);
		return bci;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#createContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected void handleCreateContest(BrainContestInfo contest)
			throws Exception {
		Validate.notNull(contest.getTitle(), "contest title must not be null");
		Validate.notNull(contest.getDescription(),
				"contest descruption must not be null");
		Validate.notNull(contest.getReleaseDate(),
				"contest release date must not be null");
		Validate.notNull(contest.getSolution(),
				"contest solution must not be null");
		Validate.notNull(contest.getDomainIdentifier(),
				"contest domain identifier must not bei null");
		
		BrainContest brainContest = getBrainContestDao().brainContestInfoToEntity(contest);
		getBrainContestDao().create(brainContest);

		contest.setId(brainContest.getId());
		contest.setTries(brainContest.getTries());

	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#saveContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected void handleSaveContest(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest.getId(), "id must not be null");
		Validate.notNull(contest.getTitle(), "contest title must not be null");
		Validate.notNull(contest.getDescription(),
				"contest descruption must not be null");
		Validate.notNull(contest.getReleaseDate(),
				"contest release date must not be null");
		Validate.notNull(contest.getSolution(),
				"contest solution must not be null");
		Validate.notNull(contest.getDomainIdentifier(),
				"contest domain identifier must not bei null");

		BrainContest brainContest = getBrainContestDao().brainContestInfoToEntity(contest);
		getBrainContestDao().update(brainContest);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getAttachments(org.openuss.braincontest.BrainContestInfo)
	 */
	@SuppressWarnings("unchecked")
	protected java.util.List handleGetAttachments(BrainContestInfo contest)
			throws Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(contest.getId());
		List<FileInfo> files = getDocumentService().getFileEntries(contest);
		return files;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#addAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleAddAttachment(BrainContestInfo contest,
			FileInfo fileInfo) throws java.lang.Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");
		FolderInfo parent = getDocumentService().getFolder(contest);
		getDocumentService().createFileEntry(fileInfo, parent);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#removeAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleRemoveAttachment(BrainContestInfo contest,
			FileInfo fileInfo) throws java.lang.Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");

		getDocumentService().removeFolderEntry(fileInfo.getId());
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#answer(java.lang.String,
	 *      org.openuss.security.User,
	 *      org.openuss.braincontest.BrainContestInfo, boolean)
	 */
	protected boolean handleAnswer(String answer, User user,
			BrainContestInfo contest, boolean topList) throws Exception {
		Validate.notNull(answer, "Answer must not be null");
		Validate.notNull(user, "User must not be null");
		Validate.notNull(contest, "Contest must not be null");

		BrainContest bc = getBrainContestDao().load(contest.getId());
		
		List checkIfAnswered = getAnswerDao().findByContestAndSolver(user, bc);
		if (checkIfAnswered.size()>0) throw new BrainContestApplicationException("braincontest_message_user_correct_answer");
		contest.setTries(bc.getTries() + 1);
		bc.setTries(bc.getTries() + 1);
		if (!bc.getSolution().equals(answer)) {
			getBrainContestDao().update(bc);
			return false;
		}
		if (!topList) {
			getBrainContestDao().update(bc);
			return true;
		}
		Answer answerObject = Answer.Factory.newInstance();
		answerObject.setAnsweredAt(new Date(System.currentTimeMillis()));
		answerObject.setContest(bc);
		answerObject.setSolver(user);
		bc.addAnswer(answerObject);
		getBrainContestDao().update(bc);
		return true;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getAnswers(org.openuss.braincontest.BrainContestInfo)
	 */
	protected List handleGetAnswers(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest, "Contest must not be null");

		BrainContest bc = getBrainContestDao().load(contest.getId());
		List<AnswerInfo> answerInfoList = new ArrayList<AnswerInfo>();
		Collection<Answer> answerCollection = bc.getAnswers();
		Iterator i = answerCollection.iterator();
		while (i.hasNext()) {
			answerInfoList.add(answer2AnswerInfo((Answer) i.next()));
		}
		if (answerInfoList.size() == 0)
			return null;
		return answerInfoList;
	}

	private AnswerInfo answer2AnswerInfo(Answer answer) {
		AnswerInfo ai = new AnswerInfo();
		ai.setAnsweredAt(answer.getAnsweredAt());
		ai.setImageId(answer.getSolver().getImageId());
		ai.setSolverName(answer.getSolver().getFirstName() + " "
				+ answer.getSolver().getLastName() + " ("
				+ answer.getSolver().getUsername() + ")");
		return ai;
	}

	@Override
	protected void handleRemoveContest(BrainContestInfo contest)
			throws Exception {
		Validate.notNull(contest, "Contest must not be null");

		BrainContest bc = getBrainContestDao().load(contest.getId());
		getBrainContestDao().remove(bc);
	}

}