package com.teamproject.plastikproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends MyFuction {

    @BindView(R.id.edtnama)
    EditText edtnama;
    @BindView(R.id.edtemail)
    EditText edtemail;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.edtpasswordconfirm)
    EditText edtpasswordconfirm;
//    @BindView(R.id.regAdmin)
//    RadioButton regAdmin;
//    @BindView(R.id.regUserbiasa)
//    RadioButton regUserbiasa;
    @BindView(R.id.btnregister)
    Button btnregister;
    //String jenkel[]={"laki-laki","perempuan"};
    String strnama,strusername,strpassword,strconpassword,stralamat,strlevel,strjenkel,strnohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        if (regAdmin.isChecked()){
//            strlevel="1";
//        }else{
//            strlevel="0";
//        }
        //mengisi spinner jenis kelamin
//        ArrayAdapter adapter = new ArrayAdapter(c,android.R.layout.simple_spinner_item,jenkel);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinjenkel.setAdapter(adapter);
//        spinjenkel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                strjenkel=jenkel[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    @OnClick({R.id.btnregister,R.id.btnlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.regAdmin:
//                strlevel="admin";
//                break;
            case R.id.btnlogin:
//                strlevel="user biasa";
                myIntent(LoginActivity.class);
                break;

            case R.id.btnregister:
                strnama =edtnama.getText().toString();
//                stralamat =edtalamat.getText().toString();
//                strnohp =edtnotelp.getText().toString();
                strusername = edtemail.getText().toString();
                strpassword =edtpassword.getText().toString();
                strconpassword =edtpasswordconfirm.getText().toString();
                if (TextUtils.isEmpty(strnama)){
                    edtnama.setError("nama tidak boleh kosong");
                    edtnama.requestFocus();
                    myanimation(edtnama);
                }
//                else if(TextUtils.isEmpty(stralamat)){
//                    edtalamat.requestFocus();
//                    edtalamat.setError("alamt tidak boleh kosong");
//                    myanimation(edtalamat);
//                }else if (TextUtils.isEmpty(strnohp)){
//                    edtnotelp.requestFocus();
//                    myanimation(edtnotelp);
//                    edtnotelp.setError("no hp tidak boleh kosong");
//                }
                else if(TextUtils.isEmpty(strusername)){
                    edtemail.requestFocus();
                    myanimation(edtemail);
                    edtemail.setError("username tidak boleh kosong");
                }else if (TextUtils.isEmpty(strpassword)){
                    edtpassword.requestFocus();
                    myanimation(edtpassword);
                    edtpassword.setError("password tidak boleh kosong");
                }else if (strpassword.length()<6){
                    myanimation(edtpassword);
                    edtpassword.setError("password minimal 6 karakter");
                }else if (TextUtils.isEmpty(strconpassword)){
                    edtpasswordconfirm.requestFocus();
                    myanimation(edtpasswordconfirm);
                    edtpasswordconfirm.setError("password confirm tidak boleh kosong");
                }else if (!strpassword.equals(strconpassword)){
                    edtpasswordconfirm.requestFocus();
                    myanimation(edtpasswordconfirm);
                    edtpasswordconfirm.setError("password tidak sama");
                }else{
                    registeruser();
                    Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                }

                    break;
        }
    }

    private void registeruser() {
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
