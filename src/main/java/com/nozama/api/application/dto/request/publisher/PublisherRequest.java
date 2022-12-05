package com.nozama.api.application.dto.request.publisher;

public class PublisherRequest {

	private String name;
	private String site;
	
	public PublisherRequest() {}
	public PublisherRequest(String name, String site) {
		this.name = name;
		this.site = site;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
}
