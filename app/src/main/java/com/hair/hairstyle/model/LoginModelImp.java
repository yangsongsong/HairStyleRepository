package com.hair.hairstyle.model;

import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.LoginService;
import com.hair.hairstyle.net.param.LoginParam;
import com.hair.hairstyle.net.result.LoginResult;
import com.hair.hairstyle.presenter.LoginListener;
import com.hair.hairstyle.rx.ResultFun;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/7/28.
 */

public class LoginModelImp implements ILoginModel {


    @Override
    public void requestLogin(String username, String password, LoginListener listener) {

        LoginParam param = new LoginParam();
        param.setPhone(username);
        param.setPassword(password);
        ServiceGenerator.createService(LoginService.class, "http://192.168.63.11:8890/")
                .login(param)
                .flatMap(new ResultFun<LoginResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResult>() {

                    @Override
                    public void accept(LoginResult loginResult) throws Exception {
                        listener.success(loginResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.error();
                    }
                });
    }
}
