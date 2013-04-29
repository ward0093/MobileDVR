package com.MSSE.MobileDVR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final int GUIDE = 1;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button guideButton = (Button)findViewById(R.id.guide_button);
        guideButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				//Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this, ChannelGuide.class);
				//intent.putExtra(CONTACT_ID, storage.newContact().getID());
				//intent.putExtra("contact", storage.newContact());
				//intent.putExtra(REPOSITORY, storage);
				startActivityForResult(intent, GUIDE);
			}
        });
    }
}
