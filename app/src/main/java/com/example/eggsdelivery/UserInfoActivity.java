package com.example.eggsdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoActivity extends AppCompatActivity {
    String userID;
    private DatabaseReference databaseReference;

    EditText txtName, txtPhone, txtAddress;

    private boolean isComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        userID = getIntent().getStringExtra("userID");
        databaseReference = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.btnContinue).setOnClickListener(v->{
            if(!txtName.getText().toString().trim().equals("")) {
                if(!txtPhone.getText().toString().trim().equals("")) {
                    if(txtPhone.getText().toString().trim().length() == 11) {
                        if(!txtAddress.getText().toString().trim().equals("")) {
                            User user = new User(txtName.getText().toString(), txtPhone.getText().toString(), txtAddress.getText().toString());
                            isComplete = true;
                            databaseReference.child("users").child(this.userID).setValue(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            txtAddress.setError(getString(R.string.ADDRESS_ERROR));
                        }
                    }else{
                        txtPhone.setError(getString(R.string.PHONE_NUM_COUNT_ERROR));
                    }
                }else{
                    txtPhone.setError(getString(R.string.PHONE_ERROR));
                }
            }else {
                txtName.setError(getString(R.string.NAME_ERROR));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isComplete == false)
            FirebaseAuth.getInstance().signOut();
    }
}