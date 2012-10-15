package es.ivan.pampin.winroulette.moreapps;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import es.ivan.pampin.appad.Ad;
import es.ivan.pampin.appad.AppAd;

/**
 * Recupera las novedades en un hilo en segundo plano
 * @author Iván García-Reiriz Pampín
 */
public class AppAdNovedadesTask extends AsyncTask<Long, Void, Ad>
{
	private Context ctx;
	
	public AppAdNovedadesTask(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	protected Ad doInBackground(Long... params) {
		try {
			List<Ad> novedades = AppAd.getNovedades(params[0]);
			if (!novedades.isEmpty())
				return novedades.get(0);
		}
		catch (Exception e) {
			Log.d("AppAd", "Error recuperando las novedades: " + e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	protected void onPostExecute(Ad result) {
		if (result != null)
			new AppAdNovedadDialog(ctx, result).show();
	}
}
