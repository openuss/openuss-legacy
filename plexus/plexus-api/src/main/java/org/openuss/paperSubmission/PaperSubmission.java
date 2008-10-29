package org.openuss.paperSubmission;

import java.util.Date;

/**
 * A PaperSubmission symbolizes a submission by a user of one or many files. It
 * is attached to a courseMember and is for a exam.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface PaperSubmission extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public Date getDeliverDate();

	public void setDeliverDate(Date deliverDate);

	/**
     * 
     */
	public String getComment();

	public void setComment(String comment);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.paperSubmission.Exam getExam();

	public void setExam(org.openuss.paperSubmission.Exam exam);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.User getSender();

	public void setSender(org.openuss.security.User sender);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.paperSubmission.PaperSubmission}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static PaperSubmission.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static PaperSubmission.Factory getFactory() {
			if (factory == null) {
				factory = (PaperSubmission.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.paperSubmission.PaperSubmission.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract PaperSubmission createPaperSubmission();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.paperSubmission.PaperSubmission}.
		 */
		public static org.openuss.paperSubmission.PaperSubmission newInstance() {
			return getFactory().createPaperSubmission();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.paperSubmission.PaperSubmission}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.paperSubmission.PaperSubmission newInstanceByIdentifier(Long id) {
			final org.openuss.paperSubmission.PaperSubmission entity = getFactory().createPaperSubmission();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.paperSubmission.PaperSubmission}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.paperSubmission.PaperSubmission newInstance(Date deliverDate) {
			final org.openuss.paperSubmission.PaperSubmission entity = getFactory().createPaperSubmission();
			entity.setDeliverDate(deliverDate);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.paperSubmission.PaperSubmission}, taking all
		 * possible properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.paperSubmission.PaperSubmission newInstance(Date deliverDate, String comment,
				org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender) {
			final org.openuss.paperSubmission.PaperSubmission entity = getFactory().createPaperSubmission();
			entity.setDeliverDate(deliverDate);
			entity.setComment(comment);
			entity.setExam(exam);
			entity.setSender(sender);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}