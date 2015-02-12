package com.example.myfood;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jsonservices.UserJSON;
import com.example.model.Meal;
import com.example.model.Submit;
import com.example.model.User;
import com.example.utils.DBhelper;
import com.example.utils.MealDBManager;
import com.example.utils.SubmitDBManager;
import com.example.utils.OrderDBManager;
import com.example.utils.MyApplication;

public class SubmitActivity extends Activity {
	private MyApplication myapplication1;
	private ProgressDialog ProgressDialog1; // 加载对话框
	private SubmitDBManager submitDBManager1;
	private OrderDBManager dingnumDBManager1;
	private MealDBManager goodsDBManager1;
	private Calendar calendar;
	private String selectdate = null;
	private EditText mDingCaiRen; // Ding Cai Ren
	private EditText mUserCellPhone;// Phone Number
	private EditText mUserAddress;// Address
	private EditText mUserZipCode;// ZipCode
	private EditText mUserCity;// City
	private EditText mUserState;// State
	private EditText mUserTime;// Time
	private EditText mAmount;// Amount
	private EditText mTotal;// Total
	private EditText mRequirement; // Special Requirements
	private Button button1;// Confirm
	private List<User> Users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		myapplication1 = (MyApplication) getApplication();
		myapplication1.getInstance().addActivity(this);
		submitDBManager1 = new SubmitDBManager(this);
		dingnumDBManager1 = new OrderDBManager(this);
		goodsDBManager1 = new MealDBManager(this);
		calendar = Calendar.getInstance();
		
		mDingCaiRen = (EditText) findViewById(R.id.user_name); // User name
		mUserCellPhone = (EditText) findViewById(R.id.user_cell);// Phone Number
		mUserAddress = (EditText) findViewById(R.id.user_address);// Address
		mUserZipCode = (EditText) findViewById(R.id.user_zip);// ZipCode
		mUserCity = (EditText) findViewById(R.id.user_city);// City
		mUserState = (EditText) findViewById(R.id.user_state);// State
		mUserTime = (EditText) findViewById(R.id.user_time);// Time
		mRequirement = (EditText) findViewById(R.tijiao.DeditText1); // Special Requirements
		mAmount = (EditText) findViewById(R.id.amount);// Amount
		mTotal = (EditText) findViewById(R.id.total);// Total
		
		button1 = (Button) findViewById(R.tijiao.button1);
		binddata();
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateding();
			}
		});
	}

	/**
	 * 更新订单
	 */
	public void updateding() {

		if (mDingCaiRen.getText().toString().equals("")) {
			Toast.makeText(this, "User Name can not be Empty", 1).show();
		} else if (mUserCellPhone.getText().toString().equals("")) {
			Toast.makeText(this, "User Cell Phone can not be Empty", 1).show();
		} else if (mUserZipCode.getText().toString().equals("")) {
			Toast.makeText(this, "ZipCode can not be Empty", 1).show();
		} else if (mUserCity.getText().toString().equals("")) {
			Toast.makeText(this, "City can not be Empty", 1).show();
		} else if (mUserState.getText().toString().equals("")) {
			Toast.makeText(this, "State can not be Empty", 1).show();
		} else {
			new AlertDialog.Builder(SubmitActivity.this)
					.setTitle("Reminder")
					.setMessage("Ready to submit?")
					.setPositiveButton("Cancel", null)
					.setNegativeButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// 保存本地数据库
									step1();
									// 提交到服务器
									step2();
									// 清空购物车，跳转到地区介绍页面
									step3();
								}

							}).show();

		}
	}

	/**
	 * 提交订单第一步，将订单存入sqlite数据库中
	 */
	private void step1() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("Submitting...");
		ProgressDialog1.show();
		Log.v("step1","show");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					handler1.sendEmptyMessage(0);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 提交订单第一步，将订单存入sqlite数据库中
	 */
	private void step2() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("Submitting...");
		ProgressDialog1.show();
		Log.v("step2","show");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					handler2.sendEmptyMessage(0);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 提交订单第一步，将订单存入sqlite数据库中
	 */
	private void step3() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("Submitting...");
		ProgressDialog1.show();
		Log.v("step3","show");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					handler3.sendEmptyMessage(0);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				/**
				 * 提交订单
				 */
				Log.v("step1","deal");
				Submit modelSubmit = new Submit(SubmitActivity.this);
				modelSubmit.set_submitnum(myapplication1.get_dingdanstring());
				modelSubmit.set_username(myapplication1.getusername());
				modelSubmit.set_dingcairen(mDingCaiRen.getText().toString());
				modelSubmit.set_tel(mUserCellPhone.getText().toString());	
				modelSubmit.set_address(mUserAddress.getText().toString());
				modelSubmit.set_zipcode(mUserZipCode.getText().toString());
				modelSubmit.set_city(mUserCity.getText().toString());
				modelSubmit.set_state(mUserState.getText().toString());
				modelSubmit.set_cantingname(myapplication1.getdiqu().toString());
				modelSubmit.set_daodiantime(mUserTime.getText().toString());
				if ("".equals(mRequirement.getText().toString())) {
					modelSubmit.set_contract("null");
				} else {
					modelSubmit.set_contract(mRequirement.getText().toString());
				}
				modelSubmit.set_amount(Double.valueOf(mAmount.getText()
						.toString()));
				double money = Double.valueOf(mTotal.getText().toString().substring(1));

				modelSubmit.set_total(money);
//				modelSubmit.set_renshu(Integer.valueOf(AEditText3.getText()
//						.toString()));
//				modelSubmit.set_fukuan(false);
//				modelSubmit.set_queding(true);
				submitDBManager1.updateding(modelSubmit);
				/**
				 * 插入对应订单所点菜品
				 */
				dingnumDBManager1
						.insertdingnum(myapplication1.get_dingdanstring(),modelSubmit.get_username().toString());
//								loaddata().get(0).get_user_name());
				
				
				
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				ProgressDialog1.dismiss();
				e.printStackTrace();
			}
		}
	};

	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			boolean res = false;
			Log.v("step2","deal");
			String message = "";
			String dishId = "dishId";
			String dishQuant = "dishQuant";
			String dingcairen = mDingCaiRen.getText().toString();
			String userId = myapplication1.getusername();
			String userAddress = mUserAddress.getText().toString();
			String userZipCode = mUserZipCode.getText().toString();
			String userCity = mUserCity.getText().toString();
			String userState = mUserState.getText().toString();
			String amount = mAmount.getText().toString();
			String total = mTotal.getText().toString();
			String contract = new String();
			if ("".equals(mRequirement.getText().toString())) {
				contract = "null";
			} else {
				contract = mRequirement.getText().toString();
			}
			int count = Integer.parseInt(amount);
			
			userAddress = userAddress.replaceAll("\\s+","%");
			
			
			try {
				int result;
				String target = myapplication1.getlocalhost() + "servlet/ReceiveOrderServlet?";
				
				HttpPost httprequest = new HttpPost(target);
				List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
				paramsList.add(new BasicNameValuePair("userName", dingcairen));
				paramsList.add(new BasicNameValuePair("userId", userId));
				paramsList.add(new BasicNameValuePair("userAddress", userAddress));
				paramsList.add(new BasicNameValuePair("userZipcode", userZipCode));
				paramsList.add(new BasicNameValuePair("userCity", userCity));
				paramsList.add(new BasicNameValuePair("userState", userState));
				paramsList.add(new BasicNameValuePair("orderPrice", total));
				String restName = myapplication1.getdiqu();
				restName = restName.replaceAll("\\s+","%");
				paramsList.add(new BasicNameValuePair("resId", restName));
				paramsList.add(new BasicNameValuePair("orderSize", amount));
				paramsList.add(new BasicNameValuePair("contract", contract));
				
				List<Meal> goods = goodsDBManager1.query();
				while(count != 0){
					paramsList.add(new BasicNameValuePair(dishId + count, goods.get(count-1).get_title()));
					paramsList.add(new BasicNameValuePair(dishQuant + count, String.valueOf(goods.get(count-1).get_buynumber())));
					count--;
				}
				
				try {
					httprequest.setEntity(new UrlEncodedFormEntity(paramsList,
							"UTF-8"));
					HttpClient HttpClient1 = new DefaultHttpClient();
					HttpResponse httpResponse = HttpClient1
							.execute(httprequest);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						result = Integer.parseInt(EntityUtils.toString(httpResponse.getEntity()));
					} else {
						result = 0;
					}
					// 针对这里不能用字符串来判断，因为字符串判断是失效的，必须用数字来进行条件判断读取返回结果达到想要的页面跳转
					if (result == 1) {
						res = true;
					}

				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				ProgressDialog1.dismiss();
				e.printStackTrace();
			}
			
			if(res)
				message = "Submit Successful!";
			else
				message = "Submit Failure";
			Toast.makeText(SubmitActivity.this,
					message, Toast.LENGTH_SHORT).show();
		}
	};
	
	private Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				myapplication1.settotalgoods("0");
				goodsDBManager1.alldelete();
				Toast.makeText(SubmitActivity.this, "Your Order has been submitted，our customer service will reach you out soon", 1)
						.show();

				Intent intent = new Intent();
				intent.setClass(SubmitActivity.this, DiquActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 初始设置
	 */
	public void binddata() {
		try {
			if (!myapplication1.ifpass()) {

				Intent Intent1 = new Intent();
				Intent1.setClass(SubmitActivity.this, RegisterActivity.class);
				startActivity(Intent1);
				finish();
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			} else {
//				AEditText1.setText(loaddata().get(0).get_user_name());
//				AEditText2.setText(loaddata().get(0).get_telphone());
//				AEditText3.setText("2");
//				BEditText1.setText(myapplication1.getdiqu());
				mUserTime.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/***
						 * 设置到店时间
						 */
						new DatePickerDialog(SubmitActivity.this, dateListener,
								calendar.get(Calendar.YEAR), calendar
										.get(Calendar.MONTH), calendar
										.get(Calendar.DAY_OF_MONTH)).show();

					}
				});
				String totalMoney = String.format("$%.2f", Double.parseDouble(myapplication1.gettotalmoney()));
				mTotal.setText(totalMoney);
				mAmount.setText(myapplication1.gettotalgoods());
			}
		} catch (Exception e) {
			Log.v("Submit", "bind exception");
			e.printStackTrace();
			Intent Intent1 = new Intent();
			Intent1.setClass(SubmitActivity.this, RegisterActivity.class);
			startActivity(Intent1);
			finish();
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}
	}

	/***
	 * 日期选择监听事件，注意：与其配套的有 private Calendar calendar; calendar =
	 * Calendar.getInstance(); private String selectdate = null; 调用方法 new
	 * DatePickerDialog(SubmitActivity.this, dateListener,
	 * calendar.get(Calendar.YEAR), calendar .get(Calendar.MONTH), calendar
	 * .get(Calendar.DAY_OF_MONTH)).show();
	 */
	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int dayOfMonth) {
			// Calendar月份是从0开始,所以month要加1

			selectdate = year + "-" + (month + 1) + "-" + dayOfMonth;
			new TimePickerDialog(SubmitActivity.this, timeListener,
					calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
					false).show();

		};
	};

	TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			selectdate += " " + hourOfDay + ":" + minute + ":00";
			mUserTime.setText(selectdate);
		};
	};

	/***
	 * 日期选择监听事件
	 * 
	 * @return
	 */

	/***
	 * 读取文件流
	 * 
	 * @param page
	 * @return
	 */
	public List<User> loaddata() {

		try {
			Users = UserJSON.getjsonlastusers(myapplication1.getlocalhost()
					+ "/android/json_users/list.aspx?id="
					+ myapplication1.getusername());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Users;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			new AlertDialog.Builder(SubmitActivity.this)
					.setTitle("Cancel")
					.setMessage("Are you sure to cancel?")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("No", null)
					.setNegativeButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// 数据获取 ,顺序不要改
									myapplication1.deleteding(myapplication1
											.get_dingdanstring());
									myapplication1.set_dingdansring("");
									finish();
									overridePendingTransition(
											R.anim.in_from_right,
											R.anim.out_to_left);
								}
							}).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}
