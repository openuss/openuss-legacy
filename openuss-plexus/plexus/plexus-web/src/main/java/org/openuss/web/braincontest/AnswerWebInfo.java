package org.openuss.web.braincontest;

/**
 * @author Sebastian Roekens
 */
public class AnswerWebInfo{
	public String answer;
	
	public boolean topList;

	public AnswerWebInfo(){
		this.topList = true;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isTopList() {
		return topList;
	}

	public void setTopList(boolean topList) {
		this.topList = topList;
	}
	
}