package com.example.secondgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameLoop extends Thread{
   public static final double MAX_UPS = 30.0;
   private static final double UPS_PERIOD = 1E+3/MAX_UPS;
   private SurfaceHolder surfaceHolder;
   private Game game;
   private boolean isRunning = false;
   private double averageUPS;
   private double averageFPS;


   public GameLoop(Game game, SurfaceHolder surfaceHolder) {
       this.game = game;
       this.surfaceHolder=surfaceHolder;
   }


   public double getAverageUPS() {
       return averageUPS;
   }


   public double getAverageFPS() {
       return averageFPS;
   }


   public void startLoop() {
       isRunning = true;
       start();
   }


   @Override
   public void run() {
       super.run();
       int updateCount = 0;
       int framecount = 0;


       long startTime;
       long elapsedTime;
       long sleepTime;


       Canvas canvas = null;
       startTime = System.currentTimeMillis();
       while(isRunning){
           try{
               canvas = surfaceHolder.lockCanvas();
               synchronized (surfaceHolder){
                   game.update();
                   updateCount++;
                   game.draw(canvas);
               }




           }catch (IllegalArgumentException e) {
               e.printStackTrace();
           } finally {
               if(canvas != null){
                   try {
                       surfaceHolder.unlockCanvasAndPost(canvas);
                       framecount++;
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }




           elapsedTime = System.currentTimeMillis()-startTime;
           sleepTime = (long) (updateCount*UPS_PERIOD-elapsedTime);
           if(sleepTime > 0) {
               try {
                   sleep(sleepTime);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }


           while(sleepTime < 0 && updateCount < MAX_UPS-1){
               game.update();
               updateCount++;
               elapsedTime = System.currentTimeMillis()-startTime;
               sleepTime = (long) (updateCount*UPS_PERIOD-elapsedTime);
           }


           elapsedTime = System.currentTimeMillis()-startTime;
           if(elapsedTime >= 1000){
               averageUPS = updateCount / (1E-3*elapsedTime);
               averageFPS = framecount / (1E-3*elapsedTime);
               updateCount=0;
               framecount=0;
               startTime=System.currentTimeMillis();
           }
       }
   }
}
