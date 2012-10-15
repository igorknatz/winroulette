package es.ivan.pampin.winroulette.moreapps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import es.ivan.pampin.appad.Ad;
import es.ivan.pampin.winroulette.R;
import es.ivan.pampin.winroulette.util.Util;

/**
 * @author Iván García-Reiriz Pampín
 */
public class AdAdapter extends ArrayAdapter<Ad>
{
	private List<Ad> ads;
	private Context ctx;
	private int ancho;
	
	private static Map<String,Bitmap> banners;

	public AdAdapter(Activity ctx, List<Ad> ads) {
		super(ctx, R.layout.ad_adapter, ads);
		this.ctx = ctx;
		this.ads = ads;
		
		banners = new HashMap<String, Bitmap>();
		
		DisplayMetrics metrics = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		ancho = metrics.widthPixels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ad_adapter, parent, false);
		
		Ad ad = ads.get(position);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.banner);
		if (banners.containsKey(ad.getId()))
			imageView.setImageBitmap(banners.get(ad.getId()));
		else
			new LoadImageTask(imageView).execute(ad);
		
		if (ancho > 400) {
			TextView descView = (TextView) rowView.findViewById(R.id.description);
			descView.setText(ad.getDescription());
		}
		else
			((LinearLayout)rowView).setGravity(Gravity.CENTER_HORIZONTAL);

		return rowView;
	}
	
	protected class LoadImageTask extends AsyncTask<Ad, Void, Bitmap>
	{
		private ImageView imageView;
		private Ad ad;
		
		public LoadImageTask(ImageView view) {
			imageView = view;
		}
		
		@Override
		protected Bitmap doInBackground(Ad... ad) {
			try {
				this.ad = ad[0];
				return Util.loadImageFormNetwork(ad[0].getBanner());
			}
			catch (IOException e) {}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				banners.put(ad.getId(), result);
				imageView.setImageBitmap(result);
			}
		}
	}
}
