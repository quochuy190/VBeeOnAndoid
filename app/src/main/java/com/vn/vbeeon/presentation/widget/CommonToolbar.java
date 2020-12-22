package com.vn.vbeeon.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.vn.vbeeon.R;

public class CommonToolbar extends Toolbar {
    public CommonToolbar(Context context) {
        super(context);
    }

    public CommonToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView tvTitle;
    private TextView tvSubtitle;
    private View view;
    private void init() {
        view = LayoutInflater.from(this.getContext()).inflate(R.layout.view_toolbar, null);
        tvTitle = view.findViewById(R.id.tv_title_toolbar);
        tvSubtitle = view.findViewById(R.id.tv_sub_title_toolbar);
        addView(view);
        ((LayoutParams) view.getLayoutParams()).gravity = Gravity.CENTER;
    }

    public void setGravityTitle(int gravity){
        ((LayoutParams) view.getLayoutParams()).gravity = gravity;
    }
    public void setSubtitle(String subtitle) {
        this.tvSubtitle.setText(subtitle);
        this.tvSubtitle.setVisibility(VISIBLE);
    }

    public void setSubtitle(int res) {
        this.tvSubtitle.setText(res);
        this.tvSubtitle.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setTitle(int res) {
        this.tvTitle.setText(res);
    }
}
