package com.example.adapter;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.adapter.MenuAdapter.goodsViewHolder;
import com.example.model.Meal;
import com.example.myfood.OrderActivity;
import com.example.myfood.R;
import com.example.utils.MealDBManager;
import com.example.utils.StreamTool;

/***
 * 自定义baseadapter
 * 
 * @author Administrator
 * 
 */
public class OrderAdapter extends ArrayAdapter<Meal> {
	private OrderActivity context;
	private MealDBManager GoodsDBManager1;
//	private List<? extends Map<String, ?>> list;
	private List<Meal> list;
	private ImageLoader mImageLoader;
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	public OrderAdapter(OrderActivity context, List<Meal> list) {
		super(context, 0, list);
		new MealDBManager(context);
		this.context = context;
		this.list = list;
		mImageLoader = new ImageLoader(); // 声明图片文件流
	}

	public int getCount() {
		return list.size();
	}

	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ding, parent, false);
        }
        
        TextView textview1;
		TextView textview2;
		TextView textview3;
		Button Button1;
		Button Button2;
		ImageView mImageView;

		textview1 = (TextView) convertView.findViewById(R.ding.textView1);
		textview2 = (TextView) convertView.findViewById(R.ding.textView2);
		textview3 = (TextView) convertView.findViewById(R.ding.textView3);
		Button1 = (Button) convertView.findViewById(R.ding.button1);
		Button2 = (Button) convertView.findViewById(R.ding.button2);
		mImageView = (ImageView) convertView.findViewById(R.ding.imageView1);
		textview1.setText(list.get(position).get_title().toString());
		textview2.setText("Price：$"
				+ list.get(position).get_sell_price().toString());
		textview3.setText(String.valueOf(list.get(position).get_buynumber()).toString());
		
		if (!mBusy) {
			mImageLoader.dingloadImage(context.localhost() + list.get(position).get_img_url().toString(), this, mImageView);
		}
		
		else {
			Bitmap bitmap = mImageLoader.getBitmapFromCache(context
					.localhost()
					+ list.get(position).get_img_url().toString());
			if (bitmap != null) {
				mImageView.setImageBitmap(bitmap);
			} else {
				mImageView
						.setImageResource(R.drawable.ic_launcher);
			}
		}

		Button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.updateData(list.get(position), list.get(position).get_buynumber()+1);
				context.query();
			}
		});
		
		Button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("1".equals(String.valueOf(list.get(position).get_buynumber()))) {
					new AlertDialog.Builder(context)
							.setTitle("Delete the meal?")
							.setIcon(android.R.drawable.ic_dialog_info)

							.setPositiveButton(
									"No",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									})
							.setNegativeButton(
									"Yes",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											context.deleteData(list.get(position).get_id());
											context.query();
										}
									}).show();
				} else {
					context.updateData(list.get(position), list.get(position).get_buynumber()-1);
					context.query();
				}
			}
		});				
		return convertView;
	}

//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		dingViewHolder viewHolder = null;
//		View v = super.getView(position, convertView, parent);
//		try {
//
//			viewHolder = new dingViewHolder();
//			viewHolder.mImageView = (ImageView) v.findViewById(R.ding.imageView1);
//			viewHolder.textview1 = (TextView) v.findViewById(R.ding.textView1);
//			viewHolder.textview2 = (TextView) v.findViewById(R.ding.textView2);
//			viewHolder.textview3 = (TextView) v.findViewById(R.ding.textView3);
//			viewHolder.Button1 = (Button) v.findViewById(R.ding.button1);
//			viewHolder.Button2 = (Button) v.findViewById(R.ding.button2);
//			Log.v("Test", "Got sbuynumber");
//
//			try {
//				if (!mBusy) {
//					mImageLoader.dingloadImage(context.localhost() + list.get(position).get("_img_url").toString(), this, viewHolder);
//					viewHolder.textview1.setText(list.get(position)
//							.get("_title").toString());
//					viewHolder.textview2.setText("Price：$"
//							+ list.get(position).get("_sell_price").toString());
//					viewHolder.textview3.setText(String.valueOf(
//							list.get(position).get("_buynumber")).toString());
//				} else {
////					Bitmap bitmap = mImageLoader.getBitmapFromCache(context.localhost() + list.get(position).get("_img_url").toString());
////					if (bitmap != null) {
////						viewHolder.mImageView.setImageBitmap(bitmap);
////					} else {
////						viewHolder.mImageView
////								.setImageResource(R.drawable.ic_launcher);
////					}
//				}
//
//				viewHolder.Button1.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						int sbuynumber = Integer.parseInt(list.get(position).get("_buynumber").toString());
//						int sid = Integer.parseInt(list.get(position).get("_id").toString());
//						GoodsDBManager1.addbuynumber(sbuynumber + 1, sid);
//						Log.v("Test", String.valueOf(sbuynumber + 1));
//						
////						viewHolder.textview3.setText(sbuynumber + 1);
////						context.reloadadapter();
//						context.query();
//					}
//				});
//
//				viewHolder.Button2.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						int sbuynumber = Integer.parseInt(list.get(position)
//								.get("_buynumber").toString());
//						final int sid = Integer.parseInt(list.get(position)
//								.get("_id").toString());
//						if ("1".equals(String.valueOf(sbuynumber))) {
//							new AlertDialog.Builder(context)
//									.setTitle("Delete the meal?")
//									.setIcon(android.R.drawable.ic_dialog_info)
//
//									.setPositiveButton(
//											"No",
//											new DialogInterface.OnClickListener() {
//
//												@Override
//												public void onClick(
//														DialogInterface dialog,
//														int which) {
//													// 无需操作
//												}
//											})
//									.setNegativeButton(
//											"Yes",
//											new DialogInterface.OnClickListener() {
//
//												@Override
//												public void onClick(
//														DialogInterface dialog,
//														int which) {
//													GoodsDBManager1.delete(sid);
//													context.query();
//												}
//											}).show();
//						} else {
//							GoodsDBManager1.addbuynumber(sbuynumber - 1, sid);
//							context.query();
//						}
//					}
//				});
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return v;
//	}

	/***
	 * html图片替换
	 */
	ImageGetter imgGetter = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			Log.d("Image Path", source);
			URL url;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(), "");
			} catch (Exception e) {
				return null;
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}
	};
}
