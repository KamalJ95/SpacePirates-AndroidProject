package kamaljahah.rg014522.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kamal on 26/03/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int width = 800;
    public static final int height = 600;
    public static final int moveSpeed = -5;
    private long outTimer;
    private long enemyStart;
    private long secondaryTimer;
    private MainThread thread;
    private secondaryThread secondThread;
    private Background background;
    private player newPlayer;
    private ArrayList<Effects> effect;
    private ArrayList<Enemy> enemy;
    private ArrayList<secondaryEnemy> newEnemy;
    private deathAnimation death;
    private long startDeath;
    private boolean reset;
    private boolean disappear;
    private boolean started;
    private ArrayList<TopBorder> topborder;
    private ArrayList<BotBorder> botborder;
    private int maxBorderHeight;
    private int minBorderHeight;
    private int difficulty = 25; //Increase will slow down difficulty progress
    private boolean top = true;
    private boolean down = true;
    private boolean gamenew;
    private Random rng = new Random();
    private int level = 1;
    private long tertiaryTimer;
    private ArrayList<thirdShip> thirdEnemy;
    public static boolean retry = true;


    /**
     * GameView constructor
     * @param context
     */
    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);


        setFocusable(true);
    }


    /**
     * Everything was initially running through the main thread, but
     * creating a secondary thread that takes everything that has an arraylist in
     * makes the game run at smoother fps.
     */
    public void secondaryThreadCalls(){
        secondThread = new secondaryThread(getHolder(), this);
        effect = new ArrayList<>();
        enemy = new ArrayList<>();
        newEnemy = new ArrayList<>();
        thirdEnemy = new ArrayList<>();
        enemyStart = System.nanoTime();
        topborder = new ArrayList<>();
        botborder = new ArrayList<>();
        secondThread = new secondaryThread(getHolder(), this);
        secondThread.setRunning(true);
        secondThread.start();
    }

    /**
     * Restarts the threads and draws the background and player into it.
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){
       MediaPlayer myplayer = MediaPlayer.create(getContext(), R.raw.seashanty);
        myplayer.start();
        thread = new MainThread(getHolder(), this);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.newblack));
        newPlayer = new player(BitmapFactory.decodeResource(getResources(),R.drawable.newpirateship),173 ,81,1);

        //Makes for a smoother game
        secondaryThreadCalls();
        try{
            secondaryThread.sleep(5);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            thread.sleep(5);
        }catch (Exception e){
            e.printStackTrace();
        }


        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }

    /**
     * Never used function
     * @param surfaceHolder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    /**
     * Was previously in new game but caused cluttering so made into new function
     */
    public void calculateHighscores(){
        if((newPlayer.getScore() > (getRecord())) && (newPlayer.getScore() > getRecord2()) && (newPlayer.getScore() > getRecord3())){
            setRecord3(getRecord2());
            setRecord2(getRecord());
            setRecord(newPlayer.getScore());
            newPlayer.resetScore();
        }
        if((newPlayer.getScore() > getRecord2()) && (newPlayer.getScore() > getRecord3()) && (newPlayer.getScore() < getRecord())){
            setRecord3(getRecord2());
            setRecord2(newPlayer.getScore());
            newPlayer.resetScore();
        }
        if((newPlayer.getScore() > getRecord3()) && newPlayer.getScore() < getRecord2()){
            setRecord3(newPlayer.getScore());
            newPlayer.resetScore();
        }else{
            newPlayer.resetScore();

        }
    }

    /**
     * Function for new game
     * Clears anything in arraylists and rests the borders.
     */
    public void newGame() {
        disappear = false;
        topborder.clear();
        botborder.clear();
        effect.clear();
        enemy.clear();
        newEnemy.clear();
        thirdEnemy.clear();
        minBorderHeight = 5;
        maxBorderHeight = 30;
        newPlayer.resetAcceleration();
        newPlayer.setY(height / 2);

        calculateHighscores();
        resetBorders();


        gamenew = true;
    }

    /**
     * Gets record for first highscore
     * @return
     */
    public int getRecord(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext()); return prefs.getInt("record", -1);
    }

    /**
     * Sets the record for first highscore
     * @param value
     */
    public void setRecord(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit(); editor.putInt("record", value);
        editor.commit();
    }

    /**
     * Gets record for second highscore
     * @return
     */
    public int getRecord2(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext()); return prefs.getInt("record2", -1);
    }

    /**
     * Sets record for second highscore
     * @param value
     */
    public void setRecord2(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit(); editor.putInt("record2", value);
        editor.commit();
    }

    /**
     * Gets record for third highscore
     * @return
     */
    public int getRecord3(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext()); return prefs.getInt("record3", -1);
    }

    /**
     * Sets record for third highscore
     * @param value
     */
    public void setRecord3(int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = prefs.edit(); editor.putInt("record3", value);
        editor.commit();
    }

    /**
     * On screen text for while the game is running. Will hold the level, current score and best score.
     * @param canvas
     */
    public void screenText(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: "+ (newPlayer.getScore()), 10, height-10, paint);
        canvas.drawText("Best Score: "+ getRecord(), width-215, height-10, paint);
        canvas.drawText("Level: " + level, 10, height-550, paint);

        //If the game is reset will run a seperate screen text
        if (!newPlayer.getRunning()&&gamenew&&reset){
            Paint newPaint = new Paint();
            newPaint.setTextSize(40);
            newPaint.setColor(Color.WHITE);
            newPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("Click to start!", width/2-50, height/2, newPaint);

            newPaint.setTextSize(20);
            canvas.drawText("Press and hold for the ship to hover up", width/2-50,height/2+20,newPaint);
            canvas.drawText("Release to let the ship go down!", width/2-50,height/2+40,newPaint);
        }

    }

    /**
     * Function for resetting the borders to initial state in a new game instance
     */
    public void resetBorders(){
        for (int i = 0; i * 25 < width + 40; i++) {
            if (i == 0) {
                topborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder), i * 25, 0, 10));
            } else {
                topborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder), i * 25, 0, topborder.get(i - 1).getHeight() + 1));
            }
        }


        for (int i = 0; i * 25 < width + 40; i++) {
            if (i == 0) {
                botborder.add(new BotBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder), i * 25,height-minBorderHeight));
            } else {
                botborder.add(new BotBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder), i * 25, botborder.get(i - 1).getY() - 1));
            }
        }
    }


    /**
     * What happens when the surface is destroyed, will keep looping until destroyed.
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        int loopCounter = 0;
        while(retry && loopCounter<1000){
            try {
                //Makes sure that the loop is not infinate
                    loopCounter++;
                    thread.setRunning(false);
                    secondThread.setRunning(false);
                    thread.join();
                    secondThread.join();
                         retry = false;
                      //   thread = null; //garbage collector can pick up object, less memory usage
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Function to deal with all of the touch events on screen
     * including going up and going down.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if (!newPlayer.getRunning() && gamenew && reset) {
                newPlayer.setRunning(true);
                newPlayer.setUp(true);
            }
            if (newPlayer.getRunning())
            {
                if (!started)
                    started = true;
                    reset = false;
                    newPlayer.setUp(true);

            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            newPlayer.setUp(false);
            return true;
        }

            return super.onTouchEvent(event);
        }


    /**
     * Function to check if two objects are colliding by checking
     * if rectangles are intersecting.
     * @param initial
     * @param secondary
     * @return
     */
        public boolean collision(GameObject initial, GameObject secondary){

        if (Rect.intersects(initial.getHitbox(),secondary.getHitbox())){
            MediaPlayer myplayer = MediaPlayer.create(getContext(), R.raw.robloxdeath);
            myplayer.start();
            return true;
        }
        return false;

      }

    /**
     * Most important function in gameview
     * Updates every gameobject, border, player.
     * Adds every enemy in position/speed that i wish
     * Deals with collision with borders and enemies
     * Deals with death animation and resetting the game
     */
    public void update(){
        if(newPlayer.getRunning()) {

            if (botborder.isEmpty())
            {
                newPlayer.setRunning(false);
                return;
            }
            if (topborder.isEmpty())
            {
                newPlayer.setRunning(false);
                return;
            }

            background.update();
            newPlayer.update();


            //A threshold that the border can have based on the score
            maxBorderHeight = 30+newPlayer.getScore()/difficulty;
            if (maxBorderHeight > height/4){
                maxBorderHeight = height/4;
                minBorderHeight = 5+newPlayer.getScore()/difficulty;
            }

            for (int i = 0; i < topborder.size(); i++){
                if (collision(topborder.get(i), newPlayer)){
                    newPlayer.setRunning(false);
                }
            }
            for (int i = 0; i < botborder.size(); i++){
                if (collision(botborder.get(i), newPlayer)){
                    newPlayer.setRunning(false);
                }
            }

            this.updateTopBorder();
            //Create the borders
            this.updateBotBorder();

            //Adds smoke from spaceship from the timer
            long timeElapsed = (System.nanoTime() - outTimer)/1000000;
            if(timeElapsed > 120){
                effect.add(new Effects(newPlayer.getX(), newPlayer.getY()+30)); //Balls will appear out of backside of spaceship.
                outTimer = System.nanoTime();
            }
            for (int i = 0; i < effect.size(); i++){
                //Go through every ball and then update
                effect.get(i).update();
                    if (effect.get(i).getX()<-10){
                        effect.remove(i); //Removes the balls that are off the screen
                    }
            }

            //Adds enemies in, first one in middle, rest are random
            long enemyElapsed = (System.nanoTime()- enemyStart)/1000000;
            //Higher the score is, the less delay there is
            if (enemyElapsed> (1500 - newPlayer.getScore()/4)){

                if (enemy.size() == 0){
                    enemy.add(new Enemy(BitmapFactory.decodeResource(getResources(),R.drawable.enemy1), width+10,  height + 50,25, 30, 30, 1));
                }
                else {
                    enemy.add(new Enemy(BitmapFactory.decodeResource(getResources(),R.drawable.enemy1), width + 10, (int) (rng.nextDouble()*(height-(maxBorderHeight*2))+maxBorderHeight),25,30, newPlayer.getScore(), 1));
                }
                //Reset Timer
                enemyStart = System.nanoTime();

            }

            long shipElapsed = (System.nanoTime()-secondaryTimer)/1000000;


            if (newPlayer.getScore() == 150){
                level = 2;
            }

                    //Randomised spot for secondary enemy
                if (newPlayer.getScore()%150==0) {
                    if (newEnemy.size() == 0) {
                        newEnemy.add(new secondaryEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), width + 5, (int) (rng.nextDouble() * (height - (maxBorderHeight * 2)) + maxBorderHeight), 32, 26, 5, 1));
                        newEnemy.add(new secondaryEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), width + 5, (int) (rng.nextDouble() * (height - (maxBorderHeight * 2)) + maxBorderHeight), 32, 26, 5, 1));
                    }

                    secondaryTimer = System.nanoTime();
                }

            long thirdElapsed = (System.nanoTime()- tertiaryTimer)/1000000;

            if (newPlayer.getScore() == 300){
                level = 3;
            }
            if (level == 3) {
                if (enemyElapsed> (1500 - newPlayer.getScore()/4))

                    //CHANGE THIS YOU PLEB!!!!
                    if (thirdEnemy.size() == 0) {
                        thirdEnemy.add(new thirdShip(BitmapFactory.decodeResource(getResources(), R.drawable.longship), width + 10, (int) (rng.nextDouble() * (height - (maxBorderHeight * 2)) + maxBorderHeight), 31, 49, 45, 1));
                    } else {
                        thirdEnemy.add(new thirdShip(BitmapFactory.decodeResource(getResources(), R.drawable.longship), width + 10, (int) (rng.nextDouble() * (height - (maxBorderHeight * 2)) + maxBorderHeight), 31, 49, newPlayer.getScore()/4, 1));
                    }

                //Reset Timer
                tertiaryTimer = System.nanoTime();
            }

            //Go through every enemy and collision will check if two game objects are colliding.
            for (int i = 0; i < enemy.size(); i++){
                enemy.get(i).update();
                if(collision(enemy.get(i), newPlayer)){
                    enemy.remove(i);
                    newPlayer.setRunning(false);
                    break;
                }
                if (enemy.get(i).getX()<-100){
                    enemy.remove(i);
                    break;
                }
            }

            for (int i = 0; i < newEnemy.size(); i++) {
                newEnemy.get(i).update();
                if (collision(newEnemy.get(i), newPlayer)) {
                    newEnemy.remove(i);
                    newPlayer.setRunning(false);
                    break;
                }
                if (newEnemy.get(i).getX() < -100) {
                    newEnemy.remove(i);
                    break;
                }
            }

                for (int i = 0; i < thirdEnemy.size(); i++) {
                    thirdEnemy.get(i).update();
                    if (collision(thirdEnemy.get(i), newPlayer)) {
                        thirdEnemy.remove(i);
                        newPlayer.setRunning(false);
                        break;
                    }
                    if (thirdEnemy.get(i).getX() < -100) {
                        thirdEnemy.remove(i);
                        break;
                    }
                }

        }
        else {
            //Reset the acceleration of the player, if not reset then the start death animation will occur and everything will reset
            newPlayer.resetAcceleration();
            if (!reset){
                gamenew = false;
                startDeath = System.nanoTime();
                reset = true;
                disappear = true;
                death = new deathAnimation(BitmapFactory.decodeResource(getResources(),R.drawable.damage), newPlayer.getX(),newPlayer.getY()-30,329,137,1);
                level = 1;
            }
            death.update();

            long timeElapsed = (System.nanoTime()-startDeath)/1000000;

            //If time has passed and there is no new game yet, then create a new game.
            if (timeElapsed > 2500 && !gamenew){
                newGame();
            }

        }
    }

    /**
     * Draw function takes in the canvas and draws everything to the screen
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas){

        final float scaleX = getWidth()/(width*1.f);
        final float scaleY = getHeight()/(height*1.f);

        if (canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleX, scaleY);
            background.draw(canvas);
            if(!disappear){
                newPlayer.draw(canvas);
            }

            //Top border
            for (TopBorder tp : topborder){
                tp.draw(canvas);
            }

            //Draws bottom border
            for (BotBorder bp : botborder){
                bp.draw(canvas);
            }

            //Draws effects
            for (Effects sp : effect){
                sp.draw(canvas);
            }

            //Draws first enemy
            for (Enemy sp : enemy){
                sp.draw(canvas);
            }

            //Draws second enemy
            for (secondaryEnemy se : newEnemy){
                se.draw(canvas);
            }

            //Draws third enemy
            for (thirdShip ls : thirdEnemy){
                ls.draw(canvas);
            }

            //draws death animation
            if(started){
                death.draw(canvas);
            }

            //Draws screen text
            screenText(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    /**
     * Function for updating the bot border to go up and down depending on player score
     */
    public void updateBotBorder()
    {
        //update bottom border
        for(int i = 0; i<botborder.size(); i++)
        {
            botborder.get(i).update();

            //if border is moving off screen, remove it and add a corresponding new one
            if(botborder.get(i).getX()<-25) {
                botborder.remove(i);


                //determine if border will be moving up or down
                if (botborder.get(botborder.size() - 1).getY() <= height-maxBorderHeight) {
                    down = true;
                }
                if (botborder.get(botborder.size() - 1).getY() >= height - minBorderHeight) {
                    down = false;
                }

                if (down) {
                    botborder.add(new BotBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder), botborder.get(botborder.size() - 1).getX() + 25, botborder.get(botborder.size() - 1
                    ).getY() + 1));
                } else {
                    botborder.add(new BotBorder(BitmapFactory.decodeResource(getResources(), R.drawable.newborder
                    ), botborder.get(botborder.size() - 1).getX() + 25, botborder.get(botborder.size() - 1
                    ).getY() - 1));
                }
            }
        }
    }

    /**
     * Function for updating the top border depending on enemy score
     * Originally had random blocks being put in place depending on score but this caused lag
     * So removed to make sure the game remains smooth.
     */
    public void updateTopBorder(){


        //Go up until reach maximum border height and then go down, so zig zag, maximum will increase when score increases.
        //Every 100 points insert random blocks

        for (int i = 0; i < topborder.size(); i++){
            topborder.get(i).update();

            //Remove top border if off map and replace by adding a new one
            if (topborder.get(i).getX()<-25){
                topborder.remove(i);

                //Retrieves the last element in the array list
                if (topborder.get(topborder.size()-1).getHeight()>=maxBorderHeight){
                    top = false;
                }
                if (topborder.get(topborder.size()-1).getHeight()<=minBorderHeight){
                    top = true;
                }

                //New border will have a larger height, else smaller height
                if (top){
                    topborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.newborder), topborder.get(topborder.size()-1).getX()+25, 0, topborder.get(topborder.size()-1).getHeight()+1));
                }
                else {
                    topborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.newborder), topborder.get(topborder.size()-1).getX()+25, 0, topborder.get(topborder.size()-1).getHeight()-1));
                }
            }
        }

    }




}
