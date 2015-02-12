package com.example.myfood;

import com.example.utils.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ResInfoActivity extends Activity {
	private Bundle Bundle1;
	private TextView TextView1; // 店名
	private MyApplication myapplication1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cantingjianjie);
		myapplication1 = (MyApplication) getApplication();
		myapplication1.getInstance().addActivity(this);
		TextView1 = (TextView) findViewById(R.id.textView1);

		Bundle1 = ResInfoActivity.this.getIntent().getExtras();
		TextView1.setText(Bundle1.getString("content"));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}

}
