package es.ivan.pampin.winroulette.moreapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import es.ivan.pampin.appad.Ad;
import es.ivan.pampin.winroulette.R;
import es.ivan.pampin.winroulette.util.Util;

/**
 * @author Iván García-Reiriz Pampín
 */
public class AppAdNovedadDialog extends AlertDialog.Builder {

	public AppAdNovedadDialog(final Context ctx, final Ad novedad) {
		super(ctx);
		
		setTitle(novedad.getName());
		
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.novedad_dialog, null);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.banner);
		try {
			imageView.setImageBitmap( Util.loadImageFormNetwork(novedad.getBanner()) );
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					String url = "market://details?id="+novedad.getId();
					Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					ctx.startActivity(market);
				}
			});
		}
		catch (Exception e) {
			Log.e("AppAds", e.getLocalizedMessage());
		}
		TextView descView = (TextView) view.findViewById(R.id.description);
		descView.setText(novedad.getDescription());
		
		setView(view);
		
		setPositiveButton(R.string.more_apps_small, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent moreAppsIntent = new Intent(ctx, MoreAppsActivity.class);
				ctx.startActivity(moreAppsIntent);
			}
		});
		setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	}
}
