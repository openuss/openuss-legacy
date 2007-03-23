package org.openuss.docmanagement;

public class DocConstants{
	
	public static final String DISTRIBUTION = "distribution";
	
	public static final String EXAMAREA = "examarea";
	
	public static final String WORKINGPLACE = "workingplace";
	
	//NamespacePrefix
	public static final String NAMESPACE_PREFIX = "doc";
	public static final String NAMESPACE_URI = "http://www.openuss.org/doc";
	
	//Resource
	public static final String PROPERTY_VISIBILITY = "doc:visibility";
	public static final String PROPERTY_MESSAGE = "doc:message";
	
	//Folder
	public static final String PROPERTY_DEADLINE = "doc:deadline";
	
	//File 													
	public static final String PROPERTY_DISTRIBUTIONTIME = "doc:distributionTime";	
	public static final String PROPERTY_LENGTH = "doc:length"; 	
	public static final String PROPERTY_OWNER = "doc:owner"; 
	public static final String PROPERTY_VIEWED = "doc:viewed"; 
	
	//Link
	public static final String PROPERTY_REFERENCE = "doc:reference";
	
	public static final String DOC_FILE = "doc:file";
	public static final String DOC_FOLDER = "doc:folder";
	public static final String NT_RESOURCE = "nt:resource";
	public static final String DOC_LINK = "doc:link";	
	public static final String JCR_CONTENT = "jcr:content";
	public static final String JCR_CREATED = "jcr:created";
	public static final String JCR_ENCODING = "jcr:encoding";
	public static final String JCR_MIMETYPE = "jcr:mimeType";
	public static final String JCR_DATA = "jcr:data";
	public static final String JCR_LASTMODIFIED = "jcr:lastModified";

	// navigation cases distribution	
	public static final String NEWDOCUMENTTOFOLDER = "docmanagement_newdocumenttofolder";
	public static final String EDITFOLDER = "docmanagement_editfolder";
	public static final String DOCUMENTEXPLORER = "docmanagement_documentexplorer";
	public static final String DELETE = "docmanagement_delete";
	public static final String FACULTY_EXPLORER = "docmanagement_facultyexplorer";
	public static final String EDITFACULTYFOLDER = "docmanagement_editfacultyfolder";
	public static final String NEWFACULTYDOCUMENT = "docmanagement_newfacultydocument";
	public static final String FOLDERTOFOLDER = "docmanagement_foldertofolder";
	
	//navigation cases examarea
	public static final String EXAMEXPLORER = "enrollment_examarea";
	public static final String EXAMVERSIONEXPLORER = "docmanagement_examversionexplorer";
	public static final String EXAMNEWDOCUMENT = "docmanagement_examnewdocument";
	
	//navigation cases workingplace
	public static final String WPEXPLORER = "enrollment_workingplace";
	public static final String WPDELETEFILE = "docmanagement_wpdeletefile";
	public static final String WPDELETEFOLDER = "docmanagement_wpdeletefolder";
	public static final String WPNEWDOCUMENT = "docmanagement_wpnewdocument";
	public static final String WPNEWFOLDER = "docmanagement_wpnewfolder";
	public static final String WPVERSIONEXPLORER = "docmanagement_wpversionexplorer";
	//mimetype zip
	public static final String MIMETYPE_ZIP = "application/zip";
	
	//delete linked files?
	public static final String DELETE_LINKS = "delete";
	public static final String REPLACE_LINKS = "copy";
	
	//folder name of trash
	public static final String TRASH_NAME = "trash";
	
	//mixin
	public static final String MIX_VERSIONABLE = "mix:versionable";
}