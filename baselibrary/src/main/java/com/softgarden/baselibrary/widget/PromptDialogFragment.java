package com.softgarden.baselibrary.widget;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.base.BaseDialogFragment;
import com.softgarden.baselibrary.databinding.DialogPromptBinding;


/**
 * Created by Administrator on 2016/4/5.
 */
public class PromptDialogFragment extends BaseDialogFragment<DialogPromptBinding> implements View.OnClickListener {
    TextView tv_title;
    TextView tv_tipsContent;
    TextView btn_ok;
    TextView btn_cancel;

    private String leftLabel, rightLabel, message, title;
    private OnOkDialogListener listener;
    private boolean singleBtn;


    @Override
    public int getLayoutId() {
        return R.layout.dialog_prompt;

    }

    @Override
    public void initialize() {

        tv_title = binding.tvTitle;
        tv_tipsContent = binding.tvTipsContent;
        btn_ok = binding.btnOk;
        btn_cancel = binding.btnCancel;

        btn_ok.setOnClickListener(this);

        if (!TextUtils.isEmpty(rightLabel)) btn_ok.setText(rightLabel);//
        if (!TextUtils.isEmpty(title)) tv_title.setText(title);//
        else tv_title.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(message)) tv_tipsContent.setText(message);//
        else tv_tipsContent.setVisibility(View.GONE);
        //
        btn_cancel.setVisibility(singleBtn ? View.GONE : View.VISIBLE);
        if (!singleBtn) {
            btn_cancel.setOnClickListener(this);
            if (!TextUtils.isEmpty(leftLabel)) btn_cancel.setText(leftLabel);//
        }
    }


    public static void show(FragmentManager manager, String title, String message, OnOkDialogListener listener) {
        PromptDialogFragment dialog = new PromptDialogFragment();
        dialog.message = message;
        dialog.title = title;
        dialog.listener = listener;
        dialog.show(manager, null);
    }

    /**
     * 一个按钮的
     *
     * @param manager
     * @param title
     * @param message
     * @param singleBtn
     * @param listener
     */
    public static void show(FragmentManager manager, String title, String message, boolean singleBtn, OnOkDialogListener listener) {
        PromptDialogFragment dialog = new PromptDialogFragment();
        dialog.message = message;
        dialog.title = title;
        dialog.singleBtn = singleBtn;
        dialog.listener = listener;
        dialog.setCancelable(false);
        dialog.show(manager, null);
    }

    public static void show(FragmentManager manager, String leftLabel, String rightLabel,
                            String title, String message, OnOkDialogListener listener) {
        PromptDialogFragment dialog = new PromptDialogFragment();
        dialog.rightLabel = rightLabel;
        dialog.leftLabel = leftLabel;
        dialog.message = message;
        dialog.title = title;
        dialog.listener = listener;
        dialog.show(manager, null);
    }

    public static void show(FragmentManager manager, String message, OnOkDialogListener listener) {
        PromptDialogFragment dialog = new PromptDialogFragment();
        dialog.message = message;
        dialog.listener = listener;
        dialog.show(manager, null);
    }


    public void onClick(View v) {
        boolean isClose = true;
        if (listener != null) {
            int i = v.getId();
            if (i == R.id.btn_ok) {
                isClose = listener.onDialogClick(true);

            } else if (i == R.id.btn_cancel) {
                isClose = listener.onDialogClick(false);
            }
        }
        if (isClose) dismiss();//当返回值为true时 才会消失
    }

    public interface OnOkDialogListener {
        /**
         * 当窗口按钮被点击
         *
         * @param ok 当确定按钮点击返回true,否则返回false
         * @return 是否关闭当前弹窗
         */
        boolean onDialogClick(boolean ok);
    }

}
