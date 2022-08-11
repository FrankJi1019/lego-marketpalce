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
import com.example.se306project1.database.UserDatabase;
import com.example.se306project1.models.User;
import com.example.se306project1.utilities.UserState;

public class MainActivity extends AppCompatActivity {
    private UserState userState;
    private User user;
    private UserDatabase userDatabase= UserDatabase.getInstance();

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

        vh.registerUsernameEditText.setOnFocusChangeListener((view, focus) -> {
            if(!focus){
                vh.test.setText(getRegisterUsername());
                checkValidUsername();
            }
        });

        vh.registerLoginButton.setOnClickListener(view -> {
            vh.signUp.setVisibility(View.GONE);
            vh.login.setVisibility(View.VISIBLE);
        });

//        TODO
        vh.registerSignUpButton.setOnClickListener(view -> {
            if (validSignUp()){
                createCurrentUser(getRegisterUsername(),getRegisterPassword());
//                userDatabase.addUserToFireStore(getRegisterUsername(),getRegisterPassword());
                vh.registerUsernameEditText.setText("");
                vh.registerPasswordEditText.setText("");
                vh.registerConfirmPasswordEditText.setText("");
                switchToCategoryActivity();
            }
        });

        vh.loginSignUpButton.setOnClickListener(view -> {
            vh.signUp.setVisibility(View.VISIBLE);
            vh.login.setVisibility(View.GONE);
        });

//        TODO
        vh.loginLoginButton.setOnClickListener(view -> {
            if(validLogin()){
                vh.loginUsernameEditText.setText("");
                vh.loginPasswordEditText.setText("");
                createCurrentUser(getLoginUsername(),getLoginPassword());
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
        user = new User(username, password);
        userState.setCurrentUser(user);
    }

    //    TODO
    private boolean validLogin(){
        return true;
    }

    private boolean validSignUp(){
        boolean isValid = checkValidUsername() && !checkEmptyInput() && confirmPassword();
        if(isValid){
            Toast.makeText(this,"CONGRATULATION! YOU ARE A MEMBER NOW!", Toast.LENGTH_LONG).show();
        }
        return isValid;
    }

// TODO
    private boolean checkValidUsername(){
        boolean userExist = userDatabase.isUserExist(getRegisterUsername());
        if(vh.registerUsernameEditText.getText().length()==0){
            Toast.makeText(this,"Please enter your username", Toast.LENGTH_LONG).show();
            return false;
        }else if(userExist){
            Toast.makeText(this,"This username has been used", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkEmptyInput(){
        if(getRegisterPassword().isEmpty() || getConfirmPassword().isEmpty()){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean confirmPassword(){
        if (!getRegisterPassword().equals(getConfirmPassword())){
            Toast.makeText(this,"Password and confirm password do not match, please try again", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private String getRegisterUsername(){
        return vh.registerUsernameEditText.getText().toString();
    }

    private String getRegisterPassword(){
        return vh.registerPasswordEditText.getText().toString();
    }

    private String getConfirmPassword(){
        return vh.registerConfirmPasswordEditText.getText().toString();
    }

    private String getLoginUsername(){
        return vh.loginUsernameEditText.getText().toString();
    }

    private String getLoginPassword(){
        return vh.loginPasswordEditText.getText().toString();
    }

}