package com.vvage.futuretown.model;

public class OrderManager {
	private int im;
	private String tv,tv2,tv3;
	public OrderManager(int im, String tv, String tv2, String tv3) {
		super();
		this.im = im;
		this.tv = tv;
		this.tv2 = tv2;
		this.tv3 = tv3;
	}
	public int getIm() {
		return im;
	}
	public void setIm(int im) {
		this.im = im;
	}
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public String getTv2() {
		return tv2;
	}
	public void setTv2(String tv2) {
		this.tv2 = tv2;
	}
	public String getTv3() {
		return tv3;
	}
	public void setTv3(String tv3) {
		this.tv3 = tv3;
	}
	@Override
	public String toString() {
		return "OrderManager [im=" + im + ", tv=" + tv + ", tv2=" + tv2
				+ ", tv3=" + tv3 + "]";
	}
	
}
