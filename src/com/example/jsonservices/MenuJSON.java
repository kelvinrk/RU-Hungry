package com.example.jsonservices;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.util.Log;

import com.example.model.Restaurant;
import com.example.model.Meal;
import com.example.utils.StreamTool;

public class MenuJSON {

	public static List<Meal> getjsonlastgoods(String url)
			throws Exception {
//		String path = url;
//		HttpURLConnection conn = (HttpURLConnection) new URL(path)
//				.openConnection();
//		conn.setConnectTimeout(50000);
//		conn.setRequestMethod("GET");
//		if (conn.getResponseCode() == 200) {
//			InputStream json = conn.getInputStream();
//			return parsejson(json);
//		}
//		return null;

		String response = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;

			HttpGet httpGet = new HttpGet(url);

			httpResponse = httpClient.execute(httpGet);

			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

			Log.v("JSON", response.toString());

			return parseResponse(response);

		} catch (Exception e) {
			Log.v("JSON", "Error");
			e.printStackTrace();
		}
		Log.v("JSON", "null");
		return null;

	}

	private static List<Meal> parseResponse(String response) {
		Log.v("JSON", "parseResponse");
		List<Meal> list = new ArrayList<Meal>();

		try {

			JSONObject temp = new JSONObject(response);
			Log.v("JSON", "got temp");
			JSONArray jsonarray = temp.getJSONArray("Goods");

			Log.v("JSON", "got good");

			for (int i = 0; i < jsonarray.length(); i++) {
				Log.v("JSON", "parsing");
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				int _id = jsonobject.getInt("id");
				int _channel_id = jsonobject.getInt("channel_id");
				int _category_id = jsonobject.getInt("category_id");
				String _title = jsonobject.getString("title");
				String _goods_no = jsonobject.getString("goods_no");
				int _stock_quantity = jsonobject.getInt("stock_quantity");
				String _market_price = jsonobject.getString("market_price");
				String _sell_price = jsonobject.getString("sell_price");
				int _point = jsonobject.getInt("point");
				String _link_url = jsonobject.getString("link_url");
				String _img_url = jsonobject.getString("img_url");
				String _seo_title = jsonobject.getString("seo_title");
				String _seo_keywords = jsonobject.getString("seo_keywords");
				String _seo_description = jsonobject
						.getString("seo_description");
				String _content = jsonobject.getString("content");
				int _sort_id = jsonobject.getInt("sort_id");
				int _click = jsonobject.getInt("click");
				int _is_msg = jsonobject.getInt("is_msg");
				int _is_top = jsonobject.getInt("is_top");
				int _is_red = jsonobject.getInt("is_red");
				int _is_hot = jsonobject.getInt("is_hot");
				int _is_slide = jsonobject.getInt("is_slide");
				int _is_lock = jsonobject.getInt("is_lock");
				int _user_id = jsonobject.getInt("user_id");
				String _add_time = jsonobject.getString("add_time");
				int _digg_good = jsonobject.getInt("digg_good");
				int _digg_bad = jsonobject.getInt("digg_bad");
				list.add(new Meal(_id, _channel_id, _category_id, _title,
						_goods_no, _stock_quantity, _market_price, _sell_price,
						_point, _link_url, _img_url, _seo_title, _seo_keywords,
						_seo_description, _content, _sort_id, _click, _is_msg,
						_is_top, _is_red, _is_hot, _is_slide, _is_lock,
						_user_id, _add_time, _digg_good, _digg_bad));
			}
			Log.v("JSON", "return list");
			return list;

		} catch (Exception e) {
			Log.v("JSON", "return Exception");
			e.printStackTrace();
			return list;
		}
	}
	
	
	private static List<Meal> parsejson(InputStream jsonStream)
			throws Exception {
		List<Meal> list = new ArrayList<Meal>();
		byte[] data = StreamTool.read(jsonStream);
		String json = new String(data);
		JSONArray jsonarray = new JSONArray(json);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			int _id = jsonobject.getInt("id");
			int _channel_id = jsonobject.getInt("channel_id");
			int _category_id = jsonobject.getInt("category_id");
			String _title = jsonobject.getString("title");
			String _goods_no = jsonobject.getString("goods_no");
			int _stock_quantity = jsonobject.getInt("stock_quantity");
			String _market_price = jsonobject.getString("market_price");
			String _sell_price = jsonobject.getString("sell_price");
			int _point = jsonobject.getInt("point");
			String _link_url = jsonobject.getString("link_url");
			String _img_url = jsonobject.getString("img_url");
			String _seo_title = jsonobject.getString("seo_title");
			String _seo_keywords = jsonobject.getString("seo_keywords");
			String _seo_description = jsonobject.getString("seo_description");
			String _content = jsonobject.getString("content");
			int _sort_id = jsonobject.getInt("sort_id");
			int _click = jsonobject.getInt("click");
			int _is_msg = jsonobject.getInt("is_msg");
			int _is_top = jsonobject.getInt("is_top");
			int _is_red = jsonobject.getInt("is_red");
			int _is_hot = jsonobject.getInt("is_hot");
			int _is_slide = jsonobject.getInt("is_slide");
			int _is_lock = jsonobject.getInt("is_lock");
			int _user_id = jsonobject.getInt("user_id");
			String _add_time = jsonobject.getString("add_time");
			int _digg_good = jsonobject.getInt("digg_good");
			int _digg_bad = jsonobject.getInt("digg_bad");
			list.add(new Meal(_id, _channel_id, _category_id, _title,
					_goods_no, _stock_quantity, _market_price, _sell_price,
					_point, _link_url, _img_url, _seo_title, _seo_keywords,
					_seo_description, _content, _sort_id, _click, _is_msg,
					_is_top, _is_red, _is_hot, _is_slide, _is_lock, _user_id,
					_add_time, _digg_good, _digg_bad));
		}
		return list;
	}
}
