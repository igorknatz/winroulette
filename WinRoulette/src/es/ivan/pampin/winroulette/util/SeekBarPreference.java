package es.ivan.pampin.winroulette.util;

import java.text.MessageFormat;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Custom Preference. Muestra una barra tipo progreso para seleccionar un numero entero
 * @author Iván García
 */
public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener
{
	private static final String ANDROID_DNS = "http://schemas.android.com/apk/res/android";
	
	private SeekBar seekBar;
	private TextView valueText;

	private String suffix;
	private int mDefault;
	private int mMax;
	private int mValue = 0;

	public SeekBarPreference(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		suffix = attrs.getAttributeValue(ANDROID_DNS, "text");
		mDefault = attrs.getAttributeIntValue(ANDROID_DNS, "defaultValue", 0);
		mMax = attrs.getAttributeIntValue(ANDROID_DNS, "max", 100);
	}

	@Override
	protected View onCreateDialogView()
	{
		LinearLayout.LayoutParams params;
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(6, 6, 6, 6);

		valueText = new TextView(getContext());
		valueText.setGravity(Gravity.CENTER_HORIZONTAL);
		valueText.setTextSize(32);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.addView(valueText, params);

		seekBar = new SeekBar(getContext());
		seekBar.setOnSeekBarChangeListener(this);
		layout.addView(seekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		seekBar.setMax(mMax);

		if (shouldPersist())
			mValue = getPersistedInt(mDefault);

		seekBar.setProgress(mValue);
		return layout;
	}

	@Override
	protected void onBindDialogView(View v)
	{
		super.onBindDialogView(v);
		seekBar.setMax(mMax);
		seekBar.setProgress(mValue);
	}

	@Override
	protected void onSetInitialValue(boolean restore, Object defaultValue)
	{
		super.onSetInitialValue(restore, defaultValue);
		if (restore)
			mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
		else
			mValue = (Integer) defaultValue;
	}
	
	@Override
	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
	{
		mValue = value;
		String t = String.valueOf(mValue);
		valueText.setText(suffix == null ? t : t.concat(suffix));
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult)
	{
	    super.onDialogClosed(positiveResult);

	    if (!positiveResult) return;

	    if (shouldPersist()) persistInt(mValue);

	    notifyChanged();
	}
	
	@Override
	public CharSequence getSummary() {
		if (super.getSummary() == null)
			return null;
	    String summary = super.getSummary().toString();
	    int value = getPersistedInt(mDefault);
	    return MessageFormat.format(summary, value);
	}

	public void setMax(int max) {
		mMax = max;
	}

	public int getMax()	{
		return mMax;
	}

	public void setProgress(int progress)
	{
		mValue = progress;
		if (seekBar != null)
			seekBar.setProgress(progress);
	}

	public int getProgress()
	{
		return mValue;
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seek) {}

	@Override
	public void onStopTrackingTouch(SeekBar seek) {}
}

