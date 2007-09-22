package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Statistic2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 4237617116340889128L;
	private String id;
	private String rulekey;
	private String bannerkey;
	private String clicked;
	private Date statistictime;

	// Constructors

	/** default constructor */
	public Statistic2() {
	}

	/** minimal constructor */
	public Statistic2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Statistic2(String id, String rulekey, String bannerkey, String clicked, Date statistictime) {
		this.id = id;
		this.rulekey = rulekey;
		this.bannerkey = bannerkey;
		this.clicked = clicked;
		this.statistictime = statistictime;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRulekey() {
		return this.rulekey;
	}

	public void setRulekey(String rulekey) {
		this.rulekey = rulekey;
	}

	public String getBannerkey() {
		return this.bannerkey;
	}

	public void setBannerkey(String bannerkey) {
		this.bannerkey = bannerkey;
	}

	public String getClicked() {
		return this.clicked;
	}

	public void setClicked(String clicked) {
		this.clicked = clicked;
	}

	public Date getStatistictime() {
		return this.statistictime;
	}

	public void setStatistictime(Date statistictime) {
		this.statistictime = statistictime;
	}

}
