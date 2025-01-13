package com.example.secondgame;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//Main activity is the entry point to the application
public class MainActivity extends AppCompatActivity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       EdgeToEdge.enable(this);
       setContentView(R.layout.activity_main);


       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           return insets;
       });


       //Set content view to game, so that objects in the game class can be rendered to the screen


       setContentView(new Game(this));
   }
}
