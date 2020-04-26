package appdev.com.hagoclone.welcomeactivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appdev.com.hagoclone.HomeActivity;
import appdev.com.hagoclone.R;


public class LoginActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> providers;
    private int MY_REQUEST_CODE = 7117; //Any Number you want
    private Button loginbtn;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WidgetsRegistration();

        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(), //Phone Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() //Google Builder
        );

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInOptions();
            }
        });


    }

    private void WidgetsRegistration() {
        loginbtn = (Button) findViewById(R.id.google_sign_in);
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.LoginTheme)
                .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                filluserdetails();
            }
        }
    }

    private void filluserdetails() {
        final DatabaseReference userid = FirebaseDatabase.getInstance().getReference("userid");
        userid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userno = dataSnapshot.getValue().toString();
                int userids = Integer.parseInt(userno);
                userids = userids + 1;
                userid.setValue(userids);
                Map<String,Object> userdetails = new HashMap<>();

                //Details to be filled in firebase database during login

                userdetails.put("Uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Nickname",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Birthday",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Gender",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Profession",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Hometown",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Bio",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("Diamonds",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userdetails.put("MobileNumber",FirebaseAuth.getInstance().getCurrentUser().getUid());

                DatabaseReference userdetailsempty = FirebaseDatabase.getInstance().getReference().child("UsersData").child(userno);
                userdetailsempty.updateChildren(userdetails);

                DatabaseReference assignuserid = FirebaseDatabase.getInstance().getReference().child("AssignUserId").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                assignuserid.setValue(userno);

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
