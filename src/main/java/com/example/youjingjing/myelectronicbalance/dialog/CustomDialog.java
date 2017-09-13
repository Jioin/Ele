package com.example.youjingjing.myelectronicbalance.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youjingjing.myelectronicbalance.R;

import butterknife.OnTextChanged;


/**
 * Created by youjingjing on 2017/7/26.
 */

public class CustomDialog extends Dialog {
    private String title;
    private String content;
    private String buttonConfirm;
    private String buttonCancel;
    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;
    private OnSureListener sureListener;
    private static final int SHOW_ONE = 1;
    private int show = 2;
    EditText dialog_edit;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.Dialog);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public CustomDialog(Context context, String title, String content,
                        String buttonConfirm,OnSureListener sureListener,
                        View.OnClickListener confirmClickListener) {
        super(context, R.style.Dialog);
        this.title = title;
        this.content = content;
        this.buttonConfirm = buttonConfirm;
        this.sureListener = sureListener;
        this.confirmClickListener = confirmClickListener;
        this.show = SHOW_ONE;
    }
    public CustomDialog(Context context, String title, String content,
                        String buttonConfirm,OnSureListener sureListener,
                        View.OnClickListener confirmClickListener, String buttonCancel) {
        super(context, R.style.Dialog);
        this.title = title;
        this.content = content;
        this.buttonConfirm = buttonConfirm;
        this.sureListener = sureListener;
        this.buttonCancel = buttonCancel;
        this.confirmClickListener = confirmClickListener;
    }
    public CustomDialog(Context context, String title, String content,OnSureListener sureListener,
                        View.OnClickListener confirmClickListener, String buttonConfirm, String buttonCancel) {
        super(context, R.style.Dialog);
        this.title = title;
        this.content = content;
        this.buttonConfirm = buttonConfirm;
        this.buttonCancel = buttonCancel;
        this.sureListener = sureListener;
        this.confirmClickListener = confirmClickListener;
    }

    public CustomDialog(Context context, String title, String content, OnSureListener sureListener,
                        View.OnClickListener confirmClickListener, View.OnClickListener cancelClickListener, String
                                buttonConfirm, String buttonCancel) {
        super(context, R.style.Dialog);
        this.title = title;
        this.content = content;
        this.buttonConfirm = buttonConfirm;
        this.buttonCancel = buttonCancel;
        this.sureListener = sureListener;
        this.confirmClickListener = confirmClickListener;
        this.cancelClickListener = cancelClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        TextView dialog_title = (TextView) findViewById(R.id.dialog_title);
        TextView dialog_content = (TextView) findViewById(R.id.dialog_content);
        TextView dialog_confirm = (TextView) findViewById(R.id.dialog_confirm);
        TextView dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
         dialog_edit = (EditText) findViewById(R.id.dialog_edit);
        View dialog_line = findViewById(R.id.dialog_line);
        if (!TextUtils.isEmpty(title))
            dialog_title.setText(title);
        if (!TextUtils.isEmpty(content)) {
            dialog_content.setText(content);
        } else {
            dialog_content.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(buttonConfirm))
            dialog_confirm.setText(buttonConfirm);
        if (!TextUtils.isEmpty(buttonCancel))
            dialog_cancel.setText(buttonCancel);
        if (SHOW_ONE == show) {
            dialog_line.setVisibility(View.GONE);
            dialog_cancel.setVisibility(View.GONE);
            if (null != confirmClickListener) {
                dialog_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a = dialog_edit.getText().toString();
                         if(a!=null&&a!="") {
                             sureListener.getText(a);

                         }
                        CustomDialog.this.dismiss();
                    }
                });
            }
            dialog_confirm.setBackgroundResource(R.drawable.back_text_selector);
        } else {
            if (null != confirmClickListener) {
                dialog_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                     sureListener.getText(dialog_edit.getText().toString());
                        String a = dialog_edit.getText().toString();
                        if(a!=null&&a!="") {
                            sureListener.getText(a);
                            Log.e( "onClick: ", a);
                        }
                        CustomDialog.this.dismiss();
                    }
                });
            }
            if (null != cancelClickListener) {
                dialog_cancel.setOnClickListener(cancelClickListener);
            } else {
                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.this.dismiss();
                    }
                });
            }

        }
    }

    public void setCanotBackPress() {
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    return true;
                }
                return false;
            }
        });
    }


    @OnTextChanged(value = R.id.dialog_edit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {

    }

}
