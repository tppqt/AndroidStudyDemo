package xsf.weather;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


public final class PM25Anim {
    public static Animator add(final View view, float x) {
        //传递进入的view水平方向x->0的变化，动画结束后显示该view
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", x, 0F);
        objectAnimator.setDuration(1000L);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator paramAnonymousAnimator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }
        });
        return objectAnimator;
    }

    public static Animator down(View view, float y) {
        //传递进入的viewY轴方向0->y距离变化
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0.0F, y);
        localObjectAnimator.setDuration(2000L);
        return localObjectAnimator;
    }

    public static Animator up(View view, float y) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "translationY", 0.0F, y);
        objectAnimator.setDuration(2000L);
        return objectAnimator;
    }

    public static Animator up(View view, float fromY, float toY) {
        //传递view在Y轴移动指定位置
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "translationY", fromY, toY);
        objectAnimator.setDuration(2000L);
        return objectAnimator;
    }

    public static Animator upAndVanish(View view, float y) {
        //Y轴完成移动后就消失
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] arrayOfAnimator = new Animator[2];
        float[] floats = new float[2];
        floats[0] = y;
        floats[1] = (3.0F * y);
        arrayOfAnimator[0] = ObjectAnimator.ofFloat(view, "translationY", floats);
        arrayOfAnimator[1] = ObjectAnimator.ofFloat(view, "alpha", 1.0F, 0.0F);
        animatorSet.playTogether(arrayOfAnimator);
        animatorSet.setDuration(2000L);
        return animatorSet;
    }
}
