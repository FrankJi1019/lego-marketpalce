package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se306project1.R;
import com.example.se306project1.models.User;
import com.example.se306project1.utilities.UserState;

public class MainActivity extends AppCompatActivity {
    private String registerUsername, registerPassword, confirmPassword, loginUsername, loginPassword;
    private UserState userState;
    private User user = new User();

    private class ViewHolder {
        ConstraintLayout signUp, login;
        EditText registerUsernameEditText, registerPasswordEditText, registerConfirmPasswordEditText, loginUsernameEditText, loginPasswordEditText;
        Button registerSignUpButton, registerLoginButton, loginLoginButton, loginSignUpButton;
        TextView test;
    }

    private ViewHolder vh = new ViewHolder();


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

        vh.registerUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                registerUsername = vh.registerUsernameEditText.getText().toString();
                if(!focus){
                    vh.test.setText(registerUsername);
                    checkValidUsername();
                }
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
                if (validSignUp()){
                    createCurrentUser(registerUsername, registerPassword);
//                    add new user to database
                    vh.registerUsernameEditText.setText("");
                    vh.registerPasswordEditText.setText("");
                    vh.registerConfirmPasswordEditText.setText("");
                    switchToCategoryActivity();
                }
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
                loginUsername = vh.loginUsernameEditText.getText().toString();
                loginPassword = vh.loginPasswordEditText.getText().toString();
                createCurrentUser(loginUsername,loginPassword);

                switchToCategoryActivity();
            }
        });
    }

    private void switchToCategoryActivity(){
        Intent categoryIntent = new Intent(getBaseContext(), CategoryActivity.class);
        startActivity(categoryIntent);
    }

    private void createCurrentUser(String username, String password){
        userState = UserState.getInstance();
        user.setUsername(username);
        user.setPassword(password);
        userState.setCurrentUser(user);
    }

    private boolean validSignUp(){
        boolean isValid = (checkValidUsername() && !checkEmptyInput() && confirmPassword())? true:false;
        Toast.makeText(this,"CONGRATULATION! YOU ARE A MEMBER NOW!", Toast.LENGTH_LONG).show();
        return isValid;
    }

// TODO
    private boolean checkValidUsername(){
        registerUsername = vh.registerUsernameEditText.getText().toString();
        if(vh.registerUsernameEditText.getText().length()==0){
            Toast.makeText(this,"Please enter your username", Toast.LENGTH_LONG).show();
            return false;
        }else if(registerUsername.equals("cc")){
            Toast.makeText(this,"This username has been used", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkEmptyInput(){
        if(vh.registerPasswordEditText.getText().length() == 0 || vh.registerConfirmPasswordEditText.getText().length() == 0){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean confirmPassword(){
        registerPassword = vh.registerPasswordEditText.getText().toString();
        confirmPassword = vh.registerConfirmPasswordEditText.getText().toString();
        if (!registerPassword.equals(confirmPassword)){
            Toast.makeText(this,"Password and confirm password do not match, please try again", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

//    TODO
    private void checkLogin(){

    }

}