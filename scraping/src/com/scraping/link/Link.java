package com.scraping.link;

public interface Link extends Comparable<Link>{

	String getTarget();

	void setTarget(String target);

	String getText();

	void setText(String text);

	String getWebsite();

	void setWebsite(String ws);

		

}
