package com.example.myfood;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Meal;
import com.example.utils.MealDBManager;
import com.example.utils.FavoriteDBManager;
import com.example.utils.MyApplication;

public class JindianActivity extends Activity {
	private MyApplication myapplication1;
	private MealDBManager GoodsDBManager1;
	private FavoriteDBManager dinglikeDBManager;
	private TextView TextView1;
	private TextView TextView2;
	private TextView TextView3;
	private Button Button1;
	private Button Button2;
	private Bundle Bundle1;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jindian);
		myapplication1 = (MyApplication) getApplication();
		myapplication1.getInstance().addActivity(this);// 这个是为了退出时清除所有打开的activity
		GoodsDBManager1 = new MealDBManager(this);
		dinglikeDBManager = new FavoriteDBManager(this);
		TextView1 = (TextView) findViewById(R.jindian.textView1);
		TextView2 = (TextView) findViewById(R.jindian.textView2);
		TextView3 = (TextView) findViewById(R.jindian.textView3);
		Button1 = (Button) findViewById(R.jindian.button1);
		Button2 = (Button) findViewById(R.jindian.button2);
		Bundle1 = this.getIntent().getExtras();
		if (Bundle1.getBoolean("_dinglike")) {
			Button1.setEnabled(false);
			Button2.setEnabled(false);

		}
		final int id = Integer.parseInt(Bundle1.getString("_id"));
		TextView1.setText(Bundle1.getString("_title"));
		TextView2.setText("Price：$" + Bundle1.getString("_sell_price"));
		TextView3.setText(Html.fromHtml(Bundle1.getString("_content"),
				imgGetter, null));

		if (GoodsDBManager1.queryid(id)) {
			Button1.setText("Cart：" + GoodsDBManager1.totalcount());
		}

		if (dinglikeDBManager.exists(Integer.parseInt(Bundle1.getString("_id")
				.toString()))) {
			Button2.setText("Favorited");
			Button2.setEnabled(false);
		}

		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myapplication1.ifpass()) {
					if (GoodsDBManager1.queryid(id)) {
						Intent Intent1 = new Intent();
						Intent1.setClass(JindianActivity.this,
								OrderActivity.class);
						startActivity(Intent1);
					} else {

						addgoods();
					}
				} else {
					Intent Intent1 = new Intent();
					Intent1.setClass(JindianActivity.this,
							RegisterActivity.class);
					startActivity(Intent1);
				}
			}
		});

		Button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myapplication1.ifpass()) {
					if (!dinglikeDBManager.exists(Integer.parseInt(Bundle1
							.getString("_id").toString()))) {
						dinglikeDBManager.add(
								Integer.parseInt(Bundle1.getString("_id")),
								myapplication1.getusername());
						Toast.makeText(JindianActivity.this, "Added to favorites",
								Toast.LENGTH_SHORT).show();
						Button2.setText("Favorited");
						Button2.setEnabled(false);
					}
				} else {
					Intent Intent1 = new Intent();
					Intent1.setClass(JindianActivity.this,
							RegisterActivity.class);
					startActivity(Intent1);
				}
			}
		});

	}

	/**
	 * 添加点菜
	 */
	private void addgoods() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			try {

				Meal dt_goods = new Meal();
				dt_goods.set_id(Integer.parseInt(Bundle1.getString("_id")
						.toString()));
				dt_goods.set_channel_id(Integer.parseInt(Bundle1.getString(
						"_channel_id").toString()));
				dt_goods.set_category_id(Integer.parseInt(Bundle1.getString(
						"_category_id").toString()));
				dt_goods.set_title(Bundle1.getString("_title").toString());
				dt_goods.set_goods_no(Bundle1.getString("_goods_no").toString());
				dt_goods.set_stock_quantity(Integer.parseInt(Bundle1.getString(
						"_stock_quantity").toString()));
				dt_goods.set_market_price(Bundle1.getString("_market_price")
						.toString());
				dt_goods.set_sell_price(Bundle1.getString("_sell_price")
						.toString());
				dt_goods.set_point(Integer.parseInt(Bundle1.getString("_point")
						.toString()));
				dt_goods.set_link_url(Bundle1.getString("_link_url").toString());
				dt_goods.set_img_url(Bundle1.getString("_img_url").toString());
				dt_goods.set_seo_title(Bundle1.getString("_seo_title")
						.toString());
				dt_goods.set_seo_keywords(Bundle1.getString("_seo_keywords")
						.toString());
				dt_goods.set_seo_description(Bundle1.getString(
						"_seo_description").toString());
				dt_goods.set_content(Bundle1.getString("_content").toString());
				dt_goods.set_sort_id(Integer.parseInt(Bundle1.getString(
						"_sort_id").toString()));
				dt_goods.set_click(Integer.parseInt(Bundle1.getString("_click")
						.toString()));
				dt_goods.set_is_msg(Integer.parseInt(Bundle1.getString(
						"_is_msg").toString()));
				dt_goods.set_is_top(Integer.parseInt(Bundle1.getString(
						"_is_top").toString()));
				dt_goods.set_is_red(Integer.parseInt(Bundle1.getString(
						"_is_red").toString()));
				dt_goods.set_is_red(Integer.parseInt(Bundle1.getString(
						"_is_hot").toString()));
				dt_goods.set_is_slide(Integer.parseInt(Bundle1.getString(
						"_is_slide").toString()));
				dt_goods.set_is_lock(Integer.parseInt(Bundle1.getString(
						"_is_lock").toString()));
				dt_goods.set_user_id(Integer.parseInt(Bundle1.getString(
						"_user_id").toString()));
				dt_goods.set_add_time(Bundle1.getString("_add_time").toString());
				dt_goods.set_digg_good(Integer.parseInt(Bundle1.getString(
						"_digg_good").toString()));
				dt_goods.set_digg_bad(Integer.parseInt(Bundle1.getString(
						"_digg_bad").toString()));
				dt_goods.set_buynumber(1);
				dt_goods.set_user(myapplication1.getusername());
				GoodsDBManager1.add(dt_goods);

				Button1.setText("Added");
				Button1.setEnabled(false);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	};

	/***
	 * 返回服务器地址
	 * 
	 * @return
	 */
	public String getlocalhost() {
		return myapplication1.getlocalhost();
	}

	/***
	 * html图片替换
	 */
	ImageGetter imgGetter = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			Log.d("Image Path", source);
			URL url;
			try {
				url = new URL(getlocalhost() + source);
				drawable = Drawable.createFromStream(url.openStream(), "");
			} catch (Exception e) {
				return null;
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}
}
