/*

Context demo

	Copyright (c) 2013, Technosite R&D
	All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n¡ 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Ê* Redistributions of source code must retain the above copyright notice, thisÊlist of conditions and the following disclaimer. 
Ê* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
Ê* Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

This software has been developed for demonstration purposes.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
package com.technosite.contextdemo;

import android.content.Context;

public class SettingsManager {

	
	private Context ct = null;
	
	public SettingsManager(Context context) {
		ct = context;
		loadSettings();
	}
	
	public SettingBrightness brightness;
	public SettingVolume volume = null;
	public SettingAccessibility accessibility = null;
public SettingExecution execution = null;	

	public void loadSettings() {
		brightness = new SettingBrightness(ct);
		volume = new SettingVolume(ct);
		accessibility = new SettingAccessibility(ct);
		execution = new SettingExecution(ct); 
	}
	
	public void unloadSettings() {
		brightness = null;
		volume = null;
		accessibility = null;
		execution = null;
	}
	
	private int checkSettingItem(String name) {
		int result = 0; // Unknown parameter
		if (name.equalsIgnoreCase("Brightness")) {
			result = 1;
		} else if (name.equalsIgnoreCase("Accessibility")) {
			result = 2;
		}if (name.equalsIgnoreCase("Assistive tool")){
			result = 3;
		} else if (name.equalsIgnoreCase("Open")) {
			result = 4;
		} else if (name.equalsIgnoreCase("WebOpen")) {
			result = 5;
		}
		return result;
	}
	
	public boolean setParameter(String name, String value) {
		boolean result = false; // Change was not done
		int param = checkSettingItem(name); 
		if (param > 0) {
			switch (param) {
			case 1 : // brightness
				if (value.equalsIgnoreCase("automatic")) {
					brightness.setMode(0);
				} else if (value.equalsIgnoreCase("manual")) {
					brightness.setMode(1);
				} else {
					int intValue = Integer.valueOf(value);
					brightness.setValue(intValue);
				}
				break;
			case 2 : // accessibility
				break;
			case 3 : // AT
				break;
			case 4 : // Open
				execution.openApplication(value);
				break;
			case 5 : // open web
				execution.openWeb(value);
				break;
			}
		}
		return result;
	}
}
	