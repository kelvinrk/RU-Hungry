package com.example.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

import com.example.model.Submit;

public class SubmitDBManager {
	private DBhelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";
	public Context context;

	public SubmitDBManager(Context context) {
		helper = new DBhelper(context);
		db = helper.getWritableDatabase();
		this.context = context;
	}

	/**
	 * 新建订单
	 * 
	 * @param dingdanString
	 */
	public void createding(String dingdanString) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append("insert into [submit] (submitnum) values('"
				+ dingdanString + "')");
//		stringBuilder
//				.append("insert into [submit] (submitnum,adddate) values('"
//						+ dingdanString + "',datetime('now'))");
		db.execSQL(stringBuilder.toString());

	}

	/**
	 * 删除订单
	 * 
	 * @param dingdanString
	 */
	public void deleteding(String dingdanString) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("delete from [submit] where submitnum = '"
				+ dingdanString + "'");
		db.execSQL(stringBuilder.toString());

	}

	/**
	 * 更新订单
	 * 
	 * @param model
	 */
	public void updateding(Submit model) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("update submit set ");
		strSql.append("username='" + model.get_username() + "',");
		strSql.append("dingcairen='" + model.get_dingcairen() + "',");
		strSql.append("tel='" + model.get_tel() + "',");
		strSql.append("address='" + model.get_address() + "',");
		strSql.append("zipcode='" + model.get_zipcode() + "',");
		strSql.append("city='" + model.get_city() + "',");
		strSql.append("state='" + model.get_state() + "',");		
		strSql.append("cantingname='" + model.get_cantingname() + "',");
		strSql.append("daodiantime='" + model.get_daodiantime() + "',");
//		strSql.append("renshu=" + model.get_renshu() + ",");
//		strSql.append("totalmoney = " + model.get_totalmoney() + ",");
		strSql.append("amount='" + model.get_amount() + "',");
		strSql.append("total='" + model.get_total() + "',");
		if (!"".equals(model.get_contract().toString())) {
			strSql.append("contract='" + model.get_contract() + "'");
		}
//		if (model.get_fukuan() == true) {
//			strSql.append("fukuan=" + 1 + ",");
//		} else {
//			strSql.append("fukuan=" + 0 + ",");
//		}
//		if (model.get_queding() == true) {
//			strSql.append("queding=" + 1 + " ");
//		} else {
//			strSql.append("queding=" + 0 + " ");
//		}
		strSql.append(" where submitnum='" + model.get_submitnum() + "'");
		db.execSQL(strSql.toString());
	}

	/**
	 * 刷出订单的list
	 * 
	 * @param username
	 * @return
	 */
	public List<Submit> query(String username) {
		ArrayList<Submit> dt_submit = new ArrayList<Submit>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from [submit] where username = '"
				+ username + "'");
		Cursor cursor = db.rawQuery(stringBuilder.toString(), null);
		cursor.moveToFirst();
		while (cursor.isAfterLast()==false) {
			Submit submit1 = new Submit(context);
			submit1.set_id(cursor.getInt(cursor.getColumnIndex("id")));
			submit1.set_submitnum(cursor.getString(cursor
					.getColumnIndex("submitnum")));
			submit1.set_username(cursor.getString(cursor
					.getColumnIndex("username")));
			submit1.set_dingcairen(cursor.getString(cursor
					.getColumnIndex("dingcairen")));
			submit1.set_tel(cursor.getString(cursor.getColumnIndex("tel")));
			submit1.set_address(cursor.getString(cursor.getColumnIndex("address")));
			submit1.set_zipcode(cursor.getString(cursor.getColumnIndex("zipcode")));
			submit1.set_city(cursor.getString(cursor.getColumnIndex("city")));
			submit1.set_state(cursor.getString(cursor.getColumnIndex("state")));
			submit1.set_cantingname(cursor.getString(cursor
					.getColumnIndex("cantingname")));
			submit1.set_daodiantime(cursor.getString(cursor
					.getColumnIndex("daodiantime")));
			submit1.set_amount(cursor.getDouble(cursor.getColumnIndex("amount")));
			submit1.set_total(cursor.getDouble(cursor.getColumnIndex("total")));
//			submit1.set_renshu(cursor.getInt(cursor.getColumnIndex("renshu")));
//			if (cursor.getLong(cursor.getColumnIndex("fukuan")) == 1) {
//				submit1.set_fukuan(true);
//			} else {
//				submit1.set_fukuan(false);
//			}
//			if (cursor.getLong(cursor.getColumnIndex("queding")) == 1) {
//				submit1.set_queding(true);
//			} else {
//				submit1.set_queding(false);
//			}
//			submit1.set_totalmoney(cursor.getDouble(cursor
//					.getColumnIndex("totalmoney")));
			submit1.set_contract(cursor.getString(cursor
					.getColumnIndex("contract")));
//			submit1.set_adddate(cursor.getString(cursor
//					.getColumnIndex("adddate")));
			dt_submit.add(submit1);
			cursor.moveToNext();
		}
		cursor.close();
		return dt_submit;
	}
	/*
	 * public boolean ifpass() { Cursor cursor; cursor =
	 * db.rawQuery("select pass from dt_users", null); if (cursor.moveToNext())
	 * { if ("1".equals(cursor.getString(cursor.getColumnIndex("pass")))) {
	 * cursor.close(); return true; } } else { cursor.close(); return false; }
	 * cursor.close(); return false; }
	 */

}
