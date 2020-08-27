package com.example.hicariba.ui.signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hicariba.R;
import com.example.hicariba.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpScreen extends AppCompatActivity {

    TextInputEditText FirstName, LastName, Email, Phone;
    TextInputLayout FirstNameLayout, LastNameLayout, EmailLayout, PhoneLayout;
    Button LogIn, Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        FirstName = findViewById(R.id.textFieldFirstName);
        LastName = findViewById(R.id.textFieldLastName);
        Email = findViewById(R.id.textFieldEmail);
        Phone = findViewById(R.id.textFieldPhone);
        LogIn = findViewById(R.id.loginScreenButton);
        Submit = findViewById(R.id.SubmitButton);
        FirstNameLayout = findViewById(R.id.textFieldFirstNameLayout);
        LastNameLayout = findViewById(R.id.textFieldLastNameLayout);
        EmailLayout = findViewById(R.id.textFieldEmailLayout);
        PhoneLayout = findViewById(R.id.textFieldPhoneLayout);


        LogIn.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         boolean b = checkValidation();
                                         if (b) {
                                             ShowProgressDiscardWarning(v);
                                         } else {
                                             Intent aIntent;
                                             aIntent = new Intent(SignUpScreen.this, LoginActivity.class);
                                             startActivity(aIntent);
                                             finish();
                                         }
                                     }
                                 }
        );

        Submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          boolean b = checkValidation();
                                          Toast.makeText(SignUpScreen.this, b ? "error" : "Ok", Toast.LENGTH_LONG).show();
                                      }
                                  }
        );

    }

    public void removeFocuse(View view) {
        view.clearFocus();
    }

    public boolean checkValidation() {
        int tracker = 0;
        if (TextUtils.isEmpty(FirstName.getText())) {
            FirstNameLayout.setError("First Name is required");
        }else {
            tracker++;
        }
        if (TextUtils.isEmpty(LastName.getText())) {
            LastNameLayout.setError("First Name is required");
        }else {
            tracker++;
        }

        if (TextUtils.isEmpty(Email.getText())) {
            EmailLayout.setError("Email is required");
        }
        else {
            tracker++;
        }
        if (TextUtils.isEmpty(Phone.getText())) {
            PhoneLayout.setError("Phone number is required");
        }
        else {
            tracker++;
        }

        if(tracker > 0){
            return true;
        }else {
            return false;
        }
    }

    public void ShowProgressDiscardWarning(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Discard Sign Up Form changes?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent aIntent;
                        aIntent = new Intent(SignUpScreen.this, LoginActivity.class);
                        startActivity(aIntent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}