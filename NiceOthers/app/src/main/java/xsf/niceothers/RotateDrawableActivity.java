package xsf.niceothers;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import xsf.niceothers.base.BaseActvity;

public class RotateDrawableActivity extends BaseActvity {
    private final int Duration = 600;  // 动画时长

    private AnimatorState state = AnimatorState.State_Stop;  //动画状态
    private AudioState audioState = AudioState.STATE_STOP;   //音乐播放器状态

    private ImageView btnPre, btnPlay, btnNext;
    private ImageView cdBox, handerd;

    private MediaPlayer mediaPlayer;

    private boolean flag = false;  //标记，控制唱片旋转

    private String names[] = {"demo01.mp3", "demo02.mp3"};
    private int position = 0;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvc;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("RotateDrawble");
        cdBox = IfindViewById(android.R.id.progress);
        handerd = IfindViewById(android.R.id.background);

        btnPre = IfindViewById(android.R.id.button1);
        btnPlay = IfindViewById(android.R.id.button2);
        btnNext = IfindViewById(android.R.id.button3);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getInfo:

                break;
        }
    }

    //----------------------------------功能函数----------------------------------

    public enum AnimatorState {
        State_Stop,
        State_Playing
    }

    public enum AudioState {
        STATE_STOP,
        STATE_PAUSE,
        STATE_PREPARE,
        STATE_PLAYING
    }

}
