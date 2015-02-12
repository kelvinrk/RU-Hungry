package com.example.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.myfood.R;

public class ImageLoader {
	private static final String TAG = "ImageLoader";
	private static final int MAX_CAPACITY = 1000;// Cache max Capacity
	private static final long DELAY_BEFORE_PURGE = 300 * 1000;// Clear interval

	// 0.75 is load factor，true is sorted by access order，false is insert order
	public static HashMap<String, Bitmap> mFirstLevelCache = new LinkedHashMap<String, Bitmap>(
			MAX_CAPACITY / 2, 0.75f, true) {
		private static final long serialVersionUID = 1L;

		protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
			if (size() > MAX_CAPACITY) {// When size over capacity it will move to second level cache
				mSecondLevelCache.put(eldest.getKey(),
						new SoftReference<Bitmap>(eldest.getValue()));
				return true;
			}
			return false;
		};
	};
	// second level cache，using soft reference，it will be recycled by gc when memory exhausted
	public static ConcurrentHashMap<String, SoftReference<Bitmap>> mSecondLevelCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
			MAX_CAPACITY / 2);

	// Clear Cache
	private Runnable mClearCache = new Runnable() {
		@Override
		public void run() {
			clear();
		}
	};
	private Handler mPurgeHandler = new Handler();

	// Reset clear timer
	private void resetPurgeTimer() {
		mPurgeHandler.removeCallbacks(mClearCache);
		mPurgeHandler.postDelayed(mClearCache, DELAY_BEFORE_PURGE);
	}

	/**
	 * Clear Cache
	 */
	private void clear() {
		mFirstLevelCache.clear();
		mSecondLevelCache.clear();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		bitmap = getFromFirstLevelCache(url);// look up first level cache
		if (bitmap != null) {
			return bitmap;
		}
		bitmap = getFromSecondLevelCache(url);// look up second level cache
		return bitmap;
	}

	/**
	 * Take from second level cache
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getFromSecondLevelCache(String url) {
		Bitmap bitmap = null;
		SoftReference<Bitmap> softReference = mSecondLevelCache.get(url);
		if (softReference != null) {
			bitmap = softReference.get();
			if (bitmap == null) {// Due to memory exhausted, the soft reference is recycled by the garbage collector
				mSecondLevelCache.remove(url);
			}
		}
		return bitmap;
	}

	/**
	 * Take from first level cache
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getFromFirstLevelCache(String url) {
		Bitmap bitmap = null;
		synchronized (mFirstLevelCache) {
			bitmap = mFirstLevelCache.get(url);
			if (bitmap != null) {
//				mFirstLevelCache.remove(url);
//				mFirstLevelCache.put(url, bitmap);
			}
		}
		return bitmap;
	}

	/**
	 * load image, look up cache first, if miss the download.
	 * 
	 * @param url
	 * @param adapter
	 * @param holder
	 */
	public void articleloadImage(String url, BaseAdapter adapter,
			ArticleAdapter.ViewHolder holder) {
		resetPurgeTimer();
		Bitmap bitmap = getBitmapFromCache(url);// read from cache
		if (bitmap == null) {
			holder.mImageView.setImageResource(R.drawable.ic_launcher);// set to default
			ImageLoadTask imageLoadTask = new ImageLoadTask();
			imageLoadTask.execute(url, adapter, holder);
		} else {
			holder.mImageView.setImageBitmap(bitmap);// set to cache image
		}

	}

	/**
	 * 
	 * load image, look up cache first, if miss the download.
	 * @param url
	 * @param adapter
	 * @param holder
	 */
	public void goodsloadImage(String url, BaseAdapter adapter,
			MenuAdapter.goodsViewHolder holder) {
		resetPurgeTimer();
		Bitmap bitmap = getBitmapFromCache(url);// read from cache
		if (bitmap == null) {
			holder.mImageView.setImageResource(R.drawable.ic_launcher);// set to default
			ImageLoadTask imageLoadTask = new ImageLoadTask();
			imageLoadTask.execute(url, adapter, holder);
		} else {
			holder.mImageView.setImageBitmap(bitmap);// set to cache image
		}

	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap bloadImage(String url) {
		// resetPurgeTimer();
		Bitmap bitmap = loadImageFromInternet(url);// download from Internet
		return bitmap;

	}

	/**
	 * load image, look up cache first, if miss the download.
	 * 
	 * @param url
	 * @param adapter
	 * @param holder
	 */
//	public void dingloadImage(String url, SimpleAdapter adapter,
//			dingAdapter.dingViewHolder holder) {
//		resetPurgeTimer();
//		Bitmap bitmap = getBitmapFromCache(url);// read from cache
//		if (bitmap == null) {
//			holder.mImageView.setImageResource(R.drawable.ic_launcher);// set to default
//			ImageLoadTask imageLoadTask = new ImageLoadTask();
//			imageLoadTask.execute(url, adapter, holder);
//		} else {
//			holder.mImageView.setImageBitmap(bitmap);// set to cache image
//		}
//
//	}
	
	public void dingloadImage(String url, OrderAdapter dingAdapter,
			ImageView mImageView) {
		resetPurgeTimer();
		Bitmap bitmap = getBitmapFromCache(url);// 从缓存中读取
		if (bitmap == null) {
			mImageView.setImageResource(R.drawable.ic_launcher);// 缓存没有设为默认图片
			ImageLoadTask imageLoadTask = new ImageLoadTask();
			imageLoadTask.execute(url, dingAdapter, mImageView);
		} else {
			mImageView.setImageBitmap(bitmap);// 设为缓存图片
		}

	}

	/**
	 * load image, look up cache first, if miss the download.
	 * 
	 * @param url
	 * @param adapter
	 * @param holder
	 */
	public void categoryloadImage(String url, BaseAdapter adapter,
			RestaurantAdapter.categoryViewHolder holder) {
		resetPurgeTimer();
		Bitmap bitmap = getBitmapFromCache(url);// read from cache
		if (bitmap == null) {
			holder.mImageView.setImageResource(R.drawable.ic_launcher);// set to default
			ImageLoadTask imageLoadTask = new ImageLoadTask();
			imageLoadTask.execute(url, adapter, holder);
		} else {
			holder.mImageView.setImageBitmap(bitmap);// set to cache image
		}

	}

	/**
	 * put into cache
	 * 
	 * @param url
	 * @param value
	 */
	public void addImage2Cache(String url, Bitmap value) {
		if (value == null || url == null) {
			return;
		}
		synchronized (mFirstLevelCache) {
			mFirstLevelCache.put(url, value);
		}
	}

	class ImageLoadTask extends AsyncTask<Object, Void, Bitmap> {
		String url;
		BaseAdapter adapter;

		@Override
		protected Bitmap doInBackground(Object... params) {
			url = (String) params[0];
			adapter = (BaseAdapter) params[1];
			Bitmap drawable = loadImageFromInternet(url);// download from Internet
			return drawable;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result == null) {
				return;
			}
		addImage2Cache(url, result);// put into cache
			adapter.notifyDataSetChanged();
		}
	}

	public Bitmap loadImageFromInternet(String url) {
		Bitmap bitmap = null;
		HttpClient client = AndroidHttpClient.newInstance("Android");
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 50000);
		HttpConnectionParams.setSocketBufferSize(params, 50000);
		HttpResponse response = null;
		InputStream inputStream = null;
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			response = client.execute(httpGet);
			int stateCode = response.getStatusLine().getStatusCode();
			if (stateCode != HttpStatus.SC_OK) {
				return bitmap;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					inputStream = entity.getContent();
					return bitmap = BitmapFactory.decodeStream(inputStream);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (ClientProtocolException e) {
			httpGet.abort();
			e.printStackTrace();
		} catch (IOException e) {
			httpGet.abort();
			e.printStackTrace();
		} finally {
			((AndroidHttpClient) client).close();
		}
		return bitmap;
	}

}
