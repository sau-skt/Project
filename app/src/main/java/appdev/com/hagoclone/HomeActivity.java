package appdev.com.hagoclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ammarptn.bottomnavbadge.Badge;
import com.ammarptn.bottomnavbadge.NotificationBadge;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import appdev.com.hagoclone.adapters.ChatroomAdapter;
import appdev.com.hagoclone.homepagefragments.GamesAndChatroomFragment;
import appdev.com.hagoclone.homepagefragments.InboxMessageFragment;
import appdev.com.hagoclone.homepagefragments.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView upper,lower;
    private BottomNavigationView bottomNavigationView;
    private Badge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        WidgetsRegistration();
        CreateUser();
        DefaultFragmentsMethod();
        BottomNavigationViewMethod();//For Bottom Navigation View


    }

    private void CreateUser() {

    }

    private void WidgetsRegistration() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        upper = (RecyclerView) findViewById(R.id.reminderrecyclerview);
        lower = (RecyclerView) findViewById(R.id.randomrecyclerview);
    }

    private void DefaultFragmentsMethod() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new GamesAndChatroomFragment()).commit();
    }

    private void BottomNavigationViewMethod() {
        badge = NotificationBadge.getBadge(bottomNavigationView, 1);
        badge.setNumber(2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GamesAndChatroomFragment()).commit();
                }
                if (item.getItemId() == R.id.nav_favourites) {
                    badge.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new InboxMessageFragment()).commit();
                }
                if (item.getItemId() == R.id.nav_search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SettingsFragment()).commit();
                }
                return true;
            }
        });
    }
}
