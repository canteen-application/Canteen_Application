package com.example.microtemp.microblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.microtemp.microblog.api.HttpRequestData;
import com.example.microtemp.microblog.api.HttpRequestMethods;
import com.example.microtemp.microblog.api.handlers.LoginRequestHandler;
import com.example.microtemp.microblog.api.handlers.RegisterRequestHandler;
import com.example.microtemp.microblog.api.models.requests.LoginRequestBody;
import com.example.microtemp.microblog.api.models.requests.RegisterRequestBody;
import com.example.microtemp.microblog.api.models.responses.LoginResponse;
import com.example.microtemp.microblog.api.models.responses.RegisterResponse;


public class LoginActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;
    EditText email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email) ;
        password = findViewById(R.id.password) ;
        loginBtn = findViewById(R.id.sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIXME
                if(email.getText().toString().equals("")|| password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Bad mail or password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginRequestBody requestBody = LoginRequestBody.builder()
                            .email(email.getText().toString())
                            .password(password.getText().toString())
                            .build();
                    HttpRequestData<LoginRequestBody> requestData = HttpRequestData.<LoginRequestBody>builder()
                            .requestBody(requestBody)
                            .method(HttpRequestMethods.POST)
                            .build();

                    new LoginRequestHandler() {
                        @Override
                        protected void onPostExecute(LoginResponse result) {
                            if(result.getHttpStatusCode()==200) {
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                            else {
                                // FIXX bledy
                                Toast.makeText(LoginActivity.this, "Nie mozna sie zalogowac", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(requestData);


                }
           //  new MakeNetworkCall().execute("http://212.191.92.88:5101/"+"email/"+email.getText() +"/haslo/"+ password.getText(), "Get");
            }
        });
        registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}

