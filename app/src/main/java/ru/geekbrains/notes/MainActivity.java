package ru.geekbrains.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.notes.data.CardSource;
import ru.geekbrains.notes.data.CardSourceRemoteImplementation;
import ru.geekbrains.notes.data.CardsSourceResponse;
import ru.geekbrains.notes.observation.Publisher;
import ru.geekbrains.notes.view.Navigation;
import ru.geekbrains.notes.view.NoteFragment;
import ru.geekbrains.notes.view.NotesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private final Publisher publisher = new Publisher();
    private Navigation navigation;

    public Publisher getPublisher() {
        return this.publisher;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    NotesFragment notesFragment;
    // Метод для общения с диалоговыми окнами
    public void onDialogResult(boolean resultDialog){
        if(resultDialog){
            notesFragment.delete(candidate);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("candidate",candidate);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            candidate = savedInstanceState.getInt("candidate",-2);
            candidate = savedInstanceState.getInt("candidate",-2);
        }
    }

    public int candidate=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.navigation = new Navigation(getSupportFragmentManager());

        initSideBar();

            notesFragment= NotesFragment.newInstance();
            this.navigation.addFragment(notesFragment, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment backStackFragment = getSupportFragmentManager()
                .findFragmentById(R.id.notes_container);
        if(backStackFragment instanceof NoteFragment) {
            onBackPressed();
        }
    }

    private void initSideBar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getBaseContext(), "settings selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(getBaseContext(), "about application selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
