package com.devops.saiprasath.geofence;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * Created by saiprasath on 5/18/2017.
 */

public class Home extends AppCompatActivity {
//    public ToggleButton toggle;
    EditText id_edit,pwd_edit2;
    Button login_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        id_edit = (EditText)findViewById(R.id.editText);
        pwd_edit2 = (EditText)findViewById(R.id.editText2);
        login_btn = (Button)findViewById(R.id.button2);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Intent intent = new Intent(Home.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
                    dialog.setIcon(R.drawable.error)
                            .setCancelable(true);
                    AlertDialog dialog2 = dialog.create();
                    dialog2.show();
                }
            }
        });
    }
    private boolean validate(){
        return (id_edit.getText().toString().equals("admin")&& pwd_edit2.getText().toString().equals("admin"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
        dialog.setIcon(R.drawable.error)
                .setTitle("? Exit ?")
                .setMessage("Stay Back or Stay Away?")
                .setCancelable(true)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog2 = dialog.create();
        dialog2.show();

    }
}
