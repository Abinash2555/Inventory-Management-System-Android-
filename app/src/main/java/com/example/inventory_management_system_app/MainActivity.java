package com.example.inventory_management_system_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Register(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}