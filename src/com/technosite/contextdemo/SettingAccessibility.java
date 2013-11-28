/*

Context demo

	Copyright (c) 2013, Technosite R&D
	All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

This software has been developed for demonstration purposes.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
package com.technosite.contextdemo;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class SettingAccessibility extends SettingItem  {

	private Context ct = null;
	
	public SettingAccessibility(Context contextParam) {
		name = "Accessibility";
		type = TypeOfSetting.ACCESSIBILITY;
		rootSupportRequired = true;
		ct = contextParam;
	}
	
	
	
	public boolean setAccessibilitySupport(boolean value) {
		int e = 0;
		if (value) e = 1;
		try {
		Settings.Secure.putInt(ct.getContentResolver(),
				Settings.Secure.ACCESSIBILITY_ENABLED, e);
		return true;
		} catch (Exception err) {
			Log.e("SettingHandler accessibility", "Error changing accessibility support. " +err);
			return false;
		}
	}

	public boolean addAccessibilityService(String nameService) {
		try {
			String enableSer = Settings.Secure.getString(
					ct.getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (!enableSer.contains(nameService)) {
								Settings.Secure.putInt(ct.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); 
																					Settings.Secure.putString(ct.getContentResolver(),
								Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
								nameService); 
				Settings.Secure.putInt(ct.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); 
							}
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error activating accessibility service. " +e);
			return false;
		}
	}

	public boolean removeAccessibilityService(String nameService) {
		try {
			String enableSer = Settings.Secure.getString(ct.getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (enableSer.contains(nameService)) { 
				Settings.Secure.putInt(ct.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 0);
																									Settings.Secure.putString(ct.getContentResolver(),
						Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, ""); 
																												} else {
								Settings.Secure.putInt(ct.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); 
				Settings.Secure.putString(ct.getContentResolver(),
								Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
								"<AccessibilityService Package>/<AccessivilityService Name>"); 
				
				Settings.Secure.putInt(ct.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); 
																								}

			return true;
		} catch (Exception e) {
			Log.e("SettingHandler", "Error removing accessibility service. " +e);
			return false;
		}
	}

	public boolean setTouchExploration(boolean value) {
		try {

			if (Settings.Secure.getString(ct.getContentResolver(),
					Settings.Secure.ACCESSIBILITY_ENABLED).contains("1")) {
								if (value == false) { 
											Settings.Secure.putInt(ct.getContentResolver(),
							Settings.Secure.TOUCH_EXPLORATION_ENABLED, 0);
				} else {
					Settings.Secure.putInt(ct.getContentResolver(),
							Settings.Secure.TOUCH_EXPLORATION_ENABLED, 1);
				}
			}
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error managing Touch explore service. " +e);
			return false;
		}
	}

	
	

}
