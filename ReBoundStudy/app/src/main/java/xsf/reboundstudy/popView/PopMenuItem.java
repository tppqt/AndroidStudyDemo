package xsf.reboundstudy.popView;

import android.graphics.drawable.Drawable;

/**
 * @author xushangfei
 * @time Created at 2016/3/31.
 * @email xsf_uestc_ncl@163.com
 */
public class PopMenuItem {
    private String title;
    private Drawable drawable;

    public PopMenuItem(String title, Drawable drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
