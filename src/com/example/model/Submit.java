package com.example.model;

import java.sql.Date;
import java.util.Calendar;

import android.content.Context;

public class Submit {
	private int id;
	private String submitnum;
	private String username;
	private String dingcairen;
	private String tel;
	private String address;
	private String zipcode;
	private String city;
	private String state;
	private String cantingname;
	private String daodiantime;
	private Double amount;
	private Double total;
	private String contract;
	private Context context;
	
//	private int renshu;
//	private String cantingname;
//	private boolean fukuan;
//	private boolean queding;
//	private String adddate;

	public Submit(Context context) {
		this.context = context;

	}

//	public submit(int id, String submitnum, String username, String tel,
//			int renshu, String cantingname, String daodiantime, String contract,
//			boolean fukuan, boolean queding, Double totalmoney, String adddate) {
	public Submit(int id, String submitnum, String username, String dingcairen, String tel, String address, 
			String zipcode, String city, String state, String cantingname, String daodiantime, Double amount, 
			Double total, String contract) {
		this.id = id;
		this.submitnum = submitnum;
		this.username = username;
		this.dingcairen = dingcairen;
		this.tel = tel;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.cantingname = cantingname;
		this.daodiantime = daodiantime;
		this.amount = amount;
		this.total = total;
		this.contract = contract;
		
//		this.submitnum = submitnum;
//		this.renshu = renshu;
//		this.fukuan = fukuan;
//		this.queding = queding;
//		this.totalmoney = totalmoney;
//		this.adddate = adddate;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public int get_id() {
		return id;
	}

	public void set_submitnum(String submitnum) {
		this.submitnum = submitnum;
	}
	
	public String get_submitnum() {
		return submitnum;
	}
	
	public void set_username(String username) {
		this.username = username;
	}

	public String get_username() {
		return username;
	}
	
	public void set_dingcairen(String dingcairen) {
		this.dingcairen = dingcairen;
	}

	public String get_dingcairen() {
		return dingcairen;
	}

	public void set_tel(String tel) {
		this.tel = tel;
	}

	public String get_tel() {
		return tel;
	}
	
	public void set_address(String address) {
		this.address = address;
	}

	public String get_address() {
		return address;
	}
	
	public void set_zipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String get_zipcode() {
		return zipcode;
	}
	
	public void set_city(String city) {
		this.city = city;
	}

	public String get_city() {
		return city;
	}
	
	public void set_state(String state) {
		this.state = state;
	}

	public String get_state() {
		return state;
	}
	
	public void set_cantingname(String cantingname) {
		this.cantingname = cantingname;
	}
	
	public String get_cantingname() {
		return cantingname;
	}
	
	public void set_daodiantime(String daodiantime) {
		this.daodiantime = daodiantime;
	}

	public String get_daodiantime() {
		return daodiantime;
	}

	public void set_amount(Double amount) {
		this.amount = amount;
	}

	public Double get_amount() {
		return amount;
	}
	
	public void set_total(Double total) {
		this.total = total;
	}

	public Double get_total() {
		return total;
	}
	
	public void set_contract(String contract) {
		this.contract = contract;
	}

	public String get_contract() {
		return contract;
	}
	
	
	
//	public void set_renshu(int renshu) {
//		this.renshu = renshu;
//	}
	
//	public int get_renshu() {
//		return renshu;
//	}
	
//
//	public void set_fukuan(boolean fukuan) {
//		this.fukuan = fukuan;
//	}
//
//	public boolean get_fukuan() {
//		return fukuan;
//	}
//
//	public void set_queding(boolean queding) {
//		this.queding = queding;
//	}
//
//	public boolean get_queding() {
//		return queding;
//	}
//
//	public void set_adddate(String adddate) {
//		this.adddate = adddate;
//	}
//
//	public String get_adddate() {
//		return adddate;
//	}
//
//	public void set_totalmoney(Double totalmoney) {
//		this.totalmoney = totalmoney;
//	}
//
//	public Double get_totalmoney() {
//		return totalmoney;
//	}

}
