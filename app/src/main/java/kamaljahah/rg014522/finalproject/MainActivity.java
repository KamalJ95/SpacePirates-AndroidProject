package kamaljahah.rg014522.finalproject;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    /**
     * On create automatically created when creating an activity class
     * On create will run the methods inside it that I desire
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the full window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);

        //Media player to play sea shanty 2
        MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.seashanty);
        mediaplayer.start();



        setContentView(R.layout.activity_main);



        //Button to start the game
        Button gameStart = (Button) findViewById(R.id.gamestart);

        gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setContentView(new GameView(view.getContext()));

            }
        });


        //Button to traverse to the highscores
         Button highscore = (Button) findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, highscore.class));
              }

     }    );


        //Button to exit the game
         Button gameExit = (Button) findViewById(R.id.exit);

        gameExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.exit(1);
            }
        });

        getSupportActionBar().hide();



        //setContentView(new GameView(this));
    }


    /**
     * Automatically created function
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Automatically created function
     * Menu inflater to inflate the menu so is visible
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }
}

