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
import android.media.AudioManager;
import android.util.Log;

public class SettingVolume extends SettingItem {

	private Context ct = null;
	private AudioManager audMng;
	
	public SettingVolume(Context context) {
		name = "Volume";
		type = TypeOfSetting.VOLUME;
		rootSupportRequired = false;
		ct = context;
	}

	// ** Notifications events
	public boolean setNotificationVolume(int value) {
		try {
			int volume = value;
			if (volume <0) volume = 0;
			if (volume >7 ) volume = 7;
			audMng.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting notification volume. " +e);
			return false;
		}
	}

	// ** Music
	
	public boolean setMusicVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume=0;
			if (volume>15) volume=15;
			audMng.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting music volume. " +e);
			return false;
		}
	}

	// ** Alarm
	
	public boolean setAlarmVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume=0;
			if (volume>7) volume=7;
			audMng.setStreamVolume(AudioManager.STREAM_ALARM, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting alarm volume. " +e);
			return false;
		}
	}

	// ** DTMF
	
	public boolean setDTMFVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume = 0;
			if (volume>15) volume = 15;
			audMng.setStreamVolume(AudioManager.STREAM_DTMF, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting DTMF volume. " +e);
			return false;
		}
	}

	// ** Ring
	public boolean setRingVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume=0;
			if (volume>7) volume=7;
			audMng.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting ring volume. " +e);
			return false;
		}
	}

	// ** System
	
	public boolean setSystemVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume=0;
			if (volume>7) volume=7;
			audMng.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting system volume. " +e);
			return false;
		}
	}

	// ** VoiceCall
	
	public boolean setVoiceCallVolume(int value) {
		try {
			int volume = value;
			if (volume<0) volume=0;
			if (volume>7) volume=7;
			audMng.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, 0);
			return true;
		} catch (Exception e) {
			Log.e("SettingHandler error", "Error setting VoiceCall volume. " +e);
			return false;
		}
	}

	
	
	
	
}
