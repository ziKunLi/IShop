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
 * @date 2018/9/13
 */

public class SignUpDelegate extends AppDelegate {

    @BindView(R2.id.editSignUpName)
    TextInputEditText textName;
    @BindView(R2.id.editSignUpEmail)
    TextInputEditText textEmail;
    @BindView(R2.id.editSignUpPhone)
    TextInputEditText textPhone;
    @BindView(R2.id.editSignUpPassword)
    TextInputEditText textPassword;
    @BindView(R2.id.editSignUpRePassword)
    TextInputEditText textRepassword;

    private ISignListener signListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            signListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btnSignUp)
    void onClickSignUp(){
        if(checkForm()){
            RestClient.builder()
                    .url("sign_up")
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

    @OnClick(R2.id.tvLinkSignIn)
    void onClickLink(){
        getSupportDelegate().start(new SignInDelegate());
    }

    private boolean checkForm(){
        final String name = textName.getText().toString();
        final String email = textEmail.getText().toString();
        final String phone = textPhone.getText().toString();
        final String password = textPassword.getText().toString();
        final String repassword = textRepassword.getText().toString();

        boolean isPass = true;

        if(StringUtil.isNull(name)){
            textName.setError("请输入姓名");
            isPass = false;
        } else{
            textName.setError(null);
        }

        if(StringUtil.isNull(email)|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("错误的邮箱格式");
            isPass = false;
        } else{
            textEmail.setError(null);
        }

        if(StringUtil.isNull(phone)|| phone.length() != 11){
            textPhone.setError("手机号码错误");
            isPass = false;
        } else{
            textPhone.setError(null);
        }

        if(StringUtil.isNull(password) || password.length() < 6){
            textPassword.setError("请填写至少6位密码");
            isPass = false;
        } else{
            textPassword.setError(null);
        }

        if(StringUtil.isNull(repassword) || repassword.length() < 6 || !repassword.equals(password)){
            textRepassword.setError("密码验证错误");
            isPass = false;
        }else {
            textRepassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
