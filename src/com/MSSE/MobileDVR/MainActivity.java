package com.MSSE.MobileDVR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends Activity {
    public static final String SHOW_INFO_ID = "showInfoID";
    public static HashMap<Integer, ShowInfo> showRepo = new HashMap<Integer, ShowInfo>();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ShowInfo testShow = new ShowInfo("Mythbusters", "Greatest show on Earth");
        showRepo.put(new Integer(1), testShow);

        Button onlyButton = (Button)findViewById(R.id.show_button);
        onlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowInfoActivity.class);
                intent.putExtra(SHOW_INFO_ID, 1);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
