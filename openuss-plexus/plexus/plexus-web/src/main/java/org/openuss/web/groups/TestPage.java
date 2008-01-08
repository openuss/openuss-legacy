package org.openuss.web.groups; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$test", scope = Scope.REQUEST)
@View
public class TestPage extends AbstractCoursePage{
	
	
	List<List<String>> list;
	
	Set<String> set;
	
	Map<String, String> map;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		List<String> list2 = new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		list = new ArrayList<List<String>>();
		list.add(list1);
		list.add(list2);
		set = new HashSet<String>();
		set.add("1");
		set.add("2");
		set.add("3");
		map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");

	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<List<String>> getList() {
		return list;
	}

	public void setList(List<List<String>> list) {
		this.list = list;
	}

}