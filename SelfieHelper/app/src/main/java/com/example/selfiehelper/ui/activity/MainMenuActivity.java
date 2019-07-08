package com.example.selfiehelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.selfiehelper.R;
import com.example.selfiehelper.ui.activity.hotspot.HotspotGroupChat;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.lang.reflect.Array;

public class MainMenuActivity extends AppCompatActivity {

    BoomMenuButton bmb;
    Integer[] imgSrcArray={R.drawable.selfiehelper_logo_icon, R.drawable.selfiehelper_logo_icon,R.drawable.selfiehelper_logo_icon, R.drawable.selfiehelper_logo_icon, R.drawable.selfiehelper_logo_icon};
    String[] textTitle= {"1", "2", "3", "4", "5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bmb = findViewById(R.id.bmb);

        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_3);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_3);


        for (int i=0; i< bmb.getPiecePlaceEnum().pieceNumber(); i++){

            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder().normalImageRes(imgSrcArray[i]).normalText(textTitle[i]);
            bmb.addBuilder(builder);
        }
    }


    public void intentStreamVideo(View view) {
        Intent i =  new Intent(MainMenuActivity.this, StartStreamActivity.class);
        startActivity(i);
    }
    public void intentViewStream(View view) {
        Intent i =  new Intent(MainMenuActivity.this, ConnectToStreamActivity.class);
        startActivity(i);
    }
    public void intentSettings(View View) {
        Intent i = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(i);
    }
    public void onlineStream(View View) {
        Intent i = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(i);
    }
   public void hotspotChat(View View) {
        Intent i = new Intent(MainMenuActivity.this, HotspotGroupChat.class);
        startActivity(i);
    }

}
