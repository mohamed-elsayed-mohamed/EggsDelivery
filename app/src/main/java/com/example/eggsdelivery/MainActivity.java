package com.example.eggsdelivery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Order userOrder;
    EditText txtQuantity1, txtQuantity2, txtQuantity3;
    TextView lblPrice1, lblPrice2, lblPrice3, lblTotalPrice1, lblTotalPrice2, lblTotalPrice3;

    private int quantityValue, price1, price2, price3;
    private String note1 = "", note2 = "", note3 = "";
    private Dialog noteDialog, confirmDialog;

    private DatabaseReference databaseReference, pricesReference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDialog = new Dialog(this);
        noteDialog.setContentView(R.layout.add_note);
        noteDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.corner_radius_10));

        confirmDialog = new Dialog(this);
        confirmDialog.setContentView(R.layout.confirm_order);
        confirmDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.corner_radius_10));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        pricesReference = FirebaseDatabase.getInstance().getReference("prices");
        pricesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price1 = snapshot.child("price1").getValue(Integer.class);
                price2 = snapshot.child("price2").getValue(Integer.class);
                price3 = snapshot.child("price3").getValue(Integer.class);

                lblPrice1.setText(price1 + " " + getString(R.string.LE));
                lblPrice2.setText(price2 + " " + getString(R.string.LE));
                lblPrice3.setText(price3 + " " + getString(R.string.LE));

                lblTotalPrice1.setText(getString(R.string.TOTAL) + " " + price1 * Integer.parseInt(txtQuantity1.getText().toString()) + " " + getString(R.string.LE));
                lblTotalPrice2.setText(getString(R.string.TOTAL) + " " + price2 * Integer.parseInt(txtQuantity2.getText().toString()) + " " + getString(R.string.LE));
                lblTotalPrice3.setText(getString(R.string.TOTAL) + " " + price3 * Integer.parseInt(txtQuantity3.getText().toString()) + " " + getString(R.string.LE));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("prices").child("price1").setValue(50);

        initComponents();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponents() {
        txtQuantity1 = findViewById(R.id.txtQuantity1);
        txtQuantity2 = findViewById(R.id.txtQuantity2);
        txtQuantity3 = findViewById(R.id.txtQuantity3);

        lblTotalPrice1 = findViewById(R.id.lblTotalPrice1);
        lblTotalPrice2 = findViewById(R.id.lblTotalPrice2);
        lblTotalPrice3 = findViewById(R.id.lblTotalPrice3);

        txtQuantity1.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtQuantity1.getText().toString().equals("")) {
                txtQuantity1.setText("1");
            } else if (Integer.parseInt(txtQuantity1.getText().toString()) == 0) {
                txtQuantity1.setText("1");
            }
        });

        txtQuantity2.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtQuantity2.getText().toString().equals("")) {
                txtQuantity2.setText("1");
            } else if (Integer.parseInt(txtQuantity2.getText().toString()) == 0) {
                txtQuantity2.setText("1");
            }
        });

        txtQuantity3.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtQuantity3.getText().toString().equals("")) {
                txtQuantity3.setText("1");
            } else if (Integer.parseInt(txtQuantity3.getText().toString()) == 0) {
                txtQuantity3.setText("1");
            }
        });

        txtQuantity1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtQuantity1.getText().toString().equals("") && Integer.parseInt(txtQuantity1.getText().toString()) != 0) {
                    lblTotalPrice1.setText(getString(R.string.TOTAL) + " " + price1 * Integer.parseInt(txtQuantity1.getText().toString()) + " " + getString(R.string.LE));
                } else {
                    lblTotalPrice1.setText(getString(R.string.TOTAL) + " 0 " + getString(R.string.LE));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtQuantity2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtQuantity2.getText().toString().equals("") && Integer.parseInt(txtQuantity2.getText().toString()) != 0) {
                    lblTotalPrice2.setText(getString(R.string.TOTAL) + " " + price2 * Integer.parseInt(txtQuantity2.getText().toString()) + " " + getString(R.string.LE));
                } else {
                    lblTotalPrice2.setText(getString(R.string.TOTAL) + " 0 " + getString(R.string.LE));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtQuantity3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtQuantity3.getText().toString().equals("") && Integer.parseInt(txtQuantity3.getText().toString()) != 0) {
                    lblTotalPrice3.setText(getString(R.string.TOTAL) + " " + price3 * Integer.parseInt(txtQuantity3.getText().toString()) + " " + getString(R.string.LE));
                } else {
                    lblTotalPrice3.setText(getString(R.string.TOTAL) + " 0 " + getString(R.string.LE));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lblPrice1 = findViewById(R.id.lblPrice1);
        lblPrice2 = findViewById(R.id.lblPrice2);
        lblPrice3 = findViewById(R.id.lblPrice3);

        lblPrice1.setText(price1 + " " + getString(R.string.LE));
        lblPrice2.setText(price2 + " " + getString(R.string.LE));
        lblPrice3.setText(price3 + " " + getString(R.string.LE));

        lblTotalPrice1.setText(getString(R.string.TOTAL) + " " + price1 + " " + getString(R.string.LE));
        lblTotalPrice2.setText(getString(R.string.TOTAL) + " " + price2 + " " + getString(R.string.LE));
        lblTotalPrice3.setText(getString(R.string.TOTAL) + " " + price3 + " " + getString(R.string.LE));

        findViewById(R.id.btnMinus1).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity1.getText().toString());
            if (quantityValue > 1) {
                txtQuantity1.setText(--quantityValue + "");
            }
        });

        findViewById(R.id.btnMinus2).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity2.getText().toString());
            if (quantityValue > 1) {
                txtQuantity2.setText(--quantityValue + "");
            }
        });

        findViewById(R.id.btnMinus3).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity3.getText().toString());
            if (quantityValue > 1) {
                txtQuantity3.setText(--quantityValue + "");
            }
        });

        findViewById(R.id.btnPlus1).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity1.getText().toString());
            if (quantityValue < 99) {
                txtQuantity1.setText(++quantityValue + "");
            }
        });

        findViewById(R.id.btnPlus2).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity2.getText().toString());
            if (quantityValue < 99) {
                txtQuantity2.setText(++quantityValue + "");
            }
        });

        findViewById(R.id.btnPlus3).setOnClickListener(v -> {
            quantityValue = Integer.parseInt(txtQuantity3.getText().toString());
            if (quantityValue < 99) {
                txtQuantity3.setText(++quantityValue + "");
            }
        });

        findViewById(R.id.btnNote1).setOnClickListener(v -> {
            EditText txtNote = noteDialog.findViewById(R.id.txtNote);
            txtNote.setText(note1);
            noteDialog.findViewById(R.id.btnOK).setOnClickListener(view -> {
                note1 = txtNote.getText().toString();
                Toast.makeText(this, R.string.SAVE_NOTE, Toast.LENGTH_LONG).show();
                noteDialog.dismiss();
            });

            noteDialog.show();
        });

        findViewById(R.id.btnNote2).setOnClickListener(v -> {
            EditText txtNote = noteDialog.findViewById(R.id.txtNote);
            txtNote.setText(note2);
            noteDialog.findViewById(R.id.btnOK).setOnClickListener(view -> {
                note2 = txtNote.getText().toString();
                Toast.makeText(this, R.string.SAVE_NOTE, Toast.LENGTH_LONG).show();
                noteDialog.dismiss();
            });
            noteDialog.show();

        });

        findViewById(R.id.btnNote3).setOnClickListener(v -> {
            EditText txtNote = noteDialog.findViewById(R.id.txtNote);
            txtNote.setText(note3);
            noteDialog.findViewById(R.id.btnOK).setOnClickListener(view -> {
                note3 = txtNote.getText().toString();
                Toast.makeText(this, R.string.SAVE_NOTE, Toast.LENGTH_LONG).show();
                noteDialog.dismiss();
            });
            noteDialog.show();

        });

        findViewById(R.id.btnDone1).setOnClickListener(v -> {
            ImageView imageType = (ImageView) confirmDialog.findViewById(R.id.imageType);
            imageType.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageType.setImageDrawable(getDrawable(R.drawable.type1));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTypeName)).setText(R.string.LBL_TYPE1_Name);
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmQuantity)).setText(txtQuantity1.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmPrice)).setText(lblPrice1.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTotalPrice)).setText(price1 * Integer.parseInt(txtQuantity1.getText().toString()) + " " + getString(R.string.LE));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmNote)).setText((note1 != null && !note1.equals("") ? note1 : getString(R.string.NA)));

            confirmDialog.findViewById(R.id.btnCancel).setOnClickListener(view -> {
                confirmDialog.dismiss();
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });

            confirmDialog.findViewById(R.id.btnConfirm).setOnClickListener(view -> {
                userOrder = new Order();
                userOrder.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userOrder.type = 1;
                userOrder.note = note1;
                userOrder.quantity = Integer.parseInt(txtQuantity1.getText().toString());
                userOrder.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                addOrderOnFirebase(userOrder);
                reset(userOrder.type);
                confirmDialog.dismiss();
            });
            confirmDialog.show();
            confirmDialog.setOnCancelListener(dialog -> {
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });
        });

        findViewById(R.id.btnDone2).setOnClickListener(v -> {
            ImageView imageType = (ImageView) confirmDialog.findViewById(R.id.imageType);
            imageType.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageType.setImageDrawable(getDrawable(R.drawable.type2));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTypeName)).setText(R.string.LBL_TYPE2_Name);
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmQuantity)).setText(txtQuantity2.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmPrice)).setText(lblPrice2.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTotalPrice)).setText(price2 * Integer.parseInt(txtQuantity2.getText().toString()) + " " + getString(R.string.LE));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmNote)).setText((note2 != null && !note2.equals("") ? note2 : getString(R.string.NA)));

            confirmDialog.findViewById(R.id.btnCancel).setOnClickListener(view -> {
                confirmDialog.dismiss();
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });

            confirmDialog.findViewById(R.id.btnConfirm).setOnClickListener(view -> {
                userOrder = new Order();
                userOrder.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userOrder.type = 2;
                userOrder.note = note2;
                userOrder.quantity = Integer.parseInt(txtQuantity2.getText().toString());
                userOrder.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                addOrderOnFirebase(userOrder);
                reset(userOrder.type);

                confirmDialog.dismiss();
            });
            confirmDialog.show();
            confirmDialog.setOnCancelListener(dialog -> {
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });
        });

        findViewById(R.id.btnDone3).setOnClickListener(v -> {
            ImageView imageType = confirmDialog.findViewById(R.id.imageType);
            imageType.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageType.setImageDrawable(getDrawable(R.drawable.type3));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTypeName)).setText(R.string.LBL_TYPE3_Name);
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmQuantity)).setText(txtQuantity3.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmPrice)).setText(lblPrice3.getText().toString());
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmTotalPrice)).setText(price3 * Integer.parseInt(txtQuantity3.getText().toString()) + " " + getString(R.string.LE));
            ((TextView) confirmDialog.findViewById(R.id.lblConfirmNote)).setText((note3 != null && !note3.equals("") ? note3 : getString(R.string.NA)));

            confirmDialog.findViewById(R.id.btnCancel).setOnClickListener(view -> {
                confirmDialog.dismiss();
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });

            confirmDialog.findViewById(R.id.btnConfirm).setOnClickListener(view -> {
                userOrder = new Order();
                userOrder.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userOrder.type = 3;
                userOrder.note = note3;
                userOrder.quantity = Integer.parseInt(txtQuantity3.getText().toString());
                userOrder.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                addOrderOnFirebase(userOrder);
                reset(userOrder.type);

                confirmDialog.dismiss();
            });
            confirmDialog.show();
            confirmDialog.setOnCancelListener(dialog -> {
                Toast.makeText(this, R.string.CANCEL_OPERATION, Toast.LENGTH_LONG).show();
            });
        });
    }

    private void reset(int type) {
        switch (type) {
            case 1: {
                note1 = "";
                txtQuantity1.setText("1");
            }
            break;
            case 2: {
                note2 = "";
                txtQuantity2.setText("1");
            }
            break;
            case 3: {
                note3 = "";
                txtQuantity3.setText("1");
            }
            break;
        }
    }

    private void addOrderOnFirebase(Order order) {
        databaseReference.child("orders").child(databaseReference.push().getKey()).setValue(order);
        Toast.makeText(this, R.string.CONFIRM_OPERATION, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLanguage:{
                SharedPreferences sharedPreferences = getSharedPreferences(SplashActivity.EGGS_DELIVERY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(item.getTitle().equals("English")) {
                    setLocale("en");
                    editor.putString(SplashActivity.LANG_VALUE, "en");
                }else{
                    setLocale("ar");
                    editor.putString(SplashActivity.LANG_VALUE, "ar");
                }

                editor.commit();
            }break;
            case R.id.itemEdit:{
                startActivity(new Intent(getApplicationContext(), UserInfoActivity.class));
                finish();
            }break;
            case R.id.itemLogout:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.LOGOUT_MESSAGE))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);

        if(menu instanceof MenuBuilder){
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }

        return true;
    }

    public void setLocale(String lang) {
        String languageToLoad  = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }

}