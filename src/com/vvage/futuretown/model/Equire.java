package com.vvage.futuretown.model;

public class Equire {
	private int xiaotu,datu;
	private String dianname,bianhao,add,pinming,price;
	public Equire(int xiaotu, int datu, String dianname, String bianhao,
			String add, String pinming, String price) {
		super();
		this.xiaotu = xiaotu;
		this.datu = datu;
		this.dianname = dianname;
		this.bianhao = bianhao;
		this.add = add;
		this.pinming = pinming;
		this.price = price;
	}
	public int getXiaotu() {
		return xiaotu;
	}
	public void setXiaotu(int xiaotu) {
		this.xiaotu = xiaotu;
	}
	public int getDatu() {
		return datu;
	}
	public void setDatu(int datu) {
		this.datu = datu;
	}
	public String getDianname() {
		return dianname;
	}
	public void setDianname(String dianname) {
		this.dianname = dianname;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Equire [xiaotu=" + xiaotu + ", datu=" + datu + ", dianname="
				+ dianname + ", bianhao=" + bianhao + ", add=" + add
				+ ", pinming=" + pinming + ", price=" + price + "]";
	}
	

}
