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
import com.example.microtemp.microblog.api.handlers.RegisterRequestHandler;
import com.example.microtemp.microblog.api.models.requests.RegisterRequestBody;
import com.example.microtemp.microblog.api.models.responses.RegisterResponse;

public class RegisterActivity extends AppCompatActivity {
    private static String REG_TAG = "RegisterActivity";
    Button backBtn, registerBtn;
    EditText email, password, surname, name, retype_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password);
        surname = findViewById(R.id.Surname);
        retype_password = findViewById(R.id.retype_password);
        name = findViewById(R.id.name);
        backBtn = findViewById(R.id.back_button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check() == true) {
                    RegisterRequestBody requestBody = RegisterRequestBody.builder()
                            .email(email.getText().toString())
                            .password(password.getText().toString())
                            .firstName(name.getText().toString())
                            .lastName(surname.getText().toString())
                            .admin(false)
                            .build();
                    HttpRequestData<RegisterRequestBody> requestData = HttpRequestData.<RegisterRequestBody>builder()
                            .requestBody(requestBody)
                            .method(HttpRequestMethods.POST)
                            .build();
                    new RegisterRequestHandler() {
                        @Override
                        protected void onPostExecute(RegisterResponse result) {
                            if (result.getHttpStatusCode() == 400) {
                                Toast.makeText(registerBtn.getContext(),
                                        "Nie można zarejestrować nowego użytkownika, ponieważ nie ma dostępu do internetu",
                                        Toast.LENGTH_LONG).show();
                            } else if (result.getHttpStatusCode() == 201) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(registerBtn.getContext(),
                                        "Uzytkownik zarejestrowany",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("response", result);
                                startActivity(intent);
                                Log.d(REG_TAG, "Result: " + result);
                            }
                        }
                    }.execute(requestData);
                }
            }
        });

    }

    public boolean check() {
        if (checkPassword(password.getText().toString()) &&
                checkName(surname.getText().toString()) &&
                checkName(name.getText().toString()) &&
                checkRetypePassword(password.getText().toString(), retype_password.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String password) {
        String patternPassword = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        if (!password.matches(patternPassword)) {
            Toast.makeText(registerBtn.getContext(),
                    "Weak password",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkName(String name) {
        String patternName = "(?=.*[a-z])(?=.*[A-Z]).{3,}";
        if (!name.matches(patternName)) {
            Toast.makeText(registerBtn.getContext(),
                    "Bad name",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkEmail(String mail) {
        String EMAIL_VERIFICATION = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
        if (!mail.matches(EMAIL_VERIFICATION)) {
            Toast.makeText(registerBtn.getContext(),
                    "Bad mail",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkRetypePassword(String password, String retypePassword) {
        if (!password.equals(retypePassword)) {
            Toast.makeText(registerBtn.getContext(),
                    "Password different",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}

