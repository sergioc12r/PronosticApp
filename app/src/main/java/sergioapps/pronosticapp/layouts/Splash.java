package sergioapps.pronosticapp.layouts;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import sergioapps.pronosticapp.R;

public class Splash extends AppCompatActivity {
    private  final int DURACION_SPLASH = 1500;
    TextView titleSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        titleSplash= (TextView)findViewById(R.id.titlesplash);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/fad.ttf");
        titleSplash.setTypeface(face);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },DURACION_SPLASH);


    }
}
