package kamaljahah.rg014522.finalproject;

import android.graphics.Rect;

/**
 * Created by Kamal on 26/03/2018.
 */

public abstract class GameObject {

    //Dy and dx are vectors, width and height is width and height
    protected int x,y,directionY,dx,width,height;

    //Getters and Setters
    public void setX(int x){
        this.x=x;
    }

    public void setY(int y){
        this.y= y;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    //Rectangle for hitbox of objects
    public Rect getHitbox(){
        return new Rect(x,y,x+width,y+height);
    }




}
