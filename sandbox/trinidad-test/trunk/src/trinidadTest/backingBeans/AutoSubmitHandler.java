package trinidadTest.backingBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class AutoSubmitHandler {
	
	private Integer university = new Integer(-1);
	private Integer department = new Integer(-1);
	private Integer institution = new Integer(-1);
	private int state = 0;
	
	/* constructor */
	public AutoSubmitHandler(){
		System.out.println("New backing bean created!");
	}
	
	/* getter and setter */
	public Integer getUniversity(){
		System.out.println("getUniversity() called");
		if( university != null ){
			return university;
		} else {
			return new Integer(-1);
		}
	}
	
	public void setUniversity(Integer value){
		System.out.println("setUniversity called(): "+value);
		System.out.println("parameter: "+value.toString());
		university = value;
		department = new Integer(-1);
		institution = new Integer(-1);
		state = 1;
	}
	
	public Integer getDepartment(){
		System.out.println("getDepartment() called");
		if( department != null ){
			return department;
		} else {
			return new Integer(-1);
		}
	}
	
	public void setDepartment(Integer value){
		System.out.println("setDepartment() called: "+value);
		department = value;
		institution = new Integer(-1);
		state = 2;
	}
	
	public Integer getInstitution() {
		if( institution != null ){
			return institution;
		} else {
			return new Integer(-1);
		}
	}

	public void setInstitution(Integer institution) {
		System.out.println("setInstitution() called: "+institution);
		this.institution = institution;
		state = 3;
	}
	
	public int getState() {
		System.out.println("getState() called (current: "+state+")");
		return state;
	}

	public void setState(int state) {
		System.out.println("setState() called: "+state);
		this.state = state;
	}

	public List getUniversities(){
		System.out.println("getUniversities() called");
		List returnValue = new ArrayList();
		returnValue.add(new SelectItem(new Integer(-1), "- bitte auswählen -"));
		returnValue.add(new SelectItem(new Integer(10), "WWU Münster"));
		returnValue.add(new SelectItem(new Integer(11), "FH Münster"));
		return returnValue;
	}
	
	public List getDepartments(){
		List returnValue = new ArrayList();
		returnValue.add(new SelectItem(new Integer(-1), "- bitte auswählen -"));
		System.out.println("getDepartments() called");
		System.out.println("current university: "+getUniversity());
		if( getUniversity().equals(new Integer(10)) ){
			returnValue.add(new SelectItem(new Integer(20), "BWL"));
			returnValue.add(new SelectItem(new Integer(21), "Jura"));
		} else if(getUniversity().equals(new Integer(11))) {
			returnValue.add(new SelectItem(new Integer(30), "Design"));
			returnValue.add(new SelectItem(new Integer(31), "Soziale Arbeit"));
		}
		return returnValue;
	}

	public List getInstitutions(){
		List returnValue = new ArrayList();
		returnValue.add(new SelectItem(new Integer(-1), "- bitte auswählen -"));
		System.out.println("getInstitutions() called");
		System.out.println("current department: "+getDepartment());
		if( getDepartment().equals(new Integer(20)) ){
			returnValue.add(new SelectItem(new Integer(50), "LS BWL 1"));
			returnValue.add(new SelectItem(new Integer(51), "LS BWL 2"));
		} else if(getDepartment().equals(new Integer(21))) {
			returnValue.add(new SelectItem(new Integer(52), "LS Jura 1"));
			returnValue.add(new SelectItem(new Integer(53), "LS Jura 2"));
		} else if(getDepartment().equals(new Integer(30))) {
			returnValue.add(new SelectItem(new Integer(54), "LS Design 1"));
			returnValue.add(new SelectItem(new Integer(55), "LS Design 2"));
		} else if(getDepartment().equals(new Integer(31))) {
			returnValue.add(new SelectItem(new Integer(56), "LS Soziale Arbeit 1"));
			returnValue.add(new SelectItem(new Integer(57), "LS Soziale Arbeit 2"));
		}
		return returnValue;
	}
	
}
