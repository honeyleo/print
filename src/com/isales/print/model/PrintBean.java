package com.isales.print.model;

import java.util.ArrayList;
import java.util.List;

public class PrintBean {

	private ShopInfo shopinfo;
	
	private ClientInfo clientinfo;
	
	private CarInfo carinfo;
	
	private List<Boutique> boutique = new ArrayList<Boutique>();
	
	public ShopInfo getShopinfo() {
		return shopinfo;
	}

	public void setShopinfo(ShopInfo shopinfo) {
		this.shopinfo = shopinfo;
	}

	public ClientInfo getClientinfo() {
		return clientinfo;
	}

	public void setClientinfo(ClientInfo clientinfo) {
		this.clientinfo = clientinfo;
	}

	public CarInfo getCarinfo() {
		return carinfo;
	}

	public void setCarinfo(CarInfo carinfo) {
		this.carinfo = carinfo;
	}

	public List<Boutique> getBoutique() {
		return boutique;
	}

	public void setBoutique(List<Boutique> boutique) {
		this.boutique = boutique;
	}

}
