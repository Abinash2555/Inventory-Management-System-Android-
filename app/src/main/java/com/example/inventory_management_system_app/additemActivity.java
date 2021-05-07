package com.example.inventory_management_system_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class additemActivity extends AppCompatActivity {
    private EditText itemname, itemcategory, itemprice;
    private TextView itembarcode;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        resulttextview = findViewById(R.id.barcodeview);
        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        itemname = findViewById(R.id.edititemname);
        itemcategory = findViewById(R.id.editcategory);
        itemprice = findViewById(R.id.editprice);
        itembarcode = findViewById(R.id.barcodeview);


        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });

    }

    public void additem() {
        String itemnameValue = itemname.getText().toString();
        String itemcategoryValue = itemcategory.getText().toString();
        String itempriceValue = itemprice.getText().toString();
        String itembarcodeValue = itembarcode.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        if (itembarcodeValue.isEmpty()) {
            itembarcode.setError("It's Empty");
            itembarcode.requestFocus();
            return;
        }


        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(itempriceValue)) {

            Items items = new Items(itemnameValue, itemcategoryValue, itempriceValue, itembarcodeValue);
            databaseReference.child(resultemail).child("Items").child(itembarcodeValue).setValue(items);
            databaseReferencecat.child(resultemail).child("ItemByCategory").child(itemcategoryValue).child(itembarcodeValue).setValue(items);
            itemname.setText("");
            itembarcode.setText("");
            itemprice.setText("");
            itembarcode.setText("");
            Toast.makeText(additemActivity.this, itemnameValue + " Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(additemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}