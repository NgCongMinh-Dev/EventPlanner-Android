package com.htw.project.eventplanner.ViewController.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.htw.project.eventplanner.Business.GroupConversationBusiness;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.htw.project.eventplanner.ViewController.Controller.ErrorDialog;

public class LoginActivity extends AppCompatActivity {

    private GroupConversationBusiness gcBusiness;

    private EditText usernameEditText;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_login);
        setContentView(R.layout.activity_login);

        gcBusiness = new GroupConversationBusiness();

        bindView();
    }

    private void bindView() {
        usernameEditText = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            gcBusiness.joinRoom(username, new ApiCallback() {
                @Override
                public void onSuccess(Object response) {
                    switchToMainActivity();
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() -> {
                        System.err.println(message);
                        new ErrorDialog(LoginActivity.this).show();
                    });

                }
            });
        });
    }

    private void switchToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}