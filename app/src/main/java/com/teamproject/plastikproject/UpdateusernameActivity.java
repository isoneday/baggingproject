package com.teamproject.plastikproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teamproject.plastikproject.helper.MyFuction;
import com.teamproject.plastikproject.model.ModelRegister;
import com.teamproject.plastikproject.network.MyRetrofitClient;
import com.teamproject.plastikproject.network.RestApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateusernameActivity extends MyFuction {

    @BindView(R.id.edtnama)
    EditText edtnama;
    @BindView(R.id.edtemail)
    EditText edtemail;
     @BindView(R.id.btnupdate)
    Button btnupdate;
    //String jenkel[]={"laki-laki","perempuan"};
    String strnama,strusername,strpassword,strconpassword,stralamat,strlevel,strjenkel,strnohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateusername);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnupdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnupdate:
                strnama =edtnama.getText().toString();
//                stralamat =edtalamat.getText().toString();
//                strnohp =edtnotelp.getText().toString();
                strusername = edtemail.getText().toString();
                if (TextUtils.isEmpty(strnama)){
                    edtnama.setError("nama tidak boleh kosong");
                    edtnama.requestFocus();
                    myanimation(edtnama);
                }
                else if(TextUtils.isEmpty(strusername)){
                    edtemail.requestFocus();
                    myanimation(edtemail);
                    edtemail.setError("username tidak boleh kosong");
                }else{
                    updateuser();
                }

                    break;
        }
    }

    private void updateuser() {
            final ProgressDialog dialog =ProgressDialog.show(c,"process register user","harap bersabar");

            RestApi api = MyRetrofitClient.getInstaceRetrofit();
            Call<ModelRegister> ModelRegisterCall =api.registerUser(strusername,strpassword,strlevel,strnama
        );
        //nangkap callback
    ModelRegisterCall.enqueue(new Callback<ModelRegister>() {
        @Override
        public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                dialog.dismiss();
                if (response.isSuccessful()){
                    myIntent(LoginActivity.class);
                    finish();

                } else{
                    myToast("gagal register");
                }
        }

        @Override
        public void onFailure(Call<ModelRegister> call, Throwable t) {
            dialog.dismiss();
            myToast("gagal koneksi :"+t.getMessage());
        }
    });
    }
}
