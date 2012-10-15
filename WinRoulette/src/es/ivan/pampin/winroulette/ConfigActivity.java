package es.ivan.pampin.winroulette;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Preferencias de configuracion de la partida
 */
public class ConfigActivity extends PreferenceActivity
{
	public static final String UMBRAL_COLOR = "umbral_color";
	public static final String UMBRAL_PAR_IMPAR = "umbral_par_impar";
	public static final String UMBRAL_MITADES = "umbral_mitades";
	public static final String UMBRAL_DOCENAS = "umbral_docenas";
	public static final String UMBRAL_COLUMNAS = "umbral_columnas";
	public static final String UMBRAL_VECINOS = "umbral_vecinos";
	public static final String UMBRAL_TERCIO = "umbral_tercio";
	public static final String UMBRAL_HUERFANOS = "umbral_huerfanos";
	
	public static int umbralColor;
	public static int umbralParImpar;
	public static int umbralMitades;
	public static int umbralDocenas;
	public static int umbralColumnas;
	public static int umbralVecinos;
	public static int umbralTercio;
	public static int umbralHuerfanos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.configuracion);
	}
	
	public static void cargaPreferencias(Context ctx)
	{
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		umbralColor = p.getInt(ConfigActivity.UMBRAL_COLOR, 5);
		umbralParImpar = p.getInt(ConfigActivity.UMBRAL_PAR_IMPAR, 5);
		umbralMitades = p.getInt(ConfigActivity.UMBRAL_MITADES, 5);
		umbralDocenas = p.getInt(ConfigActivity.UMBRAL_DOCENAS, 5);
		umbralColumnas = p.getInt(ConfigActivity.UMBRAL_COLUMNAS, 5);
		umbralVecinos = p.getInt(ConfigActivity.UMBRAL_VECINOS, 5);
		umbralTercio = p.getInt(ConfigActivity.UMBRAL_TERCIO, 5);
		umbralHuerfanos = p.getInt(ConfigActivity.UMBRAL_HUERFANOS, 5);
	}
}
