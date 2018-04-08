package kamaljahah.rg014522.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class highscore extends AppCompatActivity {

        public Button goBackBtn;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public int highscore1;
        public int highscore2;
        public int highscore3;


        @Override
        protected void onCreate(Bundle savedInstanceState){

            super.onCreate(savedInstanceState);

            //Set fullscreen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //set initial view to the menu
            setContentView(R.layout.activity_highscore);

            getSupportActionBar().hide();


            goBackBtn = (Button) findViewById(R.id.returngame);
            goBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            highscore1 = preferences.getInt("record", -1);
            tv1 = (TextView) findViewById(R.id.tv1);
            tv1.setText("High Score : " + highscore1);

            highscore2 = preferences.getInt("record2", -1);
            tv2 = (TextView) findViewById(R.id.tv2);
            tv2.setText("Second: " + highscore2);

            highscore3 = preferences.getInt("record3", -1);
            tv3 = (TextView) findViewById(R.id.tv3);
            tv3.setText("Third : " + highscore3);

        }


    }
