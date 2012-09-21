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

import android.os.Bundle;

/**
 * Class for managing the {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} for this plug-in.
 */
public final class PluginBundleManager
{
	/**
	 * Private constructor prevents instantiation
	 * 
	 * @throws UnsupportedOperationException because this class cannot be instantiated.
	 */
	private PluginBundleManager()
	{
		throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
	}

	/**
	 * Type: {@code String}
	 * <p>
	 * String message to display in a Toast message.
	 */
	public static final String BUNDLE_EXTRA_INT_ACCOUNT_ID = PluginBundleManager.class.getPackage().getName() + ".ACCOUNT_ID"; 
	public static final String BUNDLE_EXTRA_BOOL_DEACTIVATE = PluginBundleManager.class.getPackage().getName() + ".DEACTIVATE";

	/**
	 * Method to verify the content of the bundle are correct.
	 * <p>
	 * This method will not mutate {@code bundle}.
	 * 
	 * @param bundle bundle to verify. May be null, which will always return false.
	 * @return true if the Bundle is valid, false if the bundle is invalid.
	 */
	public static boolean isBundleValid(final Bundle bundle)
	{
		if (null == bundle)
		{
			return false;
		}

		/*
		 * Make sure the expected extras exist
		 */
		if (!bundle.containsKey(BUNDLE_EXTRA_INT_ACCOUNT_ID))
		{
			return false;
		}

		if (!bundle.containsKey(BUNDLE_EXTRA_BOOL_DEACTIVATE))
		{
			return false;
		}

		return true;
	}
}