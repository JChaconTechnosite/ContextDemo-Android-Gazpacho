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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ProfileManager implements SensorEventListener   {

	public interface ProfileManagerInterface {
		
		public void loadPage(String url);
		public void setBrightness(float value);
	public void startScreenReader(boolean value);
	}
	private Context context = null;
	private Viewer cViewer = null;
	private String lastUser = null;
	
	public ProfileManager (Context ct, Viewer cview) {
		super();
		context = ct;
		cViewer  = cview;
		initEnvironmentControl();
	}

	public boolean loadProfile(String user) {
		try {
			
			lastUser = user;
			if (lastUser.equalsIgnoreCase("abuela")) {
				environmentControl = true;
				setForOld();
				cViewer.loadPage("file:///sdcard/c4a/recipe/index_wb.html");
			} else
		if (lastUser.equalsIgnoreCase("hijo ")) {
			environmentControl = false;
			setForAll();
			cViewer.loadPage("file:///sdcard/c4a/recipe/index.html");
		} else 
		if (lastUser.equalsIgnoreCase("padre   ")) {
			environmentControl = false;
			setForBlind();
			cViewer.loadPage("file:///sdcard/c4a/recipe/index_bb.html");
		}
		else {
			// error
						environmentControl = false;
			setForAll();
			cViewer.loadPage("file:///sdcard/c4a/error/index.html");
		}
		
		Log.i("Profile manager", "User' " + user + "' profile is loaded");
		return true;
		} catch (Exception e) {
			Log.e("Profile manager error", "error: " +e);
			return false;
		}
	}

	
	// ** Settings
	
	private SettingsManager settings = new SettingsManager (context);
	
	private void setForOld() {
		cViewer.setBrightness(1f);
		cViewer.startScreenReader(false);
	}
	private void setForAll() {
		cViewer.setBrightness(0.2f);
		cViewer.startScreenReader(false);
	}
	
	private void setForBlind() {
		cViewer.setBrightness(0f);
		cViewer.startScreenReader(true);
	}
	
	// ** Environment
	 
	private boolean environmentControl = false;
private boolean hiContrast = false;
private float brightness = 0;
private SensorManager sensorManager = null;
private  Sensor brightnessSensor = null;

	private void initEnvironmentControl() {
		try {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		brightnessSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT );
		sensorManager.registerListener(this, brightnessSensor, SensorManager.SENSOR_DELAY_FASTEST);
		} catch (Exception e) {
			Log.e("Environment management error", "Error activating sensors. " +e);
		}
	}
	

	@Override public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_LIGHT){ // brightness sensor 
			brightness = event.values[0];
			if (environmentControl ) {
				if (brightness >1500 ) { 
					if (!hiContrast ) {
						hiContrast = true;
						cViewer.loadPage("file:///sdcard/c4a/recipe/index_bw.html");
					}
				} else {
					if (hiContrast ) {
						hiContrast = false;
						cViewer.loadPage("file:///sdcard/c4a/recipe/index_wb.html");
					}
				}
			}
						}
	}

	@Override public void onAccuracyChanged(Sensor sensor, int accuracy) {   
	}
	
}