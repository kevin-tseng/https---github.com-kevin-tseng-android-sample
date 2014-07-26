package org.browseData;

public class Site {
	private String id;
	private String name;
	private String phoneNo;
	private String address;
	
	public Site(){}
	public Site(String id, String name, String phoneNo, String address){
		this.id = id;
		this.name = name;
		this.phoneNo = phoneNo;
		this.address = address;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
}
