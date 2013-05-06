package com.MSSE.MobileDVR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
@Deprecated
public class RecordedShowsActivity extends Activity {
    private static final int GUIDE = 1;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_shows);

        Button guideButton = (Button)findViewById(R.id.recorded_shows_button);
        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecordedShowsActivity.this, ChannelGuide.class);
                //intent.putExtra(CONTACT_ID, storage.newContact().getID());
                //intent.putExtra("contact", storage.newContact());
                //intent.putExtra(REPOSITORY, storage);
                startActivityForResult(intent, GUIDE);
            }
        });

        Button recordedShowsButton = (Button)findViewById(R.id.recorded_shows_button);
        recordedShowsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecordedShowsActivity.this, ChannelGuide.class);
                //intent.putExtra(CONTACT_ID, storage.newContact().getID());
                //intent.putExtra("contact", storage.newContact());
                //intent.putExtra(REPOSITORY, storage);
                startActivityForResult(intent, GUIDE);
            }
        });


    }
}
