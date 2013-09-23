package com.vvage.futuretown.model;

public class Text {
	private String tv;

	public Text(String tv) {
		super();
		this.tv = tv;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	@Override
	public String toString() {
		return "Text [tv=" + tv + "]";
	}

	
	

}
