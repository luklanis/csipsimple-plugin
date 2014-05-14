package ch.luklanis.csipsimple.taskerplugin;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {

    private static final String TAG = EditActivity.class.getName();
	private boolean isCancelled;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * A hack to prevent a private serializable classloader attack
         */
        BundleScrubber.scrub(getIntent());
        BundleScrubber.scrub(getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE));
        
        setContentView(R.layout.activity_edit);
        
        isCancelled = false;
        
        /*
         * if savedInstanceState is null, then then this is a new Activity instance and a check for EXTRA_BUNDLE is needed
         */
        if (null == savedInstanceState)
        {
            final Bundle forwardedBundle = getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);

            if (PluginBundleManager.isBundleValid(forwardedBundle))
            {
            	// PluginBundleManager.isBundleValid must be changed if elements are added to the bundle
            	// 
                ((EditText) findViewById(R.id.account_id)).setText(String.valueOf(forwardedBundle.getLong(PluginBundleManager.BUNDLE_EXTRA_INT_ACCOUNT_ID)));
                ((CheckBox) findViewById(R.id.account_deactivate)).setChecked(forwardedBundle.getBoolean(PluginBundleManager.BUNDLE_EXTRA_BOOL_DEACTIVATE));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /*
         * inflate the default menu layout from XML
         */
        getMenuInflater().inflate(R.menu.twofortyfouram_locale_help_save_dontsave, menu);
        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
        final int id = item.getItemId();

        if (id == R.id.twofortyfouram_locale_menu_help)
        {
//            try
//            {
//                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(HELP_URL)));
//            }
//            catch (final Exception e)
//            {
            Toast.makeText(getApplicationContext(), R.string.no_help_available, Toast.LENGTH_LONG).show();
//            }

            return true;
        }
        else if (id == R.id.twofortyfouram_locale_menu_dontsave)
        {
            isCancelled = true;
            finish();
            return true;
        }
        else if (id == R.id.twofortyfouram_locale_menu_save)
        {
            finish();
            return true;
        }

        return super.onMenuItemSelected(featureId, item);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public void finish()
    {
        if (isCancelled)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            final long account_id = Long.parseLong(((EditText) findViewById(R.id.account_id)).getText().toString());
            final boolean deactivate = ((CheckBox) findViewById(R.id.account_deactivate)).isChecked();

            /*
             * This is the result Intent to Locale
             */
            final Intent resultIntent = new Intent();

            /*
             * This extra is the data to ourselves: either for the Activity or the BroadcastReceiver. Note that anything
             * placed in this Bundle must be available to Locale's class loader. So storing String, int, and other standard
             * objects will work just fine. However Parcelable objects must also be Serializable. And Serializable objects
             * must be standard Java objects (e.g. a private subclass to this plug-in cannot be stored in the Bundle, as
             * Locale's classloader will not recognize it).
             */
            final Bundle resultBundle = new Bundle();
            resultBundle.putBoolean(PluginBundleManager.BUNDLE_EXTRA_BOOL_DEACTIVATE, deactivate);     
            resultBundle.putLong(PluginBundleManager.BUNDLE_EXTRA_INT_ACCOUNT_ID, account_id);
            
            Log.i(TAG, "Saved Bundle: " + resultBundle.toString());

            resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, resultBundle);
            
            // add text for display in tasker
            resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, 
            		String.format("%s account with id %d", deactivate ? " Deactivate" : "Activate", account_id));

            setResult(RESULT_OK, resultIntent);
        }

        super.finish();
    }
}
