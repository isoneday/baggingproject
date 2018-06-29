package com.teamproject.plastikproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teamproject.plastikproject.activity.HalamanBaggingActivity;
import com.teamproject.plastikproject.helper.SessionManager;
import com.teamproject.plastikproject.model.ModelUser;
import com.teamproject.plastikproject.network.MyRetrofitClient;
import com.teamproject.plastikproject.network.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends SessionManager {

    @BindView(R.id.regUsername)
    EditText regUsername;
    @BindView(R.id.regPass)
    EditText regPass;
//    @BindView(R.id.regAdmin)
//    RadioButton regAdmin;
//    @BindView(R.id.regUserbiasa)
//    RadioButton regUserbiasa;
    @BindView(R.id.regBtnLogin)
    Button regBtnLogin;
    @BindView(R.id.regBtnRegister)
    Button regBtnRegister;
      String strlevel,strusername,strpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//
//        if (regAdmin.isChecked()){
//            strlevel="admin";
//        }else{
//            strlevel="user biasa";
//        }
    }

    @OnClick({ R.id.regBtnLogin, R.id.regBtnRegister})
    public void onViewClicked(View view) {
        strusername =regUsername.getText().toString();
        strpassword =regPass.getText().toString();

        switch (view.getId()) {
//            case R.id.regAdmin:
//                strlevel="admin";
//
//                break;
//            case R.id.regUserbiasa:
//                strlevel="user biasa";
//
//                break;
            case R.id.regBtnLogin:
                if (TextUtils.isEmpty(strusername)){
                        regUsername.setError("username tidak boleh kosong");
                    }else if (TextUtils.isEmpty(strpassword)){
                        regPass.setError("password tidak boleh kosong");
                    }else if (strpassword.length()<6){
                        regPass.setError("minimal password 6 karakter");
                    }else {
                        loginuser();
                }

                    break;
            case R.id.regBtnRegister:
                myIntent(RegisterActivity.class);
                break;
        }
    }

    private void loginuser() {
        showProgressDialog("proses login user");
        RestApi api = MyRetrofitClient.getInstaceRetrofit();
        Call<ModelUser> modelUserCall =api.loginUser(
                 strusername,strpassword);
        modelUserCall.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                hideProgressDialog();

             if (response.isSuccessful()){
                 String token =response.body().getId();
                 Log.d("testaja",token);
                 sessionManager.createSession(strusername);
            //     sessionManager.setIdUser(token);
                sessionManager.setToken(token);
//ASU6fXPWzLCbrf8Wt6fiynINiISpHcndJM5dwRsZsehhyKW2KDL25rWTOGIu4RVl
                 myIntent(HalamanBaggingActivity.class);
             }else{
                 myToast("gagal login,cek email anda");
             }
 //                if (result.equals("1")) {
//                myToast(msg);

//                String iduser =response.body().getUser().getIdUser();
//                        sessionManager.createSession(strusername);
//                    sessionManager.setIdUser(iduser);
//                    finish();
//                }


                }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                hideProgressDialog();
                myToast("gagal koneksi :"+t.getMessage());

            }
        });

    }
}
