package xsf.niceothers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import xsf.niceothers.base.BaseActvity;

public class RotateDrawableActivity extends BaseActvity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private final int Duration = 600;  // 动画时长

    private AnimatorState state = AnimatorState.State_Stop;  //动画状态
    private AudioState audioState = AudioState.STATE_STOP;   //音乐播放器状态

    private ImageView btnPre, btnPlay, btnNext;
    private ImageView cdBox, handerd;

    private MediaPlayer mediaPlayer;


    private boolean isRoteflag = false;  //标记，控制唱片旋转

    private String names[] = {"demo01.mp3", "demo02.mp3"};
    private int position = 0;
    private final static int PREVIOUS = -1;
    private final static int STOP = 0;
    private final static int NEXT = 1;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvc;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("RotateDrawble");
        cdBox = IfindViewById(android.R.id.progress);
        handerd = IfindViewById(android.R.id.background);

        btnPre = IfindViewById(R.id.btnPre);
        btnPlay = IfindViewById(R.id.btnPlay);
        btnNext = IfindViewById(R.id.btnNext);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        //启用线程来控制圆盘转动
        new MyThread().start();


    }

    @Override
    public void onClick(View v) {
        //一切都按键必须在动画完成后才能触发
        if (state == AnimatorState.State_Stop) {
            switch (v.getId()) {
                case R.id.btnPre:
                    isRoteflag = false;
                    MusicAction(PREVIOUS);
                    break;
                case R.id.btnPlay:
                    if (audioState != AudioState.STATE_PLAYING) {
                        if (audioState == AudioState.STATE_STOP) {
                            prepareMusic();
                        } else {
                            start();
                        }
                    } else {
                        pause();
                    }

                    break;
                case R.id.btnNext:
                    isRoteflag = false;
                    MusicAction(NEXT);
                    break;

            }
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        MusicAction(NEXT);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        setAudioState(AudioState.STATE_PAUSE);
        start();

    }

    //----------------------------------功能函数----------------------------------
    //开始动画
    private void start() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                handerd.getDrawable().setLevel(level);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                state = AnimatorState.State_Playing;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = AnimatorState.State_Stop;
                audioStart();
            }
        });
        animator.setDuration(Duration);
        animator.start();
    }

    /**
     * 暂停动画
     */
    private void pause() {
        ValueAnimator animator01 = ValueAnimator.ofInt(10000, 0);
        animator01.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                handerd.getDrawable().setLevel(level);
            }
        });

        animator01.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                state = AnimatorState.State_Playing;
                audioPause();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = AnimatorState.State_Stop;
            }
        });
        animator01.setDuration(Duration);
        animator01.start();
    }


    //根据类型决定切歌方式
    private void MusicAction(final int Type) {
        //启动手柄动画
        if (audioState == AudioState.STATE_PLAYING) {
            ValueAnimator animator01 = ValueAnimator.ofInt(10000, 0);
            animator01.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int level = (int) animation.getAnimatedValue();
                    handerd.getDrawable().setLevel(level);
                }
            });

            animator01.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    audioStop();
                    cdBox.getDrawable().setLevel(0);
                    state = AnimatorState.State_Playing;

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    state = AnimatorState.State_Playing;
                    switch (Type) {
                        case PREVIOUS:
                            audioPrevious();
                            break;
                        case STOP:
                            audioStop();
                            break;
                        case NEXT:
                            audioNext();
                            break;
                    }
                }
            });


            animator01.setDuration(Duration);
            animator01.start();
        }


    }


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

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int level = cdBox.getDrawable().getLevel();
            level = level + 200;
            if (level > 10000) {
                level = level - 10000;
            }
            cdBox.getDrawable().setLevel(level);
        }
    };

    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while (true) {
                    Thread.sleep(50);
                    if (isRoteflag) {
                        //只有在flag==true的情况下才会对唱片进行旋转操作
                        handler.sendMessage(new Message());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //音乐控制

    //音乐开始
    private void audioStart() {
        if (mediaPlayer != null && (audioState == AudioState.STATE_PAUSE || audioState == AudioState.STATE_PREPARE)) {
            setAudioState(AudioState.STATE_PLAYING);
            mediaPlayer.start();
            isRoteflag = true;
        } else {
            if (mediaPlayer == null) {
                prepareMusic();
            }
        }
    }

    /**
     * 音乐暂停
     */
    private void audioPause() {
        if (mediaPlayer != null && audioState == AudioState.STATE_PLAYING) {
            setAudioState(AudioState.STATE_PAUSE);
            mediaPlayer.pause();
            isRoteflag = false;
        }
    }

    //音乐停止
    private void audioStop() {
        if (null != mediaPlayer && audioState != AudioState.STATE_STOP) {
            setAudioState(AudioState.STATE_STOP);
            mediaPlayer.stop();
            isRoteflag = false;
        }

    }

    //下一曲
    private void audioNext() {
        if (audioState == AudioState.STATE_STOP) {
            position++;
            if (position >= names.length) {
                position = 0;
            }
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            prepareMusic();
        }
    }

    //播放上一曲
    private void audioPrevious() {
        if (audioState == AudioState.STATE_STOP) {
            position--;
            if (position < 0) {
                position = names.length - 1;
            }
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            prepareMusic();
        }
    }


    private void setAudioState(AudioState audioState) {
        this.audioState = audioState;
        //随着状态改变设置play的图标
        if (audioState == AudioState.STATE_PLAYING) {
            btnPlay.setImageResource(R.drawable.selector_pause);
        } else {
            btnPlay.setImageResource(R.drawable.selector_play);
        }
    }

    //准备播放文件
    private void prepareMusic() {
        //从Assets中获取音频资源
        cdBox.getDrawable().setLevel(0);
        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = this.getAssets().openFd(names[position]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
