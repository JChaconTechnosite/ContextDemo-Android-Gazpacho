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
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;


public class SettingBrightness extends SettingItem  {

	private Context ct = null;
	public SettingBrightness(Context context) {
		name = "Brightness";
		type = TypeOfSetting.BRIGHTNESS;
		rootSupportRequired = false;
		ct = context;
	}
	
	// Automation management
	
	
	public void setMode(int value) {
		try {
		SharedPreferences prefs = ct.getSharedPreferences("SystemSettingsPreferences", Context.MODE_PRIVATE);
		int finalValue = value;
		if (value<0 || value>1) finalValue = 0;
		prefs.edit().putInt("SCREEN_BRIGHTNESS_MODE", finalValue);
		prefs.edit().commit();
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error changing Screen brightness mode. " +e);
		}
	}
	
	public int getMode() {
		int result = -1;
		try {
		result = Settings.System.getInt(ct.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error getting screen brightness mode. " +e);
		}
		return result;
	}
	
	// Brightness level management
			
	public int getValue() {
	int result = -1;
	try {
	result = Settings.System.getInt(ct.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
	} catch (Exception e) {
		Log.e("SettingHandler error", "Error getting brightness level. " +e);
	}
	
		return result;
	}
	
	public void setValue(int value) {
		try {
		SharedPreferences prefs = ct.getSharedPreferences("SystemSettingsPreferences", Context.MODE_PRIVATE);
		int finalValue = value;

		if (value<0) finalValue = 0;
		if (value>255) finalValue=255;
		prefs.edit().putInt("SCREEN_BRIGHTNESS_MODE", 1);
		prefs.edit().putInt("SCREEN_BRIGHTNESS", finalValue);
		prefs.edit().commit();
		
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error changing brightness screen. " +e);
		}
	}
	
}
