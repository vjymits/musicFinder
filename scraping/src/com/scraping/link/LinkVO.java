package com.scraping.link;

public class LinkVO implements Link{
	
	
	private String target, text, website;
	
	
	
	public LinkVO(){}
	
	public LinkVO(String target, String text){
		this.target=target;
		this.text=text;
	}
	
	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void setTarget(String target) {
		target=target.trim();
		this.target = target;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getWebsite(){
		return website;
	}
	@Override
	public void setWebsite(String ws){
		this.website =ws;
	}

	@Override
	public int compareTo(Link o) {
		return this.getTarget().compareTo(o.getTarget());
	}
	
	@Override
	public boolean equals(Object obj){
		Link lnk = (LinkVO)obj;
		return lnk.getTarget().equals(this.getTarget());
			
		
	}

	

}
