package com.example.myfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ArticleAdapter;
import com.example.jsonservices.ArticleJSON;
import com.example.model.Article;
import com.example.myfood.R.color;
import com.example.utils.MyApplication;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;

public class MainActivity extends Activity {
	private MyApplication myapplication1;
	private ProgressDialog ProgressDialog1; // 加载对话框
	private ListView listview1;
	private ArticleAdapter adapter;
	private int page = 1;
	private long waitTime = 2000;
	private long touchTime = 0;
	private TextView textView;
	private ImageButton ImageButton1;// 语音
	private ImageButton ImageButton2;// 语音
	private List<Article> list1 = null;
	private List<HashMap<String, Object>> articleslist = new ArrayList<HashMap<String, Object>>();

	private String textString = "";
	private Thread Thread1;
	private boolean havedata = true; // 来判断是否还有数据
	private EditText setedit;
	private ImageButton yuyinButton;
	private RelativeLayout rela;

	private List<Article> Articles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myapplication1 = (MyApplication) getApplication();
		myapplication1.getInstance().addActivity(this);
		rela = (RelativeLayout) findViewById(R.id.rlay);
//		listview1 = (ListView) findViewById(R.id.mlistView1);
		textView = (TextView) findViewById(R.id.textView2);
		ImageButton1 = (ImageButton) findViewById(R.id.xunfeiimageButton1);
		ImageButton2 = (ImageButton) findViewById(R.id.systemimageButton1);
		final TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
		tabhost.setup();

		TabWidget tabwidget = tabhost.getTabWidget();
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("App Info")
				.setContent(R.id.tab1));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("Restaurant")
				.setContent(R.id.tab2));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("User Profile")
				.setContent(R.id.tab3));

		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout LinearLayout1 = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.searchdialog, null);

				setedit = (EditText) LinearLayout1
						.findViewById(R.searchdialog.editText1);
				yuyinButton = (ImageButton) LinearLayout1
						.findViewById(R.searchdialog.imageButton1);
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Please input key words")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(LinearLayout1)
						.setPositiveButton("Cancel", null)
						.setNegativeButton("Confirm",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// Obtain Data
										searchtext(setedit.getText().toString());
									}
								}).show();
				yuyinButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showDialog(1);
					}
				});
			}
		});

		/**
		 * 系统设置
		 */
		ImageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SystemActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
//				Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
//				startActivity(settingsActivity);
//				return true;
			}
		});

		/***
		 * 餐厅预定
		 */
		tabhost.getTabWidget().getChildAt(1)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tabhost.setCurrentTab(0);

						Intent intent = new Intent();
						intent.setClass(MainActivity.this, DiquActivity.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
					}
				});

		tabhost.getTabWidget().getChildAt(2)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tabhost.setCurrentTab(0);
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,
								RegisterActivity.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
					}

				});

		//bindarticle();
	}

	public String localhost() {
		return myapplication1.getlocalhost();
	}

	/**
	 * 退出系统
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				myapplication1.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 语音查询
	 */
	@Override
	protected Dialog onCreateDialog(int id) {

		RecognizerDialog recognizerDialog = new RecognizerDialog(
				MainActivity.this, "appid=5132fe14");// 这里应该写从科大讯飞申请到的appid
		recognizerDialog.setEngine("sms", null, null);
		recognizerDialog.setListener(new RecognizerDialogListener() {
			@Override
			public void onResults(ArrayList<RecognizerResult> results,
					boolean arg1) {
				for (int i = 0; i < results.size(); i++) {
					textString += results.get(i).text;
				}
			}

			@Override
			public void onEnd(SpeechError arg0) {
				// Toast.makeText(MainActivity.this, textString, 1).show();
				setedit.setText(textString.substring(0, textString.length() - 1));
			}
		});
//		return recognizerDialog;
		return null;
	}

	/***
	 * 主页面查询操作一个是语音一个是文本输入
	 * 
	 * @param text
	 */
	public void searchtext(String text) {
		if ("".equals(myapplication1.getdiqu())) {
			Intent Intent1 = new Intent();
			Intent1.setClass(MainActivity.this, DiquActivity.class);
			startActivity(Intent1);
			Toast.makeText(MainActivity.this, "Please choose Restaurant Address", 1).show();
		} else {
			myapplication1
					.setgoodsurl("/android/json_goods/list.aspx?channel_id=2&keywords='"
							+ java.net.URLEncoder.encode(text) + "'&page=");

			Intent Intent1 = new Intent();
			Intent1.setClass(MainActivity.this, MenuActivity.class);
			Bundle Bundle1 = new Bundle();
			Bundle1.putString("tg", "0");
			Intent1.putExtras(Bundle1);
			startActivity(Intent1);
		}

	}

}
