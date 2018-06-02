package com.softgarden.baselibrary.widget;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.softgarden.baselibrary.BR;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.base.BaseDialogFragment;
import com.softgarden.baselibrary.base.databinding.DataBindingAdapter;
import com.softgarden.baselibrary.databinding.DialogBottomBinding;

import java.util.List;

/**
 * Created by MirkoWu on 2017/3/29 0029.
 */

public class BottomListDialog extends BaseDialogFragment<DialogBottomBinding> {

    DataBindingAdapter<String> bottomListAdapter;
    List<String> data;
    boolean showCancel = false;//默认不显示

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bottom;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置dialog在屏幕的位置 不用使其铺满整个屏幕 ，
        // 也解决getDialog().getWindow().setLayout(MATCH_PARENT，MATCH_PARENT）和 setCancelable(true)冲突了
        //该方法只对根布局为LinearLayout 适用
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(lp);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        //使dialog和软键盘可以共存
        getDialog().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(true);
    }

    @Override
    public void initialize() {
        binding.btnCancel.setOnClickListener(v -> BottomListDialog.this.dismiss());
        binding.btnCancel.setVisibility(showCancel ? View.VISIBLE : View.GONE);
        binding.mRecyclerView.addItemDecoration(new ColorDividerDecoration(getContext(),
                ColorDividerDecoration.VERTICAL, R.color.line_color, 2, ColorDividerDecoration.MIDDLE));

        bottomListAdapter = new DataBindingAdapter<>(R.layout.item_bottom, BR.item);
        bottomListAdapter.replaceData(data);
        if (onItemClickListener != null)
            bottomListAdapter.setOnItemClickListener((itemView, data1, position) -> {
                boolean isDismiss = onItemClickListener.onItemClick(data1, position);
                if (isDismiss) this.dismiss();
            });

        binding.mRecyclerView.setAdapter(bottomListAdapter);
    }

    public static void show(FragmentManager manager, List<String> data, OnItemClickListener<String> onItemClickListener) {
        show(manager, data, true, onItemClickListener);
    }

    public static void show(FragmentManager manager, List<String> data, boolean showCancel, OnItemClickListener<String> onItemClickListener) {
        BottomListDialog dialog = new BottomListDialog();
        dialog.data = data;
        dialog.showCancel = showCancel;
        dialog.onItemClickListener = onItemClickListener;
        dialog.show(manager, (String) null);
    }


    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener<T> {
        boolean onItemClick(T data, int position);
    }

}
