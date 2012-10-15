package es.ivan.pampin.winroulette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import es.ivan.pampin.winroulette.moreapps.AppAdUtils;
import es.ivan.pampin.winroulette.moreapps.MoreAppsActivity;

public class MainActivity extends Activity
{
	private EditText visor;
	private LinearLayout lastNumbersView;
	private WinRoulette roulette;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AppAdUtils.showNovedades(this);
        
        setContentView(R.layout.activity_main);
        
        PreferenceManager.setDefaultValues(this, R.xml.configuracion, false);
        
        visor = (EditText)findViewById(R.id.editNumber);
        visor.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE)
					okNumberClickHandler(null);
				return false;
			}
		});
        
        lastNumbersView = (LinearLayout)findViewById(R.id.lastNumbers);
        registerForContextMenu(lastNumbersView);
        
        //permite recuperar el estado tras un giro de pantalla
        Object conf = getLastNonConfigurationInstance();
        roulette = conf == null ? new WinRoulette() : (WinRoulette)conf;
        if (conf != null) {
        	updateContadores();
        	for (Integer num : roulette.numbers)
        		addNumberToLastView(num);
        }
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		ConfigActivity.cargaPreferencias(this);
		updateContadores();
	}



	/**
     * Almacena el estado de la app
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
    	return roulette;
    }
    
    /**
     * Crea el menu de opciones de la aplicacion
     */
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	/**
	 * Invoca la llamada a las opciones de menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.menu_config:
				Intent configActivity = new Intent(this, ConfigActivity.class);
				startActivity(configActivity);
				return true;
			case R.id.menu_moreApps:
				Intent moreAppsIntent = new Intent(this, MoreAppsActivity.class);
				startActivity(moreAppsIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Crea el menu contextual
	 */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(ContextMenu.NONE, ContextMenu.NONE, 0, R.string.delete_last_number);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	if (item.getItemId() == ContextMenu.NONE) {
    		roulette.deleteLastNumber();
    		lastNumbersView.removeViewAt(0);
    		updateContadores();
    	}
    	return super.onContextItemSelected(item);
    }
    
    
    
    private boolean isValidNumber()
    {
    	try {
        	Integer num = Integer.valueOf(visor.getText().toString());
        	return num <= 36;
		} catch (Exception e) {
			return false;
		}
	}
    
    public void enterNumberClickHandler(View view)
    {
    	Button button = (Button)view;
    	Integer num = Integer.valueOf(button.getText().toString());

		visor.append(num.toString());
    }
    
    public void deleteNumberClickHandler(View view)
    {
    	visor.setText("");
    }
    
    public void okNumberClickHandler(View view)
    {
    	if (isValidNumber()) {
    		Integer num = Integer.valueOf(visor.getText().toString());
    		addNumberToLastView(num);
	    	roulette.addNumber(num);
	    	updateContadores();
    	}
    	visor.setText("");
    }
    
    private void addNumberToLastView(Integer num)
    {
    	TextView textView = new TextView(this);
    	textView.setTextColor(WinRoulette.getColor(num));
    	textView.setPadding(0, 0, 15, 10);
    	textView.setText(num.toString());
    	
    	lastNumbersView.addView(textView, 0);
    }
    
    private void updateContadores()
    {
    	updateContador(R.id.textoRojo, roulette.getVecesRojo(), ConfigActivity.umbralColor);
    	updateContador(R.id.textoNegro, roulette.getVecesNegro(), ConfigActivity.umbralColor);
    	
    	updateContador(R.id.textoPar, roulette.getVecesPar(), ConfigActivity.umbralParImpar);
    	updateContador(R.id.textoImpar, roulette.getVecesImpar(), ConfigActivity.umbralParImpar);

    	updateContador(R.id.textoMitad1, roulette.getVecesPrimeraMitad(), ConfigActivity.umbralMitades);
    	updateContador(R.id.textoMitad2, roulette.getVecesSegundaMitad(), ConfigActivity.umbralMitades);
    	
    	updateContador(R.id.textoVecinos, roulette.getVecesVecinos(), ConfigActivity.umbralVecinos);
    	updateContador(R.id.textoTercio, roulette.getVecesTercio(), ConfigActivity.umbralTercio);
    	updateContador(R.id.textoHuerfanos, roulette.getVecesHuerfanos(), ConfigActivity.umbralHuerfanos);
    	
    	updateContador(R.id.textoDocena1, roulette.getVecesPrimeraDocena(), ConfigActivity.umbralDocenas);
    	updateContador(R.id.textoDocena2, roulette.getVecesSegundaDocena(), ConfigActivity.umbralDocenas);
    	updateContador(R.id.textoDocena3, roulette.getVecesTerceraDocena(), ConfigActivity.umbralDocenas);
    	
    	updateContador(R.id.texto1col, roulette.getVecesPrimeraColumna(), ConfigActivity.umbralColumnas);
    	updateContador(R.id.texto2col, roulette.getVecesSegundaColumna(), ConfigActivity.umbralColumnas);
    	updateContador(R.id.texto3col, roulette.getVecesTerceraColumna(), ConfigActivity.umbralColumnas);
    }
    
    private void updateContador(int id, Integer veces, int umbral)
    {
    	TextView texto = (TextView)findViewById(id);
    	texto.setText(veces.toString());
    	if (veces == umbral-1)
    		texto.setTextColor(Color.YELLOW);
    	else if (veces >= umbral)
    		texto.setTextColor(Color.RED);
    	else
    		texto.setTextColor(Color.WHITE);
    }
    
    public void jugadasVecinosHandler(View view)
    {
    	Intent imageActivity = new Intent(this, ImageActivity.class);
		startActivity(imageActivity);
    }
}
