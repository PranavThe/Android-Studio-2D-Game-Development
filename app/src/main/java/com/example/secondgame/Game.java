package com.example.secondgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


public class Game extends SurfaceView implements SurfaceHolder.Callback {
   private final Player player;
   private final Joystick joystick;
   private GameLoop gameloop;
   private Context context;
   private android.util.Log Log;


   public Game(Context context) {
       super(context);


       //Get surface holder and add callback
       SurfaceHolder surfaceHolder = this.getHolder();
       surfaceHolder.addCallback(this);


       this.context=context;
       gameloop = new GameLoop(this, surfaceHolder);


       joystick = new Joystick(275, 600, 70, 40);
       player = new Player(getContext(), 2*500, 500, 30);
       setFocusable(true);
   }


   @Override
   public boolean onTouchEvent(MotionEvent event) {
       switch(event.getAction()) {
           case MotionEvent.ACTION_DOWN:
               if(joystick.isPressed((double)event.getX(), (double) event.getY())){
                   joystick.setIsPressed(true);
               }
               return true;
           case MotionEvent.ACTION_MOVE:
               if(joystick.getIsPressed()) {
                   joystick.setActuator((double) event.getX(), (double) event.getY());
               }
               return true;
           case MotionEvent.ACTION_UP:
               joystick.setIsPressed(false);
               joystick.resetActuator();
               return true;
       }
       return super.onTouchEvent(event);
   }


   @Override
   public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
       gameloop.startLoop();
   }


   @Override
   public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {


   }


   @Override
   public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {


   }


   @Override
   public void draw(Canvas canvas) {
       super.draw(canvas);
       drawUPS(canvas);
       drawFPS(canvas);
       joystick.draw(canvas);
       player.draw(canvas);
   }
   public void drawUPS(Canvas canvas){
       String averageUPS = Double.toString(gameloop.getAverageUPS());
       Paint paint = new Paint();
       int color = ContextCompat.getColor(context, R.color.magenta);
       paint.setColor(color);
       paint.setTextSize(50);
       canvas.drawText("UPS: " + averageUPS, 100, 100, paint);


   }
   public void drawFPS(@NonNull Canvas canvas){
       String averageFPS = Double.toString(gameloop.getAverageFPS());
       Paint paint = new Paint();
       int color = ContextCompat.getColor(context, R.color.magenta);
       paint.setColor(color);
       paint.setTextSize(50);
       canvas.drawText("FPS: " + averageFPS, 100, 200, paint);


   }


   public void update() {
       joystick.update();
       player.update(joystick);


   }
}
