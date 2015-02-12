package com.example.myfood;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.OrderAdapter;
import com.example.model.Meal;
import com.example.utils.MealDBManager;
import com.example.utils.MyApplication;

public class OrderActivity extends Activity {
	public MyApplication myapplication1;
	public MealDBManager GoodsDBManager1;
	public OrderAdapter adapter;
	public TextView TextView1;
	public TextView TextView2;
	public TextView TextView3;
	public Button Button1;
	public ListView ListView1;
	public List<Meal> listgoods;
	public ArrayList<Map<String, String>> list;
	private int pos; // 滚动位置记录
	private Thread Thread1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ding);
		myapplication1 = (MyApplication) getApplication();
		myapplication1.getInstance().addActivity(this);
		GoodsDBManager1 = new MealDBManager(this);
		TextView1 = (TextView) findViewById(R.activity_ding.textView1);
		TextView2 = (TextView) findViewById(R.activity_ding.textView2);
		TextView3 = (TextView) findViewById(R.activity_ding.textView3);
		Button1 = (Button) findViewById(R.activity_ding.button_submit);
		ListView1 = (ListView) findViewById(R.activity_ding.listView1);
		TextView1.setText("Please confirm the order.");
		TextView2.setText("Notice：Tax is not included.");
		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myapplication1.settotalmoney(String.valueOf(GoodsDBManager1
						.totalnum())); // 总金额
				myapplication1.settotalgoods(String.valueOf(listgoods.size())); // 点菜数量
				if("".equals(myapplication1.get_dingdanstring())){
					Random random = new Random();
				myapplication1.set_dingdansring(String.valueOf(random.nextInt(9999)));
				myapplication1.createding(myapplication1.get_dingdanstring());
				}
				Intent Intent1 = new Intent();
				Intent1.setClass(OrderActivity.this, SubmitActivity.class);
				startActivity(Intent1);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		query();
	}

	@Override
	public void onResume() {
		super.onResume();
		GoodsDBManager1.open(); /* connect to the database */
		query();
	}

	@Override
	public void onPause() {
		super.onPause();
		GoodsDBManager1.close();
	}

	public void updateData(Meal goods1, int val) {
		GoodsDBManager1.update(goods1, val);
	}

	public void deleteData(int _sid) {
		GoodsDBManager1.delete(_sid);
	}

	public String localhost() {
		return myapplication1.getlocalhost();
	}
	
	public void query() {
		GoodsDBManager1 = new MealDBManager(this);
		String total = String.format("Total：$%.2f", GoodsDBManager1.totalnum());
		TextView3.setText(total);
		listgoods = GoodsDBManager1.findAll();
		ListView1 = (ListView) findViewById(R.activity_ding.listView1);
		adapter = new OrderAdapter(this, listgoods);
		ListView1.setAdapter(adapter);
//		ListView1.setSelection(pos); // 恢复到之前的滚动位置	
//		ListView1.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				// 不滚动时保存当前滚动到的位置
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					pos = ListView1.getLastVisiblePosition();
//				}
//				switch (scrollState) {
//				case OnScrollListener.SCROLL_STATE_FLING:
//					adapter.setFlagBusy(true);
//					break;
//				case OnScrollListener.SCROLL_STATE_IDLE:
//					adapter.setFlagBusy(false);
//					break;
//				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//					adapter.setFlagBusy(false);
//					break;
//				default:
//					break;
//				}
//				adapter.notifyDataSetChanged();
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//			}
//		});
	}

	
	private Handler Handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				TextView3.setText("Total：$" + GoodsDBManager1.totalnum());
//				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(0x717);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}

}
