package es.ivan.pampin.winroulette.moreapps;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import es.ivan.pampin.appad.Ad;
import es.ivan.pampin.appad.AppAd;
import es.ivan.pampin.winroulette.R;
import es.ivan.pampin.winroulette.util.Util;

public class MoreAppsActivity extends Activity implements OnItemClickListener
{
	private ProgressDialog dialog;
	private static List<Ad> ads;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.moreapps);
		
		if (ads != null)
			muestraApps();
		else
		{
			dialog = ProgressDialog.show(MoreAppsActivity.this, "", getResources().getText(R.string.cargando_apps), true, true, new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					muestraAppsError();
				}
			});
			dialog.show();
			
			new MoreAppsTask().execute();
		}
	}
	
	public void muestraApps()
	{
		ListView listView = (ListView)findViewById(R.id.adsListView);
		listView.setAdapter(new AdAdapter(this, ads));
		listView.setOnItemClickListener(this);
	}
	
	public void muestraAppsError()
	{
		Toast.makeText(MoreAppsActivity.this, R.string.error_conexion, Toast.LENGTH_LONG).show();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String url = "market://details?id="+ads.get(position).getId();
		Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(market);
	}
	
	public class MoreAppsTask extends AsyncTask<Void, Void, List<Ad>>
	{
		@Override
		protected List<Ad> doInBackground(Void... params)
		{
			if (Util.isNetworkAvaible(MoreAppsActivity.this))
				try {
					return AppAd.getAds(Util.WINROLETTE_ID);
				}
				catch (Exception e) {
					Log.e("Damitario", "Error al recuperar moreapps: " + e.getMessage(), e);
				}
			return null;
		}

		@Override
		protected void onPostExecute(List<Ad> result) {
			if (result != null) {
				ads = result;
				muestraApps();
			}
			else
				muestraAppsError();
			dialog.dismiss();
		}
	}
}
