package com.htw.project.eventplanner.ViewController.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.htw.project.eventplanner.BuildConfig;
import com.htw.project.eventplanner.Business.EventBusiness;
import com.htw.project.eventplanner.Business.GroupConversationBusiness;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;

public class LoginActivity extends AppCompatActivity {

    private GroupConversationBusiness gcBusiness;

    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
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
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();

            gcBusiness.joinRoom(username, firstName, lastName, new ApiCallback() {
                @Override
                public void onSuccess(Object response) {
                    switchToMainActivity();
                }

                @Override
                public void onError(String message) {
                    // TODO error
                }
            });
        });
    }

    private void switchToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}