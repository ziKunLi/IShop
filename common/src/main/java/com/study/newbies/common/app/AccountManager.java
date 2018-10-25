package com.study.newbies.common.app;

import com.study.newbies.common.util.storage.AppPreference;

/**
 * @author NewBies
 */
public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户登录状态，登录后调用
     */
    public static void setSignState(boolean state) {
        AppPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return AppPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
