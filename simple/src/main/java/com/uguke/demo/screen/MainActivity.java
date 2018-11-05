package com.uguke.demo.screen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.uguke.android.screen.Screen;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView statusCommon;
    private TextView navCommon;
    private TextView statusImmerse;
    private TextView navImmerse;
    private TextView fontDark;
    private TextView fontLight;
    private CheckBox statusBox;
    private CheckBox navBox;
    private SeekBar statusColor;
    private SeekBar statusAlpha;
    private SeekBar navColor;
    private SeekBar navAlpha;

    private boolean statusColorMode;
    private boolean navColorMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();

        Screen.with(this)
                .statusBarDarkFont()
                .statusBarBackground(R.drawable.status_bar1)
                .navigationBarBackground(R.drawable.nav_bar1)
                .statusBarAlpha(150)
                .navigationBarAlpha(150);
    }


    private void initView() {
        statusCommon = findViewById(R.id.status_common);
        navCommon = findViewById(R.id.nav_common);
        statusImmerse = findViewById(R.id.status_immerse);
        navImmerse = findViewById(R.id.nav_immerse);
        fontDark = findViewById(R.id.font_dark);
        fontLight = findViewById(R.id.font_light);
        statusBox = findViewById(R.id.status_box);
        navBox = findViewById(R.id.nav_box);
        statusColor = findViewById(R.id.status_color);
        statusAlpha = findViewById(R.id.status_alpha);
        navColor = findViewById(R.id.nav_color);
        navAlpha = findViewById(R.id.nav_alpha);

        statusCommon.setOnClickListener(this);
        navCommon.setOnClickListener(this);
        statusImmerse.setOnClickListener(this);
        navImmerse.setOnClickListener(this);
        fontDark.setOnClickListener(this);
        fontLight.setOnClickListener(this);

        statusBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                statusColorMode = isChecked;
                if (statusColorMode) {
                    int color = getBarColor(statusColor.getProgress());
                    Screen.with(MainActivity.this).statusBarColor(color);
                } else {
                    Screen.with(MainActivity.this).statusBarBackground(R.drawable.status_bar1);
                }
            }
        });

        navBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                navColorMode = isChecked;
                if (navColorMode) {
                    int color = getBarColor(navColor.getProgress());
                    Screen.with(MainActivity.this).navigationBarColor(color);
                } else {
                    Screen.with(MainActivity.this).navigationBarBackground(R.drawable.status_bar1);
                }
            }
        });


        statusAlpha.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Screen.with(MainActivity.this).statusBarAlpha(progress);
            }
        });

        navAlpha.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Screen.with(MainActivity.this).navigationBarAlpha(progress);
            }
        });

        statusColor.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (statusColorMode) {
                    int color = getBarColor(progress);
                    Screen.with(MainActivity.this).statusBarColor(color);
                }
            }
        });

        navColor.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (navColorMode) {
                    int color = getBarColor(progress );
                    Screen.with(MainActivity.this).navigationBarColor(color);
                }
            }
        });

    }

    public int getBarColor(int progress) {
        int [] colors = {
                Color.parseColor("#0492CE"),
                Color.parseColor("#66B032"),
                Color.parseColor("#CFEA2B"),
                Color.parseColor("#FEFE33"),
                Color.parseColor("#FABC04"),
                Color.parseColor("#FB9904")};
        return colors[progress / 50];
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.status_common:
                // 内容不侵入状态栏
                Screen.with(this).immerseStatusBar(false);
                break;
            case R.id.nav_common:
                // 内容不侵入导航栏
                Screen.with(this).immerseNavigationBar(false);
                break;
            case R.id.status_immerse:
                // 内容侵入状态栏
                Screen.with(this).immerseStatusBar();
                break;
            case R.id.nav_immerse:
                // 内容侵入导航栏
                Screen.with(this).immerseNavigationBar();
                break;
            case R.id.font_dark:
                Screen.with(this).statusBarDarkFont();
                break;
            case R.id.font_light:
                Screen.with(this).statusBarLightFont();
                break;
            default:
        }
    }
}
