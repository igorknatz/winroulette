package es.ivan.pampin.winroulette.moreapps;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;
import es.ivan.pampin.winroulette.util.Util;

/**
 * Utilidades para comprobar si hay novedades en appad
 * @author Iván García-Reiriz Pampín
 */
public class AppAdUtils
{
	private static final String FICHERO = "appad_novedades.txt";
	
	public static void showNovedades(Context ctx)
	{
		if (Util.isNetworkAvaible(ctx)) {
			long conexion = 0;
			try {
				conexion = getUltimaConexion(ctx);
				if (conexion > 0)
					new AppAdNovedadesTask(ctx).execute(conexion);
				saveUltimaConexion(ctx);
			}
			catch (Exception e) {
				Log.d("AppAd", "Error recuperando la ultima conexion: " + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * @return Ultima fecha almacenada
	 */
	public static long getUltimaConexion(Context ctx) throws Exception
	{
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader( ctx.openFileInput(FICHERO) ) );
			String tiempo = br.readLine();
			br.close();
			return tiempo == null || tiempo.trim().length()==0 ? 0 : Long.parseLong(tiempo);
		}
		catch (Exception e) {
			Log.i("AppAd", "Primer guardado de ultima conexion: " + e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Almacena la fecha actual como la de ultima conexion
	 */
	public static void saveUltimaConexion(Context ctx) throws Exception
	{
		FileOutputStream fos = ctx.openFileOutput(FICHERO, Context.MODE_PRIVATE);
		fos.write(String.valueOf(System.currentTimeMillis()).getBytes());
		fos.close();
	}
}
