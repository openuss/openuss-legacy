package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Scoinfo2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 9102228672695744678L;
	private String id;
	private String type;
	private String iteminfoId;
	private String prerequites;
	private String masteryscore;
	private String scoid;
	private Integer sequence;
	private String parameterstring;
	private String launch;
	private String userscoinfobaseId;

	// Constructors

	/** default constructor */
	public Scoinfo2() {
	}

	/** minimal constructor */
	public Scoinfo2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Scoinfo2(String id, String type, String iteminfoId, String prerequites, String masteryscore, String scoid,
			Integer sequence, String parameterstring, String launch, String userscoinfobaseId) {
		this.id = id;
		this.type = type;
		this.iteminfoId = iteminfoId;
		this.prerequites = prerequites;
		this.masteryscore = masteryscore;
		this.scoid = scoid;
		this.sequence = sequence;
		this.parameterstring = parameterstring;
		this.launch = launch;
		this.userscoinfobaseId = userscoinfobaseId;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIteminfoId() {
		return this.iteminfoId;
	}

	public void setIteminfoId(String iteminfoId) {
		this.iteminfoId = iteminfoId;
	}

	public String getPrerequites() {
		return this.prerequites;
	}

	public void setPrerequites(String prerequites) {
		this.prerequites = prerequites;
	}

	public String getMasteryscore() {
		return this.masteryscore;
	}

	public void setMasteryscore(String masteryscore) {
		this.masteryscore = masteryscore;
	}

	public String getScoid() {
		return this.scoid;
	}

	public void setScoid(String scoid) {
		this.scoid = scoid;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getParameterstring() {
		return this.parameterstring;
	}

	public void setParameterstring(String parameterstring) {
		this.parameterstring = parameterstring;
	}

	public String getLaunch() {
		return this.launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getUserscoinfobaseId() {
		return this.userscoinfobaseId;
	}

	public void setUserscoinfobaseId(String userscoinfobaseId) {
		this.userscoinfobaseId = userscoinfobaseId;
	}

}
