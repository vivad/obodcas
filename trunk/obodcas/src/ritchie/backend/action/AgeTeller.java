package ritchie.backend.action;

import java.util.ArrayList;
import java.util.Map;

public class AgeTeller {
	
	private String name1;
	private int age;
	private String gender;
	private String country;
	private String about;
	private boolean travelling;
	private ArrayList<String> travellingCountry;
	//private Map<Integer,String> travellingCountry;
		
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	public boolean isTravelling() {
		return travelling;
	}
	public void setTravelling(boolean travelling) {
		this.travelling = travelling;
	}
	
	/*public Map<Integer, String> getTravellingCountry() {
		return travellingCountry;
	}
	public void setTravellingCountry(Map<Integer, String> travellingCountry) {
		this.travellingCountry = travellingCountry;
	}*/
	public ArrayList<String> getTravellingCountry() {
		return travellingCountry;
	}
	public void setTravellingCountry(ArrayList<String> travellingCountry) {
		this.travellingCountry = travellingCountry;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		System.out.println("age in setter: "+age);
		this.age = age;
	}
	
	public String execute(){
		System.out.println(getAge());
		if(getAge()>10){
			System.out.println("in success");
			return "success";
		}else{
			System.out.println("in failure");
			return "failure";
		}
	}
}
