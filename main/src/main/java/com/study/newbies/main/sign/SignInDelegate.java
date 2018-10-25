package com.study.newbies.main.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.app.AccountManager;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.IError;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.util.StringUtil;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author NewBies
 * @date 2018/9/14
 */

public class SignInDelegate extends AppDelegate {

    @BindView(R2.id.editSignInEmail)
    TextInputEditText editSignInEmail;
    @BindView(R2.id.editSignInPassword)
    TextInputEditText editSignInPassword;

    private ISignListener signListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            signListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btnSignIn)
    void onClickSignIn(){
        if(checkForm()){
            RestClient.builder()
                    .url("sign_in")
                    .params("", "")
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String message) {

                        }
                    })
                    .build()
                    .post();
            showToast("验证通过");
            AccountManager.setSignState(true);
            signListener.onSignUpSuccess();
        }
    }

    @OnClick(R2.id.iconSignInWeChat)
    void onClickWeChat(){

    }

    @OnClick(R2.id.tvLinkSignUp)
    void onClickLink(){
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm(){
        final String email = editSignInEmail.getText().toString();
        final String password = editSignInPassword.getText().toString();

        boolean isPass = true;

        if(StringUtil.isNull(email)|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editSignInEmail.setError("错误的邮箱格式");
            isPass = false;
        } else{
            editSignInEmail.setError(null);
        }

        if(StringUtil.isNull(password) || password.length() < 6){
            editSignInPassword.setError("请填写至少6位密码");
            isPass = false;
        } else{
            editSignInPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
