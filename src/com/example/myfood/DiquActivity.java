package com.example.myfood;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.example.adapter.RestaurantAdapter;
import com.example.control.MyListView;
import com.example.control.MyListView.OnRefreshListener;
import com.example.jsonservices.RestaurantJSON;
import com.example.method.mymethod;
import com.example.model.Restaurant;
import com.example.utils.DBhelper;
import com.example.utils.MyApplication;

public class DiquActivity extends Activity {

	private ProgressDialog ProgressDialog1; // 加载对话框
	private mymethod mymethods; // 公共方法调用
	private RestaurantAdapter adapter; // 店铺adapter
	private SimpleAdapter dqadapter; // 地区adapter
	private MyApplication myapplication1;
	private Thread thread = null;
	private MyListView listview1;
	private View view;
	private List<Restaurant> list1;
	private List<HashMap<String, Object>> categorylist = new ArrayList<HashMap<String, Object>>();
	private List<HashMap<String, Object>> dqlist = null;
	private final int pageType = 1;
	private Thread Thread1;
	private boolean havedata = true; // 来判断是否还有数据
	private TabHost tabhost;
	private List<Restaurant> dqcategory;
	private List<Restaurant> Categorys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diqu);
		try {
			myapplication1 = (MyApplication) getApplication();
			myapplication1.getInstance().addActivity(this);
			tabhost = (TabHost) findViewById(android.R.id.tabhost);
			tabhost.setup();
			TabWidget tabwidget = tabhost.getTabWidget();
			tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("Campus")
					.setContent(R.id.tab1));
			tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("View Map")
					.setContent(R.id.tab2));
			listview1 = (MyListView) findViewById(R.id.listview1);

			
			threadstart();
			binddq();
			tabhost.getTabWidget().getChildAt(0)
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							tabhost.setCurrentTab(0);

							try {
								Builder mydialog = new AlertDialog.Builder(
										DiquActivity.this);
								mydialog.setTitle("Campus Selection");
								mydialog.setAdapter(dqadapter,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub

												try {
													String test = "URL area_id=" + dqlist.get(which).get("id");
													Log.v("JSON", test);
													myapplication1
															.setcategoryurl("servlet/JsonAction?action_flag=Category&area_id="
																	+ dqlist.get(which).get("id"));
													myapplication1.setAreaId((String) dqlist.get(which).get("id"));
													Intent Intent1 = new Intent();
													Intent1.setClass(
															DiquActivity.this,
															DiquActivity.class);
													startActivity(Intent1);

													finish();
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										});
								mydialog.show();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			// 单击事件跳转到店铺详细页
			listview1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					Intent intent1 = new Intent();
//					intent1.setClass(DiquActivity.this, CantingActivity.class);
					intent1.setClass(DiquActivity.this, MenuActivity.class);
					Bundle bundle1 = new Bundle();
					bundle1.putInt(
							"id",
							Integer.parseInt(categorylist.get(arg2 - 1)
									.get("_id").toString()));
					bundle1.putInt(
							"channel_id",
							Integer.parseInt(categorylist.get(arg2 - 1)
									.get("_channel_id").toString()));
					bundle1.putString("title",
							categorylist.get(arg2 - 1).get("_title").toString());
					bundle1.putString("call_index", categorylist.get(arg2 - 1)
							.get("_call_index").toString());
					bundle1.putInt(
							"parent_id",
							Integer.parseInt(categorylist.get(arg2 - 1)
									.get("_parent_id").toString()));
					bundle1.putString("class_list", categorylist.get(arg2 - 1)
							.get("_class_list").toString());
					bundle1.putInt(
							"class_layer",
							Integer.parseInt(categorylist.get(arg2 - 1)
									.get("_class_layer").toString()));
					bundle1.putInt(
							"sort_id",
							Integer.parseInt(categorylist.get(arg2 - 1)
									.get("_sort_id").toString()));
					bundle1.putString("link_url", categorylist.get(arg2 - 1)
							.get("_link_url").toString());
					bundle1.putString("img_url", categorylist.get(arg2 - 1)
							.get("_img_url").toString());
					bundle1.putString("content", categorylist.get(arg2 - 1)
							.get("_content").toString());
					bundle1.putString("seo_title", categorylist.get(arg2 - 1)
							.get("_seo_title").toString());
					bundle1.putString("seo_keywords", categorylist
							.get(arg2 - 1).get("_seo_keywords").toString());
					bundle1.putString("seo_description",
							categorylist.get(arg2 - 1).get("_seo_description")
									.toString());

					intent1.putExtras(bundle1);
					startActivity(intent1);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					myapplication1.setdiqu(categorylist.get(arg2 - 1)
							.get("_title").toString()); // 传递全局地区选择变量
				}
			});

			listview1.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					// TODO Auto-generated method stub
					// 当不滚动时
					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						// 判断是否滚动到底部
						if (view.getLastVisiblePosition() == view.getCount() - 1) {
							if (havedata) {
//								threadmore();
							}
						}
					}
					switch (scrollState) {
					case OnScrollListener.SCROLL_STATE_FLING:
						adapter.setFlagBusy(true);
						break;
					case OnScrollListener.SCROLL_STATE_IDLE:
						adapter.setFlagBusy(false);
						break;
					case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
						adapter.setFlagBusy(false);
						break;
					default:
						break;
					}
					adapter.notifyDataSetChanged();

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub

				}
			});

			listview1.setonRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					// TODO Auto-generated method stub
					if (havedata) {
						threadmore();
					}
					new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void... params) {
							// 因为这个列表加载不是在android中设置，而是通过服务器端获取，所以当page页超过时，服务端是没有数据显示的，为了避免程序的出错而对page进行了try判断一旦数据出问题了就不再刷新
							// 由于我现在水平有限对这个判断问题还没有得到妥善解决。

							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							try {
								listview1.onRefreshComplete();
							} catch (Exception e) {
								e.printStackTrace();
								ProgressDialog1.dismiss();
							}
						}

					}.execute();
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			mymethods.showMsg(DiquActivity.this, "No Network Connection");
		}
	}

	public String localhost() {
		return myapplication1.getlocalhost();
	}

	/**
	 * 启动地图
	 */
	private void qdmap() {
		Log.v("Test", "qdmap()");
		String area_id = myapplication1.getAreaId();

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < categorylist.size(); i++) {
			if (!"".equals(categorylist.get(i).get("_title").toString())) {

				arrayList.add(area_id + ","
						+ categorylist.get(i).get("_id").toString() + ","
						+ categorylist.get(i).get("_title").toString() + ","
						+ categorylist.get(i).get("_content").toString() + ","
						+ categorylist.get(i).get("_seo_title").toString());
			}
		}

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.setClass(DiquActivity.this, MapActivity.class);

		bundle.putStringArrayList("ResMap", arrayList);
		intent.putExtras(bundle);
		startActivity(intent);
		Log.v("Test", "start ACT");
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/***
	 * 返回地区列表
	 */
	private void binddq() {
		try {
			dqlist = new ArrayList<HashMap<String, Object>>();
			
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("id", "0");
			item.put("title", "All Campuses");
			dqlist.add(item);
			item = new HashMap<String, Object>();
			item.put("id", "1");
			item.put("title", "College Ave");
			dqlist.add(item);
			item = new HashMap<String, Object>();
			item.put("id", "2");
			item.put("title", "Busch");
			dqlist.add(item);
			item = new HashMap<String, Object>();
			item.put("id", "3");
			item.put("title", "Livingston");
			dqlist.add(item);
			item = new HashMap<String, Object>();
			item.put("id", "4");
			item.put("title", "Cook/Douglass");
			dqlist.add(item);
			dqadapter = new SimpleAdapter(DiquActivity.this, dqlist,
					R.layout.alertdialog_dq, new String[] { "title", ""},
					new int[] { R.id.alertdialog_dq_textView1,
					R.id.alertdialog_dq_textView2});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 配套使用加载读取
	 */
	public void threadstart() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("Loading data，please wait...");
		ProgressDialog1.show();
		Thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread1.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Handler1.sendEmptyMessage(0);
			}
		});
		Thread1.start();
	}

	private Handler Handler1 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				list1 = loaddata();
				bindlist(list1);
				adapter = new RestaurantAdapter(DiquActivity.this, list1);
				adapter.addlist(list1);
				listview1.setAdapter(adapter);

				tabhost.getTabWidget().getChildAt(1)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// tabhost.setCurrentTab(1);
								qdmap();

							}
						});

				ProgressDialog1.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialog1.dismiss();
				Toast.makeText(DiquActivity.this, "Weak Network Connection, please wait!", 1).show();
			}
		}
	};

	/***
	 * 加载更多
	 */
	public void threadmore() {
		ProgressDialog1 = new ProgressDialog(this);
		ProgressDialog1.setMessage("Loading data，please wait...");
		ProgressDialog1.show();
		Thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread1.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Handler2.sendEmptyMessage(0);
			}
		});
		Thread1.start();
	}

	private Handler Handler2 = new Handler() {
		public void handleMessage(Message msg) {
			try {
				list1 = loaddata();
				bindlist(list1);
//				adapter.addlist(list1);
				adapter.notifyDataSetChanged();
				ProgressDialog1.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				ProgressDialog1.dismiss();
				adapter.notifyDataSetChanged();
				Toast.makeText(DiquActivity.this, "No record", 1).show();
				havedata = false;
			}
		}
	};

	public void bindlist(List<Restaurant> addlist) {
		try {
			for (Restaurant categorys : addlist) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("_id", categorys.get_id());
				item.put("_channel_id", categorys.get_channel_id());
				item.put("_title", categorys.get_title());
				item.put("_call_index", categorys.get_call_index());
				item.put("_parent_id", categorys.get_parent_id());
				item.put("_class_list", categorys.get_class_list());
				item.put("_class_layer", categorys.get_class_layer());
				item.put("_sort_id", categorys.get_sort_id());
				item.put("_link_url", categorys.get_link_url());
				item.put("_img_url", categorys.get_img_url());
				item.put("_content", categorys.get_content());
				item.put("_seo_title", categorys.get_seo_title());
				item.put("_seo_keywords", categorys.get_seo_keywords());
				item.put("_seo_description", categorys.get_seo_description());
				categorylist.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 加载json数据
	 */
	@SuppressLint("NewApi")
	
	public List<Restaurant> loaddata() {

		String temp_url = localhost() + myapplication1.getcategoryurl();

		try {
			Categorys = RestaurantJSON.getJsonCategory(temp_url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Categorys;
	}
	


	/**
	 * 打电话
	 * 
	 * @param tel
	 */
	public void calltel(String tel) {
		try {
			final String[] spStr = tel.split(",");
			new AlertDialog.Builder(this).setItems(spStr,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(
									"android.intent.action.CALL", Uri
											.parse("tel:" + spStr[which]));
							startActivity(intent);
						}
					}).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			Intent intent = new Intent();
			intent.setClass(DiquActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
	
	
}