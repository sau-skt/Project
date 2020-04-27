package appdev.com.hagoclone.welcomeactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import appdev.com.hagoclone.HomeActivity;
import appdev.com.hagoclone.R;

public class RegisterActivity extends AppCompatActivity {

    //Vars for widgets
    private EditText name, dob;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        WidgetsRegister();
        DatePick();

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || dob.getText().toString().equals("") ){
                    Toast.makeText(RegisterActivity.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdateDatabase();

                }
            }
        });
    }

    private void UpdateDatabase() {
        final DatabaseReference assignuserid = FirebaseDatabase.getInstance().getReference().child("AssignUserId").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        assignuserid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userno = dataSnapshot.getValue().toString();
                DatabaseReference userdetailsempty = FirebaseDatabase.getInstance().getReference().child("UsersData").child(userno);
                userdetailsempty.child("Nickname").setValue(name.getText().toString());
                userdetailsempty.child("Birthday").setValue(dob.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

    public void checkButton(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        final DatabaseReference assignuserid = FirebaseDatabase.getInstance().getReference().child("AssignUserId").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        assignuserid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userno = dataSnapshot.getValue().toString();
                DatabaseReference userdetailsempty = FirebaseDatabase.getInstance().getReference().child("UsersData").child(userno);
                userdetailsempty.child("Gender").setValue(radioButton.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private DatePickerDialog picker;

    private void DatePick() {
        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void WidgetsRegister() {
        name = findViewById(R.id.regusername);
        dob = findViewById(R.id.regdob);
        radioGroup = findViewById(R.id.radioGroup);
        submitbutton = findViewById(R.id.submit_button);
    }
}
