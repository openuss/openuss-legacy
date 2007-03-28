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

import org.apache.commons.lang.Validate;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.security.User;

/**
 * @see org.openuss.braincontest.BrainContestService
 */
public class BrainContestServiceImpl extends org.openuss.braincontest.BrainContestServiceBase {

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContests(java.lang.Object)
	 */
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
			while (i.hasNext()) {
				bciList.add(brainContest2BrainContestInfo((BrainContest) i.next()));
			}
		} else if (contests == null) {
			bciList = new ArrayList<BrainContestInfo>();
		}
		return bciList;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected org.openuss.braincontest.BrainContestInfo handleGetContest(
			org.openuss.braincontest.BrainContestInfo contest) throws java.lang.Exception {
		return privGetContest(contest);

	}

	private org.openuss.braincontest.BrainContestInfo privGetContest(BrainContestInfo contest) {
		Validate.notNull(contest.getId());
		BrainContest brainContest = getBrainContestDao().load(contest.getId());
		BrainContestInfo bci = brainContest2BrainContestInfo(brainContest);
		return bci;
	}

	private BrainContestInfo brainContest2BrainContestInfo(BrainContest brainContest) {
		BrainContestInfo bci = new BrainContestInfo();
		bci.setDescription(brainContest.getDescription());
		bci.setDomainIdentifier(brainContest.getDomainIdentifier());
		bci.setId(brainContest.getId());
		bci.setReleaseDate(brainContest.getReleaseDate());
		bci.setSolution(brainContest.getSolution());
		bci.setTitle(brainContest.getTitle());
		bci.setTries(brainContest.getTries());
		bci.setAnswers(brainContest.getAnswersCount());
		return bci;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#createContest(org.openuss.braincontest.BrainContestInfo)
	 */
	protected void handleCreateContest(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest.getTitle(), "contest title must not be null");
		Validate.notNull(contest.getDescription(), "contest descruption must not be null");
		Validate.notNull(contest.getReleaseDate(), "contest release date must not be null");
		Validate.notNull(contest.getSolution(), "contest solution must not be null");
		Validate.notNull(contest.getDomainIdentifier(), "contest domain identifier must not bei null");

		BrainContest brainContest = new BrainContestImpl();
		brainContest.setDomainIdentifier(contest.getDomainIdentifier());
		brainContest.setDescription(contest.getDescription());
		brainContest.setTitle(contest.getTitle());
		brainContest.setReleaseDate(contest.getReleaseDate());
		brainContest.setSolution(contest.getSolution());
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
		Validate.notNull(contest.getDescription(), "contest descruption must not be null");
		Validate.notNull(contest.getReleaseDate(), "contest release date must not be null");
		Validate.notNull(contest.getSolution(), "contest solution must not be null");
		Validate.notNull(contest.getDomainIdentifier(), "contest domain identifier must not bei null");

		BrainContest brainContest = getBrainContestDao().load(contest.getId());
		brainContest.setId(contest.getId());
		brainContest.setDescription(contest.getDescription());
		brainContest.setDomainIdentifier(contest.getDomainIdentifier());
		brainContest.setReleaseDate(contest.getReleaseDate());
		brainContest.setSolution(contest.getSolution());
		brainContest.setTitle(contest.getTitle());
		brainContest.setTries(contest.getTries());
		getBrainContestDao().update(brainContest);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#getAttachments(org.openuss.braincontest.BrainContestInfo)
	 */
	protected java.util.List handleGetAttachments(BrainContestInfo contest)	throws Exception {
		try {
			Validate.notNull(contest, "BrainContest must not be null");
		} catch (IllegalArgumentException e) {
			throw new BrainContestApplicationException(e.getMessage());
		}
		List<FileInfo> files = getDocumentService().getFileEntries(contest);
		return files;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#addAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleAddAttachment(BrainContestInfo contest, FileInfo fileInfo) throws java.lang.Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");
		FolderInfo parent = getDocumentService().getFolder(contest);
		getDocumentService().createFileEntry(fileInfo, parent);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#removeAttachment(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.documents.FileInfo)
	 */
	protected void handleRemoveAttachment(BrainContestInfo contest, FileInfo fileInfo) throws java.lang.Exception {
		Validate.notNull(contest, "BrainContest must not be null");
		Validate.notNull(fileInfo, "fileInfo must not be null");
		
		getDocumentService().removeFolderEntry(fileInfo.getId());
	}

	/**
	 * @see org.openuss.braincontest.BrainContestService#answer(java.lang.String,
	 *      org.openuss.security.User,
	 *      org.openuss.braincontest.BrainContestInfo, boolean)
	 */
	protected boolean handleAnswer(String answer, User user, BrainContestInfo contest, boolean topList)
			throws Exception {
		Validate.notNull(answer, "Answer must not be null");
		Validate.notNull(user, "User must not be null");
		Validate.notNull(contest, "Contest must not be null");

		BrainContest bc = getBrainContestDao().load(contest.getId());
		bc.setTries(bc.getTries() + 1);
		contest.setTries(contest.getTries() + 1);
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
		ai.setSolverName(answer.getSolver().getFirstName() + " " + answer.getSolver().getLastName() + " ("
				+ answer.getSolver().getUsername() + ")");
		return ai;
	}

	@Override
	protected void handleRemoveContest(BrainContestInfo contest) throws Exception {
		Validate.notNull(contest, "Contest must not be null");

		BrainContest bc = getBrainContestDao().load(contest.getId());
		getBrainContestDao().remove(bc);
	}

}