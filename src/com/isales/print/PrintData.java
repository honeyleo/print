package com.isales.print;

import com.isales.print.model.Mortgage;
import com.isales.print.model.Replacement;

public class PrintData {

	private int count = 1;
	private com.isales.print.model.PrintBean data;

	private Mortgage mortgage;
	
	private Replacement replacement;
	
	private String remark = "";
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public com.isales.print.model.PrintBean getData() {
		return data;
	}

	public void setData(com.isales.print.model.PrintBean data) {
		this.data = data;
	}

	public Mortgage getMortgage() {
		return mortgage;
	}

	public void setMortgage(Mortgage mortgage) {
		this.mortgage = mortgage;
	}

	public Replacement getReplacement() {
		return replacement;
	}

	public void setReplacement(Replacement replacement) {
		this.replacement = replacement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
