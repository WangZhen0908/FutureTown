package com.vvage.futuretown.model;

import android.widget.TextView;

public class Cang {
	private int im;
	private String pinming,bianhao,xiangqing;
	public Cang(int im, String pinming, String bianhao, String xiangqing) {
		super();
		this.im = im;
		this.pinming = pinming;
		this.bianhao = bianhao;
		this.xiangqing = xiangqing;
	}
	public int getIm() {
		return im;
	}
	public void setIm(int im) {
		this.im = im;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getXiangqing() {
		return xiangqing;
	}
	public void setXiangqing(String xiangqing) {
		this.xiangqing = xiangqing;
	}
	@Override
	public String toString() {
		return "Cang [im=" + im + ", pinming=" + pinming + ", bianhao="
				+ bianhao + ", xiangqing=" + xiangqing + "]";
	}
	
	

}
