import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Joystick {




   private  int outerCircleCenterPositionX;
   private  int outerCircleCenterPositionY;
   private  int innerCircleCenterPositionX;
   private  int innerCircleCenterPositionY;
   private  int outerCircleRadius;
   private  int innerCircleRadius;
   private  Paint innerCirclePaint;
   private Paint outerCirclePaint;
   private double joystickCenterToTouchDistance;
   private double actuatorX;
   private boolean isPressed;
   private double actuatorY;




   public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius){
       outerCircleCenterPositionX = centerPositionX;
       outerCircleCenterPositionY = centerPositionY;
       innerCircleCenterPositionX = centerPositionX;
       innerCircleCenterPositionY = centerPositionY;


       this.outerCircleRadius = outerCircleRadius;
       this.innerCircleRadius = innerCircleRadius;


       outerCirclePaint = new Paint();
       outerCirclePaint.setColor(Color.GRAY);
       outerCirclePaint.setStyle((Paint.Style.FILL_AND_STROKE));


       innerCirclePaint = new Paint();
       innerCirclePaint.setColor(Color.BLUE);
       innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);




   }


   public void draw(Canvas canvas) {
       canvas.drawCircle(outerCircleCenterPositionX, outerCircleCenterPositionY, outerCircleRadius, outerCirclePaint);
       canvas.drawCircle(innerCircleCenterPositionX, innerCircleCenterPositionY, innerCircleRadius, innerCirclePaint);
   }


   public void update() {
       updateInnerCirclePosition();
   }


   private void updateInnerCirclePosition() {
       innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
       innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
   }


   public boolean isPressed(double touchPositionx, double touchPositiony) {
       joystickCenterToTouchDistance = Math.sqrt(
               Math.pow(outerCircleCenterPositionX-touchPositionx, 2) + Math.pow(outerCircleCenterPositionY - touchPositiony, 2)
       );
       return joystickCenterToTouchDistance < outerCircleRadius;
   }




   public void setIsPressed(boolean isPressed) {
       this.isPressed = isPressed;
   }


   public boolean getIsPressed() {
       return isPressed;
   }


   public void setActuator(double touchPositionx, double touchPositiony) {
       double deltax = touchPositionx-innerCircleCenterPositionX;
       double deltay = touchPositiony-innerCircleCenterPositionY;
       double deltaDistance = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
      
       if(deltaDistance < outerCircleRadius){
           actuatorX = deltax/outerCircleRadius;
           actuatorY = deltay/outerCircleRadius;
       } else{
           actuatorX = deltax/deltaDistance;
           actuatorY = deltay/deltaDistance;       
       }
      
   }


   public void resetActuator() {
       actuatorX = 0.0;
       actuatorY= 0.0;
   }


   public double getActuatorX() {
       return actuatorX;
   }
   public double getActuatorY() {
       return actuatorY;
   }
}
