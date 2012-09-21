/*
 * Copyright (C) 2012 asksven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This file was contributed by two forty four a.m. LLC <http://www.twofortyfouram.com>
 * unter the terms of the Apache License, Version 2.0
 */

package ch.luklanis.csipsimple.taskerplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * This is the "fire" BroadcastReceiver for a Locale Plug-in setting.
 */
public final class FireReceiver extends BroadcastReceiver
{

	private static final String TAG = "FireReceiver";

	/**
	 * @param context {@inheritDoc}.
	 * @param intent the incoming {@link com.twofortyfouram.locale.Intent#ACTION_FIRE_SETTING} Intent. This should contain the
	 *            {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} that was saved by {@link EditActivity} and later broadcast
	 *            by Locale.
	 */
	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		/*
		 * Always be sure to be strict on input parameters! A malicious third-party app could always send an empty or otherwise
		 * malformed Intent. And since Locale applies settings in the background, the plug-in definitely shouldn't crash in the
		 * background.
		 */

		/*
		 * Locale guarantees that the Intent action will be ACTION_FIRE_SETTING
		 */
		if (!com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction()))
		{
			return;
		}

		/*
		 * A hack to prevent a private serializable classloader attack
		 */
		BundleScrubber.scrub(intent);
		BundleScrubber.scrub(intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE));

		final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);

		boolean deactivate = bundle.getBoolean(PluginBundleManager.BUNDLE_EXTRA_BOOL_DEACTIVATE);

		long account_id = bundle.getLong(PluginBundleManager.BUNDLE_EXTRA_INT_ACCOUNT_ID);

		Log.i(TAG, "Retrieved Bundle: " + bundle.toString());

		Log.d(TAG, "Preparing to save a custom ref");

		Intent broadcastIntent = new Intent("com.csipsimple.accounts.activate");
		broadcastIntent.putExtra("id", account_id);
		broadcastIntent.putExtra("active", !deactivate);
		context.sendBroadcast(broadcastIntent);
	}
}