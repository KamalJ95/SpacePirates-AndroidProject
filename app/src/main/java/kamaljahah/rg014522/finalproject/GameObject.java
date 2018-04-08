package kamaljahah.rg014522.finalproject;

import android.graphics.Rect;

/**
 * Created by Kamal on 26/03/2018.
 */

/**
 * Abstract game object class.
 */
public abstract class GameObject {

    /**
     * Each object will have an x pos, y pos with a direction in the y angle. DY and DX are vectors and width and height are as stated.
     */
    protected int x,y,directionY,dx,width,height;

    /**
     * Setter for y
     * @param y
     */
    public void setY(int y){

        this.y= y;
    }

    /**
     * Getter for x
     * @return
     */
    public int getX(){

        return x;
    }

    /**
     * Getter for Y
     * @return
     */
    public int getY(){

        return y;
    }

    /**
     * Getter for height
     * @return
     */
    public int getHeight(){

        return height;
    }

    /**
     * Getter for width
     * @return
     */
    public int getWidth(){
        return width;
    }

    /**
     * Getter for hitbox using rectangles, similar to what I used in the robot arena
     * @return
     */
    public Rect getHitbox(){

        return new Rect(x,y,x+width,y+height);
    }




}
