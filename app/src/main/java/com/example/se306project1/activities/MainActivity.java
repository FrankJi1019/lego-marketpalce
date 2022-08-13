package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.se306project1.R;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.database.FireStoreCallback;

import com.example.se306project1.database.UserDatabase;
import com.example.se306project1.models.User;
import com.example.se306project1.utilities.PasswordEncripter;
import com.example.se306project1.utilities.UserState;

public class MainActivity extends AppCompatActivity {
    private UserState userState;
    private User user;
    private UserDatabase userDatabase= UserDatabase.getInstance();

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), MainActivity.class);
        activity.startActivity(intent);
    }

    private class ViewHolder {
        ConstraintLayout signUp, login;
        EditText registerUsernameEditText, registerPasswordEditText, registerConfirmPasswordEditText, loginUsernameEditText, loginPasswordEditText;
        Button registerSignUpButton, registerLoginButton, loginLoginButton, loginSignUpButton;
    }

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createView();
        vh.registerLoginButton.setOnClickListener(view -> {getLoginPage();});
        vh.loginSignUpButton.setOnClickListener(view -> {getSignUpPage();});
        vh.registerUsernameEditText.setOnFocusChangeListener((view, focus) -> {
           if(!focus){ OnUserNotValid();}
        });
        vh.registerSignUpButton.setOnClickListener(view -> {OnUserSignUp();});
        vh.loginLoginButton.setOnClickListener(view -> {OnUserLogin();});
    }

    private void createView(){
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
    }



    private void switchToCategoryActivity(){
        Intent categoryIntent = new Intent(getBaseContext(), CategoryActivity.class);
        startActivity(categoryIntent);
    }


    private void getLoginPage(){
        vh.signUp.setVisibility(View.GONE);
        vh.login.setVisibility(View.VISIBLE);
        vh.registerUsernameEditText.setText("");
        vh.registerPasswordEditText.setText("");
        vh.registerConfirmPasswordEditText.setText("");
    }

    private void getSignUpPage(){
        vh.signUp.setVisibility(View.VISIBLE);
        vh.login.setVisibility(View.GONE);
        vh.loginUsernameEditText.setText("");
        vh.loginPasswordEditText.setText("");
    }

    private void OnUserNotValid(){
        userDatabase.isUserExist(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                boolean isExist = (Boolean) value;
                if(isExist){
                    usernameIsUsed();
                }
            }}, getRegisterUsername());
    }

    private void OnUserLogin(){
            if(checkEmptyLogin()){
                userDatabase.isUserExist(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        boolean isExist = (Boolean) value;
                        if(!isExist){
                            userNotFound();
                        }
                    }
                }, getLoginUsername());
                userDatabase.isLoginValid(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        boolean isValid = (Boolean) value;
                        userLogin(isValid, getLoginUsername(), getLoginPassword());
                    }
                }, getLoginUsername(), getLoginPassword());
            }
    }

    private void OnUserSignUp(){
        if(checkRegisterEmptyInput()){
            userDatabase.isUserExist(new FireStoreCallback() {
                @Override
                public <T> void Callback(T value) {
                    boolean isExist = (Boolean) value;
                    if(isExist){
                        usernameIsUsed();
                    }else if (checkConfirmPassword()){
                        userSignUp(getRegisterUsername(), getRegisterPassword());
                    }
                }
            }, getRegisterUsername());
        }
    }

    private void userLogin(boolean isValid, String username, String password){
        if (isValid){
            createCurrentUser(username);
            Toast.makeText(this, "Successfully login", Toast.LENGTH_SHORT).show();
            switchToCategoryActivity();
            vh.loginUsernameEditText.setText("");
            vh.loginPasswordEditText.setText("");
        }else{
            Toast.makeText(this, "The combination of password and username is incorrect, please try agian", Toast.LENGTH_SHORT).show();
        }
    }

    private void userSignUp(String username, String password){
        Toast.makeText(this,"CONGRATULATION! YOU ARE A MEMBER NOW!", Toast.LENGTH_SHORT).show();
        String encryptedPassword = getEncryptedPassword(password);
        createCurrentUser(username);
        userDatabase.addUserToFireStore(username,encryptedPassword);
        vh.registerUsernameEditText.setText("");
        vh.registerPasswordEditText.setText("");
        vh.registerConfirmPasswordEditText.setText("");
        switchToCategoryActivity();
    }

    private boolean checkEmptyLogin(){
        if(vh.loginUsernameEditText.getText().length()==0){
            Toast.makeText(this,"Please enter your username", Toast.LENGTH_SHORT).show();
            return false;
        }else if(vh.loginPasswordEditText.getText().length() == 0){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createCurrentUser(String username){
        userState = UserState.getInstance();
        user = new User(username);
        userState.setCurrentUser(user);
    }

    private void userNotFound(){
        Toast.makeText(this, "User not found, please try again", Toast.LENGTH_SHORT).show();
    }

    private void usernameIsUsed(){
        Toast.makeText(this,"This username has been used", Toast.LENGTH_SHORT).show();
    }

    private boolean checkRegisterEmptyInput(){
        if(vh.registerUsernameEditText.getText().length()==0) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return false;
        }else if(getRegisterPassword().isEmpty() || getConfirmPassword().isEmpty()){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkConfirmPassword(){
        if (!getRegisterPassword().equals(getConfirmPassword())){
            Toast.makeText(this,"Password and confirm password do not match, please try again", Toast.LENGTH_SHORT).show();
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

    private String getEncryptedPassword(String password){
        return PasswordEncripter.hashPassword(password);
    }

}