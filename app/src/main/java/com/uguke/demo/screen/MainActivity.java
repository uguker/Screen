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

    private TextView mStatusCommon;
    private TextView mNavCommon;
    private TextView mStatusImmerse;
    private TextView mNavImmerse;
    private TextView mFontDark;
    private TextView mFontLight;
    private CheckBox mStatusBox;
    private CheckBox mNavBox;
    private SeekBar mStatusColor;
    private SeekBar mStatusAlpha;
    private SeekBar mNavColor;
    private SeekBar mNavAlpha;

    private boolean mStatusColorMode;
    private boolean mNavColorMode;
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
        mStatusCommon = findViewById(R.id.status_common);
        mNavCommon = findViewById(R.id.nav_common);
        mStatusImmerse = findViewById(R.id.status_immerse);
        mNavImmerse = findViewById(R.id.nav_immerse);
        mFontDark = findViewById(R.id.font_dark);
        mFontLight = findViewById(R.id.font_light);
        mStatusBox = findViewById(R.id.status_box);
        mNavBox = findViewById(R.id.nav_box);
        mStatusColor = findViewById(R.id.status_color);
        mStatusAlpha = findViewById(R.id.status_alpha);
        mNavColor = findViewById(R.id.nav_color);
        mNavAlpha = findViewById(R.id.nav_alpha);

        mStatusCommon.setOnClickListener(this);
        mNavCommon.setOnClickListener(this);
        mStatusImmerse.setOnClickListener(this);
        mNavImmerse.setOnClickListener(this);
        mFontDark.setOnClickListener(this);
        mFontLight.setOnClickListener(this);

        mStatusBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mStatusColorMode = isChecked;
                if (mStatusColorMode) {
                    int color = getBarColor(mStatusColor.getProgress());
                    Screen.with(MainActivity.this).statusBarColor(color);
                } else {
                    Screen.with(MainActivity.this).statusBarBackground(R.drawable.status_bar1);
                }
            }
        });

        mNavBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNavColorMode = isChecked;
                if (mNavColorMode) {
                    int color = getBarColor(mNavColor.getProgress());
                    Screen.with(MainActivity.this).navigationBarColor(color);
                } else {
                    Screen.with(MainActivity.this).navigationBarBackground(R.drawable.status_bar1);
                }
            }
        });


        mStatusAlpha.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Screen.with(MainActivity.this).statusBarAlpha(progress);
            }
        });

        mNavAlpha.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Screen.with(MainActivity.this).navigationBarAlpha(progress);
            }
        });

        mStatusColor.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mStatusColorMode) {
                    int color = getBarColor(progress);
                    Screen.with(MainActivity.this).statusBarColor(color);
                }
            }
        });

        mNavColor.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mNavColorMode) {
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
