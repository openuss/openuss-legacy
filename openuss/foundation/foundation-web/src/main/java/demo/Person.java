/**
 * 
 */
package demo;

/**
 * Created by Exadel Studio
 *
 */
public class Person {
	private String name;
	private String firstName;

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("set name = "+name);
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}