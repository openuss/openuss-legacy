package org.openuss.search;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.openuss.lecture.Faculty;
import org.springmodules.lucene.index.core.DocumentCreator;
import org.springmodules.lucene.index.core.DocumentModifier;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public class LectureIndexerImpl extends LuceneIndexSupport implements LectureIndexer {

	/**
	 * {@inheritDoc}
	 */
	public void addFaculty(final Faculty faculty) {
		getLuceneIndexTemplate().addDocument(new DocumentCreator() {
			public Document createDocument() throws Exception {
				Document document = new Document();
				setFields(faculty, document);
				return document;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateFaculty(final Faculty faculty) {
		Term facultyTerm = new Term(IDENTIFIER, String.valueOf(faculty.getId()));
		getLuceneIndexTemplate().updateDocument(facultyTerm, new DocumentModifier() {
			public Document updateDocument(Document document) throws Exception {
				Document newDocument = new Document();
				setFields(faculty, document);
				return newDocument;
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteFaculty(final Faculty faculty) {
		Term term = new Term(IDENTIFIER, String.valueOf(faculty.getId()));
		getLuceneIndexTemplate().deleteDocuments(term);
	}

	private void setFields(final Faculty faculty, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(faculty.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, "FACULTY", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, getFacultyContent(faculty), Field.Store.YES, Field.Index.TOKENIZED));
	}

	private String getFacultyContent(final Faculty faculty) {
		StringBuilder content = new StringBuilder();
		content.append(faculty.getId());
		content.append(" |+| ");
		content.append(faculty.getName());
		content.append(" |+| ");
		content.append(faculty.getDescription());
		content.append(" |+| ");
		content.append(faculty.getOwnername());
		content.append(" |+| ");
		content.append(faculty.getAddress());
		content.append(" |+| ");
		content.append(faculty.getEmail());
		content.append(" |+| ");
		content.append(faculty.getCity());
		content.append(" |+| ");
		content.append(faculty.getPostcode());
		content.append(" |+| ");
		content.append(faculty.getTelephone());
		content.append(" |+| ");
		content.append(faculty.getTelefax());
		content.append(" |+| ");
		content.append(faculty.getWebsite());
		return content.toString();
	}

}
