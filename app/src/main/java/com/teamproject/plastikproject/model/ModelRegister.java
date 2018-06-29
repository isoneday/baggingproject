package com.teamproject.plastikproject.model;

import com.google.gson.annotations.SerializedName;


public class ModelRegister{

	@SerializedName("is_admin")
	private String isAdmin;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	public void setIsAdmin(String isAdmin){
		this.isAdmin = isAdmin;
	}

	public String getIsAdmin(){
		return isAdmin;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"ModelRegister{" + 
			"is_admin = '" + isAdmin + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}