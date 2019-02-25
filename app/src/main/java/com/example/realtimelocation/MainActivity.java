package com.example.realtimelocation;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_Login;
    private final static int LOGIN_PERMISSION=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Login=(Button)findViewById(R.id.login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AuthUI.IdpConfig>providers= Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build());
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),LOGIN_PERMISSION
                );

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==LOGIN_PERMISSION) {
            startNewActivity(requestCode, data);
        }

    }

    private void startNewActivity(int requestCode, Intent data) {
        if (requestCode==RESULT_OK) {
            Intent intent = new Intent(MainActivity.this,ListOnline.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this,"Failed Login",Toast.LENGTH_SHORT).show();
        }
    }
}
