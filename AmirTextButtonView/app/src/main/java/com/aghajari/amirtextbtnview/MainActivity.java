package com.aghajari.amirtextbtnview;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aghajari.amirtextbuttonview.AmirTextButtonListener;
import com.aghajari.amirtextbuttonview.AmirTextButtonView;
import com.aghajari.amirtextbuttonview.AmirTextData;

public class MainActivity extends AppCompatActivity {

    AmirTextButtonView textbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textbtn = findViewById(R.id.txtbtn);

        textbtn.setListener(new AmirTextButtonListener() {
            @Override
            public void onStateChanged(int newState, AmirTextData Data) {}

            @Override
            public boolean canAcceptText(CharSequence text) {
                if (text.toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"email is empty!",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                return true;
            }
        });

        textbtn.setButtonData(new AmirTextData("SUBSCRIBE",300));
        textbtn.setRadius(textbtn.dp(60));

        GradientDrawable GD = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.rgb(4,168,198),Color.rgb(0,152,240)});
        GD.setShape(GradientDrawable.RECTANGLE);
        GD.setCornerRadius(textbtn.getRadius());
        textbtn.setButtonBackground(GD);

        GradientDrawable CD = new GradientDrawable();
        CD.setColor(Color.WHITE);
        CD.setShape(GradientDrawable.RECTANGLE);
        CD.setCornerRadius(textbtn.getRadius());
        textbtn.setDefaultBackground(CD);

        Drawable image = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.send));
        DrawableCompat.setTint(DrawableCompat.wrap(image),Color.WHITE);
        textbtn.setIcon(image);

        textbtn.addText(new AmirTextData("THANKS FOR SUBSCRIBING",250,1000));
        //textbtn.addText(new AmirTextData("THANKS FOR SUBSCRIBING2",250,1000));
        textbtn.setEditTextData("enter your email...",Color.GRAY,16,Color.BLACK, Typeface.DEFAULT);

        /**
        textbtn.setButton2Data(new AmirTextData("CANCEL",500));

        GradientDrawable GD2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.BLACK,Color.LTGRAY});
        GD2.setShape(GradientDrawable.RECTANGLE);
        GD2.setCornerRadius(textbtn.getRadius());
        textbtn.setButton2Background(GD2,true,true,true);
         */

        textbtn.setKeyboardManager(false,true);

        textbtn.build();
        textbtn.setBaseElevation(textbtn.dp(8));

    }

    @Override
    public void onBackPressed(){
        if (textbtn.getState() == AmirTextButtonView.STATE_EDITTEXT){
            textbtn.switchToButton();
        }else{
            this.finish();
        }
    }
}
