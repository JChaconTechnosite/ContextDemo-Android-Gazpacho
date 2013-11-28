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

 
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.technosite.contextdemo.ProfileManager.ProfileManagerInterface;

public class Viewer extends Activity implements ProfileManagerInterface {
	
	private WebView monitor = null;
	private TextView alternative = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewer);
		monitor = (WebView) findViewById(R.id.webview);
		alternative = (TextView) findViewById(R.id.alternative);
		profileManager = new ProfileManager (this, this);
		manageNfcIntent(getIntent());		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewer, menu);
		return true;
	}

	private Handler mHandler = new Handler();
	private void closeAfterSeconds(int timeToCounter) {
		mHandler.removeCallbacks(timerEvent);
		mHandler.postDelayed(timerEvent, timeToCounter * 1000);
		
        	}
	
	private void endTimer() {
		mHandler.removeCallbacks(timerEvent);
		finish();
	}

	private Runnable timerEvent = new Runnable() {
		public void run() {
			endTimer();
		}
	};

    private void playSoundNotification() {
    	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    	builder.setSound(soundUri );
    	notificationManager.notify(1234, builder.build());
    }

    public String getTextFromNFCIntent(Intent intent){
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);

        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        

        try
        {
                byte[] payload = msg.getRecords()[0].getPayload();

                /*
             * payload[0] contains the "Status Byte Encodings" field, per the
             * NFC Forum "Text Record Type Definition" section 3.2.1.
             *
             * bit7 is the Text Encoding Field.
             *
             * if (Bit_7 == 0): The text is encoded in UTF-8 if (Bit_7 == 1):
             * The text is encoded in UTF16
             *
             * Bit_6 is reserved for future use and must be set to zero.
             *
             * Bits 5 to 0 are the length of the IANA language code.
             */

                 //Get the Text Encoding
                String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";

                //Get the Language Code
                int languageCodeLength = payload[0] & 0077;
                // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

                //Get the Text
                String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);

            return text;
        }
        catch(Exception e)
        {
                throw new RuntimeException("Record Parsing Failure!!");
        }
    }

    private static String lastUser;
    
    private boolean manageNfcIntent(Intent intent) {
    	boolean result = false;
        // NDEF exchange mode
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                    	String textoDeEtiqueta = getTextFromNFCIntent(intent);
                    	profileManager.loadProfile(textoDeEtiqueta);
            result = true;
        } else {
        	
        }

    	return result;
    }
    
    // ** Cloud4ALL information management



// ** Profile manager interface

    private ProfileManager profileManager = null; 
    
public void loadPage(String url) {
monitor.loadUrl(url);	

}

public void setBrightness(float value) {
	WindowManager.LayoutParams layout = getWindow().getAttributes();
	layout.screenBrightness =value;
	getWindow().setAttributes(layout);
}

public void showTextInterface(boolean value) {
	
}

public void startScreenReader(boolean value) {
	try {
		SettingAccessibility access = new SettingAccessibility(this);
	if (value) {
		access.setAccessibilitySupport(true);
  				access.addAccessibilityService("com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService");
		this.showTextInterface(true);
		
		playSoundNotification();
	} else {
		access.removeAccessibilityService("talkback");
		access.setAccessibilitySupport(false);
		this.showTextInterface(false);
	}
	} catch (Exception e) {
		Log.e("screen reader error", "Error managing screenReader. " +e); 
	}
}
}
