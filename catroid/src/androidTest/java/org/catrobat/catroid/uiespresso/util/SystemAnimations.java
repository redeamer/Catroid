/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.util;

import android.app.Instrumentation;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SystemAnimations {
	private static final String TAG = SystemAnimations.class.getSimpleName();

	private static final List<String> ANIMATION_PROPERTIES;
	static {
		ANIMATION_PROPERTIES = Collections.unmodifiableList(Arrays.asList(
				"window_animation_scale", "transition_animation_scale",
						"animator_duration_scale"));
	}
	private static final float DISABLED = 0.0f;
	private static final float DEFAULT = 1.0f;

	private final Instrumentation instrumentation;

	public SystemAnimations(final Instrumentation instrumentation) {
		this.instrumentation = instrumentation;
	}

	public void disableAll() {
		setSystemAnimationsScale(DISABLED);
	}

	public void enableAll() {
		setSystemAnimationsScale(DEFAULT);
	}

	private void setSystemAnimationsScale(float animationScale) {
		try {
			for (String property : ANIMATION_PROPERTIES) {
				setGlobalSetting(property, String.valueOf(animationScale));
			}
		} catch (Exception e) {
			Log.e(TAG, "Could not change animation scale to " + animationScale + " :'(");
		}
	}

	private void setGlobalSetting(final String key, final String value) {
		instrumentation.getUiAutomation()
				.executeShellCommand("settings put global " + key + " " + value);
	}
}
