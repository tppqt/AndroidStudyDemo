package xsf.weather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import xsf.MvpStudy.R;
import xsf.weather.base.BaseActvity;

public class PM25Activity extends BaseActvity {
    private static final String LogTag = "pm25";
    private static Map<String, String> maps = new HashMap<String, String>();
    private static float x;
    private Boolean canPrint = false;
    private Boolean canRip = false;
    private int cityIndex = 0;
    private Boolean isAbout = false;
    private Boolean isCitySwitching = false;
    private Boolean isFeedback = false;
    private Boolean isFirst = true;
    private TextView mAQI;
    private Animation mAlphaAnim = new AlphaAnimation(0.1F, 1.0F);
    private ViewGroup mBodyLayout;
    private Animator mBodyReset;
    private Animator mBodyTranslate;
    private TextView mBy;
    private TextView mCity;
    private Animator mFeedbackSended;
    private ListView mListView;
    private ImageButton mNext;
    private TextView mPM25;
    private TextView mPaperAQI;
    private TextView mPaperAQIDesc;
    private TextView mPaperArea;
    private TextView mPaperDivider1;
    private TextView mPaperDivider2;
    private TextView mPaperFeedbackDesc;
    private EditText mPaperFeedbackMessage;
    private TextView mPaperFeedbackTitle;
    private ViewGroup mPaperLayout;
    private ViewGroup mPaperListLayout;
    private TextView mPaperProposal;
    private TextView mPaperQuality;
    private TextView mPaperShareFrom;
    private TextView mPaperTime;
    private TextView mPaperTitle;
    private Animator mPaperUpAll;
    private Animator mPaperUpTitle;
    private ImageButton mPrint;
    private MediaPlayer mPrintPlayer;
    private MediaPlayer mRipPlayer;
    private PM25CitySetting mSetting;
    private ImageButton mShare;
    private TextView mStudio;
    private TextView mValue;
    private ViewGroup mrl;
    private String vAQI;
    private String vBeforeCity;
    private String vCity;
    private String vPM25;
    private String vQuality;
    private String vShare = "今日%s空气质量指数(AQI)：%s，等级【%s】；PM2.5 浓度值：%s μg/m3。%s。（请关注博客：http://blog.csdn.net/weidi1989 ）";
    private String vTime;

    static {
        maps.put("优", "空气特别好，尽情活动吧");
        maps.put("良", "仅对特别敏感患者轻微影响");
        maps.put("轻度污染", "易感人群勿长期户外活动");
        maps.put("中度污染", "可能影响心脏、呼吸系统");
        maps.put("重度污染", "心脏病和肺病患者症状加剧");
        maps.put("严重污染", "所有人尽量避免户外活动");
    }

    @Override
    protected void init() {
        mSetting = new PM25CitySetting(this);

        //初始化播放器
        mPrintPlayer = MediaPlayer.create(this, R.raw.print);
        mPrintPlayer.setLooping(true);
        mRipPlayer = MediaPlayer.create(this, R.raw.paper_rip);

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvc;
    }

    @Override
    protected void initView() {
        //getSupportActionBar().setTitle("MVC");
        mrl = ((ViewGroup) findViewById(R.id.rl));
        mBodyLayout = ((ViewGroup) findViewById(R.id.layout_main));
        mPaperLayout = ((ViewGroup) findViewById(R.id.layout_ticket_out));

        mPaperListLayout = ((ViewGroup) findViewById(R.id.layout_paper_list_header));
        mCity = ((TextView) findViewById(R.id.txt_city_value));
        mAQI = ((TextView) findViewById(R.id.txt_aqi_desc));
        mPM25 = ((TextView) findViewById(R.id.txt_pm25_desc));
        mValue = ((TextView) findViewById(R.id.txt_value));
        mShare = ((ImageButton) findViewById(R.id.btn_paper_share));
        mNext = ((ImageButton) findViewById(R.id.btn_next));
        mPrint = ((ImageButton) findViewById(R.id.btn_print));
        mBy = ((TextView) findViewById(R.id.txt_led_by));
        mStudio = ((TextView) findViewById(R.id.txt_led_studio));
        mListView = ((ListView) mPaperLayout.findViewById(R.id.paper_list));
        mPaperAQI = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_aqi_value));
        mPaperArea = ((TextView) mPaperLayout.findViewById(R.id.txt_paper_area));
        mPaperTime = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_datetime));
        mPaperQuality = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_quality));
        mPaperProposal = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_proposal));
        mPaperTitle = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_report));
        mPaperAQIDesc = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_aqi_desc));
        mPaperShareFrom = ((TextView) mPaperLayout
                .findViewById(R.id.txt_share_from));
        mPaperDivider1 = ((TextView) mPaperLayout.findViewById(R.id.divide1));
        mPaperDivider2 = ((TextView) mPaperLayout.findViewById(R.id.divide2));
        mPaperFeedbackMessage = ((EditText) mPaperLayout
                .findViewById(R.id.txt_paper_feedback_message));
        mPaperFeedbackTitle = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_feedback_title));
        mPaperFeedbackDesc = ((TextView) mPaperLayout
                .findViewById(R.id.txt_paper_feedback_desc));
    }

    @Override
    protected void initData() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //在onCreate方法中获取宽高
        mrl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int i = PM25Activity.this.getWindow()
                        .findViewById(R.id.rl).getHeight();
                mrl.setLayoutParams(new FrameLayout.LayoutParams(-1, i * 3));
                Log.d(LogTag, "device height :"
                        + PM25Activity.this.getWindow().getDecorView()
                        .getHeight());
                Log.d(LogTag, "onGlobalLayout:" + i);
                PM25Activity.this.mrl.setPadding(0, -i, 0, i);
                RelativeLayout.LayoutParams bodyLayoutParams = (RelativeLayout.LayoutParams) PM25Activity.this.mBodyLayout
                        .getLayoutParams();
                bodyLayoutParams.height = (i * 2);
                bodyLayoutParams.setMargins(0, 0, 0, -i);
                PM25Activity.this.mBodyLayout
                        .setLayoutParams(bodyLayoutParams);
                RelativeLayout.LayoutParams paperLayoutParams = (RelativeLayout.LayoutParams) PM25Activity.this.mPaperLayout
                        .getLayoutParams();
                paperLayoutParams.height = (i * 2);
                paperLayoutParams.setMargins(
                        0, PM25Activity.this.getResources().getDimensionPixelSize(R.dimen.main_paper_margin_top),
                        0, PM25Activity.this.getResources().getDimensionPixelSize(R.dimen.main_paper_margin_bottom));
                mPaperLayout.setLayoutParams(paperLayoutParams);
                mrl.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });

        //初始化字体
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fonts/LCD.ttf");
        mCity.setTypeface(typeface);
        mAQI.setTypeface(typeface);
        mPM25.setTypeface(typeface);
        mValue.setTypeface(typeface);
        mBy.setTypeface(typeface);
        mStudio.setTypeface(typeface);

        //动画
        mBodyTranslate = PM25Anim.down(this.mBodyLayout, getResources().getDimension(R.dimen.paper_anim_down));//表盘下移
        //mBodyTranslate.start();
        mPaperUpTitle = PM25Anim.up(this.mPaperLayout, getResources().getDimension(R.dimen.paper_anim_up_one));//报告title出现
        //mPaperUpTitle.start();
        mPaperUpAll = PM25Anim.up(this.mPaperLayout, getResources().getDimension(R.dimen.paper_anim_up_one), getResources().getDimension(R.dimen.paper_anim_up_two));
        //mPaperUpAll.start();
        mFeedbackSended = PM25Anim.upAndVanish(this.mPaperLayout, getResources().getDimension(R.dimen.paper_anim_up_two));
        mBodyReset = PM25Anim.up(this.mBodyLayout, getResources().getDimension(R.dimen.paper_anim_down), 0.0F);

        mFeedbackSended.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBodyReset.start();
                disablePaperFeedback();//将反馈gone
            }
        });

        Animator.AnimatorListener local3 = new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator paramAnonymousAnimator) {
            }

            public void onAnimationEnd(Animator paramAnonymousAnimator) {
                mPrintPlayer.pause();
            }

            public void onAnimationRepeat(Animator paramAnonymousAnimator) {
            }

            public void onAnimationStart(Animator paramAnonymousAnimator) {
                mPrintPlayer.start();
            }
        };
        mPaperUpAll.addListener(local3);
        mPaperUpTitle.addListener(local3);//两次打印

        mNext.setOnClickListener(this);
        mPrint.setOnClickListener(this);
        mNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO长按选城市
                Toast.makeText(PM25Activity.this, "长按", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mPrint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //提建议
                isFeedback = true;
                enablePaperFeedback();
                startPaperAnimation();

                return true;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                //选择
                Toast.makeText(PM25Activity.this, "短按", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_print:
                doPrintAnmii();
                break;

        }
    }


    /***
     * -----------------------子函数-----------------------------------------------------------------
     **/
    private void doPrintAnmii() {

    }

    //长按,建议的动画
    private void startPaperAnimation() {
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) mPaperLayout.getLayoutParams();
        Object[] objects1 = new Object[1];
        objects1[0] = Integer.valueOf(localLayoutParams.height);
        Log.d(LogTag, String.format("paperLP.height %s", objects1));
        localLayoutParams.height = getResources().getDimensionPixelSize(R.dimen.paper_height);
        Object[] objects2 = new Object[1];
        objects2[0] = Integer.valueOf(localLayoutParams.height);
        Log.d(LogTag, String.format("paperLP.height %s", objects2));
        this.mPaperLayout.setLayoutParams(localLayoutParams);
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] arrayOfAnimator = new Animator[3];
        arrayOfAnimator[0] = mPaperUpTitle;
        arrayOfAnimator[1] = mBodyTranslate;
        arrayOfAnimator[2] = mPaperUpAll;
        animatorSet.playSequentially(arrayOfAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator paramAnonymousAnimator) {
            }

            public void onAnimationEnd(Animator paramAnonymousAnimator) {
                if (!isFeedback)
                    mShare.setVisibility(View.VISIBLE);
                canRip = true;
            }

            public void onAnimationRepeat(Animator paramAnonymousAnimator) {
            }

            public void onAnimationStart(Animator paramAnonymousAnimator) {
            }
        });
        animatorSet.start();
    }

    private void enablePaperFeedback() {
        mPaperTitle.setVisibility(View.GONE);
        mPaperAQIDesc.setVisibility(View.GONE);
        mPaperArea.setVisibility(View.GONE);
        mPaperAQI.setVisibility(View.GONE);
        mPaperQuality.setVisibility(View.GONE);
        mPaperTime.setVisibility(View.GONE);
        mPaperProposal.setVisibility(View.GONE);
        mPaperListLayout.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mPaperShareFrom.setVisibility(View.GONE);
        mPaperDivider1.setVisibility(View.GONE);
        mPaperDivider2.setVisibility(View.GONE);
        mPaperFeedbackMessage.setVisibility(View.VISIBLE);
        mPaperFeedbackTitle.setVisibility(View.VISIBLE);
        mPaperFeedbackDesc.setVisibility(View.VISIBLE);
    }

    private void disablePaperFeedback() {
        mPaperTitle.setVisibility(View.VISIBLE);
        mPaperAQIDesc.setVisibility(View.VISIBLE);
        mPaperArea.setVisibility(View.VISIBLE);
        mPaperAQI.setVisibility(View.VISIBLE);
        mPaperQuality.setVisibility(View.VISIBLE);
        mPaperTime.setVisibility(View.VISIBLE);
        mPaperProposal.setVisibility(View.VISIBLE);
        mPaperListLayout.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mPaperShareFrom.setVisibility(View.VISIBLE);
        mPaperDivider1.setVisibility(View.VISIBLE);
        mPaperDivider2.setVisibility(View.VISIBLE);
        mPaperLayout.setAlpha(1.0F);
        mPaperFeedbackMessage.setVisibility(View.GONE);
        mPaperFeedbackTitle.setVisibility(View.GONE);
        mPaperFeedbackDesc.setVisibility(View.GONE);
    }

    /**
     * 连续按两次返回键就退出
     */
    private long firstTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime < 3000) {
            finish();
        } else {
            firstTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
