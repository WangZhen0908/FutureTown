package com.vvage.futuretown.model;

public class ReleaseGoods {
	public int[] goodspic;
	/*
	 * 商家发布商品有
	 * 展示类型   展示  show  询价  ask  送货上门
	 */
	public String type;
	public String name;
	public String newPrice;
	public String price;
	/*
	 * 个人发布商品有
	 * 联系方式  qq 电话  邮箱  站内信
	 */
	public String contact;
	public String content;
	/*
	 * 个人发布商品有 需要手动填写
	 */
	public String address;
}
