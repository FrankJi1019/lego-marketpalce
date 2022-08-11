package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se306project1.R;

public class MainActivity extends AppCompatActivity {

    private class ViewHolder {
        ConstraintLayout signUp, login;
        EditText registerUsernameEditText, registerPasswordEditText, registerConfirmPasswordEditText, loginUsernameEditText, loginPasswordEditText;
        Button registerSignUpButton, registerLoginButton, loginLoginButton, loginSignUpButton;
        TextView test;
    }

    ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vh = new ViewHolder();
        setContentView(R.layout.activity_main);
        vh.registerUsernameEditText = findViewById(R.id.register_username_edit_text);
        vh.registerPasswordEditText = findViewById(R.id.register_password_edit_text);
        vh.registerConfirmPasswordEditText = findViewById(R.id.confirm_password);
        vh.registerLoginButton = findViewById(R.id.register_login_button);
        vh.registerSignUpButton = findViewById(R.id.register_sign_up_button);
        vh.loginUsernameEditText = findViewById(R.id.login_username_edit_text);
        vh.loginPasswordEditText = findViewById(R.id.login_password_edit_text);
        vh.loginLoginButton = findViewById(R.id.login_login_button);
        vh.loginSignUpButton = findViewById(R.id.login_sign_up_button);
        vh.signUp = findViewById(R.id.sign_up_page);
        vh.login = findViewById(R.id.login_page);
        vh.test = findViewById(R.id.test);
        vh.registerUsernameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) || keyEvent.getAction() == KeyEvent.ACTION_UP){
                    String username = vh.registerUsernameEditText.getText().toString();
                    vh.test.setText(username);
                    return true;
                }
                return false;
            }
        });

        vh.registerPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) || keyEvent.getAction() == KeyEvent.ACTION_UP){
                    String password= vh.registerPasswordEditText.getText().toString();
                    vh.test.setText(password);
                    return true;
                }
                return false;
            }
        });

        vh.registerConfirmPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) || keyEvent.getAction() == KeyEvent.ACTION_UP){
                    String password= vh.registerConfirmPasswordEditText.getText().toString();
                    vh.test.setText(password);
                    return true;
                }
                return false;
            }
        });

        vh.registerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.signUp.setVisibility(View.GONE);
                vh.login.setVisibility(View.VISIBLE);
            }
        });

//        TODO
        vh.registerSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPassword();
            }
        });

        vh.loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.signUp.setVisibility(View.VISIBLE);
                vh.login.setVisibility(View.GONE);
            }
        });

//        TODO
        vh.loginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void confirmPassword(){
        if (vh.registerConfirmPasswordEditText.getText() != vh.registerPasswordEditText){
            Toast.makeText(this,"Password and confirm password do not match, please try again", Toast.LENGTH_LONG).show();
        }else{
//            TODO
//            add user to user database
        }
    }

}