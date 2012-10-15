package es.ivan.pampin.winroulette.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Iván García-Reiriz Pampín
 */
public final class Util
{
	public static final String WINROLETTE_ID = "es.ivan.pampin.winroulette";
	
	public static final Paint ANTIALIAS_PAINT = new Paint(Paint.FILTER_BITMAP_FLAG);
	
	/**
	 * Comprueba si hay conexion a internet
	 */
	public static boolean isNetworkAvaible(Context ctx)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = connectivityManager.getActiveNetworkInfo();
		return network != null && network.isAvailable() && network.isConnected();
	}
	
	/**
	 * @return Imagen cargada desde internet
	 */
	public static Bitmap loadImageFormNetwork(String url) throws IOException
	{
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
		return BitmapFactory.decodeStream(connection.getInputStream());
	}
}
