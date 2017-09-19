package com.example.youjingjing.myelectronicbalance.widge;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.dialog.MyProgressDialog;
import com.example.youjingjing.myelectronicbalance.presenter.LoginPresenter;
import com.example.youjingjing.myelectronicbalance.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.login_edit_name)
    EditText etUserName;
    @BindView(R.id.login_edit_psw)
    EditText etPassword;

    @BindView(R.id.login_btn_commit)
    Button btn_commit;

    @BindView(R.id.login_textInputLayout_name)
    TextInputLayout textInputLayout_name;

    @BindView(R.id.login_textInputLayout_key)
    TextInputLayout textInputLayout_key;

    @BindView(R.id.login_check_remember)
    CheckBox checkBox_remember;

    @BindView(R.id.login_check_autoLogin)
    CheckBox checkBox_autoLogin;
    @BindView(R.id.login_edit_host)
    EditText mLoginEditHost;
    @BindView(R.id.login_textInputLayout_host)
    TextInputLayout mLoginTextInputLayoutHost;
    @BindView(R.id.login_edit_port)
    EditText mLoginEditPort;
    @BindView(R.id.login_textInputLayout_port)
    TextInputLayout mLoginTextInputLayoutPort;
    @BindView(R.id.login_tv_status)
    TextView mLoginTvStatus;

    private LoginPresenter loginPresenter;

    String name;
    String psw;
    String port;
    String host;

    MyProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = createPresenter();
        //设置可以计数
        textInputLayout_key.setCounterEnabled(true);
        //计数的最大值
        textInputLayout_key.setCounterMaxLength(6);


        btn_commit.setOnClickListener(this);
        checkBox_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox_remember.isChecked()) {
//                    Toast.makeText(LoginActivity.this, "记住密码", Toast.LENGTH_SHORT).show();
                    writeBoolean("ISCHECK", true);
                } else {
//                    Toast.makeText(LoginActivity.this, "不记住密码", Toast.LENGTH_SHORT).show();
                    writeBoolean("ISCHECK", false);
                }
            }
        });

        //显示是否记住密码
        rememberKey();


        checkBox_autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox_autoLogin.isChecked()) {
//                    Toast.makeText(LoginActivity.this, "自动登录", Toast.LENGTH_SHORT).show();
                    writeBoolean("AUTO_ISCHECK", true);

                    checkBox_remember.setChecked(true);
                    writeBoolean("ISCHECK", true);

                } else {
//                    Toast.makeText(LoginActivity.this, "不自动登录", Toast.LENGTH_SHORT).show();
                    writeBoolean("AUTO_ISCHECK", false);
                }
            }
        });
        //显示是否记住自动登录
//        autoLogin();



    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(); //具体界面对应的presenter由具体界面决定
    }

    @Override
    public void showLoginSuccess() {
        mLoginTvStatus.setVisibility(View.GONE);
        if (dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    public void showLoginFailure(String msg) {
        writeBoolean("AUTO_ISCHECK",false);
        mLoginTvStatus.setText("登录失败");
        btn_commit.setClickable(true);
        mLoginTvStatus.setVisibility(View.VISIBLE);
        if (dialog != null) {

            dialog.dismiss();
        }

    }

    @Override
    public void showLogging() {
        btn_commit.setClickable(false);

//        mLoginTvStatus.setText("正在登录. . .");
//        mLoginTvStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetWorkError() {
        Toast.makeText(this, "网络故障..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rememberKey() {
        name = readData("name");
        port = readData("port");
        host = readData("host");
        if (readBoolean("ISCHECK")) {
            checkBox_remember.setChecked(true);
            psw = readData("psw");
            etPassword.setText(psw);
        } else {
            checkBox_remember.setChecked(false);
            etPassword.setText("");
        }
        etUserName.setText(name);
        mLoginEditPort.setText(port);
        mLoginEditHost.setText(host);
    }

    @Override
    public void autoLogin() {
        if (readBoolean("AUTO_ISCHECK")) {
            checkBox_autoLogin.setChecked(true);
            rememberKey();
            attemptLogin();
        } else {
            checkBox_autoLogin.setChecked(false);
        }
    }

    @Override
    public void setStatus(boolean boo) {

    }

    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        etUserName.setError(null);
        etPassword.setError(null);
        mLoginEditHost.setError(null);
        mLoginEditPort.setError(null);

        // Store values at the time of the login attempt.
        String account = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String host = mLoginEditHost.getText().toString();
        String port = mLoginEditPort.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) ) {
            etPassword.setError("密码不能为空");
            focusView = etPassword;
            cancel = true;
        }else if(!isPasswordValid(password)){
            etPassword.setError("密码不正确");
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            etUserName.setError("用户名不能为空");
            focusView = etUserName;
            cancel = true;
        } else if (!isAccountValid(account)) {
            etUserName.setError("用户名不正确");
            focusView = etUserName;
            cancel = true;
        }
        if (TextUtils.isEmpty(port)) {
            mLoginEditPort.setError("端口号不能为空");
            focusView = etUserName;
            cancel = true;
        } else if (!isNumValid(port)) {
            mLoginEditPort.setError("端口号不正确");
            focusView = mLoginEditPort;
            cancel = true;
        }
        if (TextUtils.isEmpty(host)) {
            mLoginEditPort.setError("端口号不能为空");
            focusView = etUserName;
            cancel = true;
        } else if (!isNumValid(host)) {
            mLoginEditPort.setError("端口号不正确");
            focusView = mLoginEditPort;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            Log.e("attemptLogin: ","----" );
            name = etUserName.getText().toString();
            psw = etPassword.getText().toString();
            host = mLoginEditHost.getText().toString();
            port = mLoginEditPort.getText().toString();
            writeString("name", name);
            writeString("host", host);
            writeString("port", port);
            writeString("psw", psw);

            dialog = new MyProgressDialog();
            dialog.show(getSupportFragmentManager(),"1");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            presenter.login(LoginActivity.this, etUserName.getText().toString(), etPassword.getText().toString(), Integer.parseInt(mLoginEditPort.getText().toString()),mLoginEditHost.getText().toString());

        }
    }

    private boolean isNumValid(String port) {
        return true;
    }

    private boolean isAccountValid(String account) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_commit:
               attemptLogin();
                break;
            case 1:
                break;
        }
    }


}
