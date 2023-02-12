package com.example.usersgse.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersgse.R;
import com.example.usersgse.adapters.UserAdapter;
import com.example.usersgse.interfaces.InterfaceMainActivity;
import com.example.usersgse.models.Users;
import com.example.usersgse.presenters.PresenterMainActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ViewMainActivity extends AppCompatActivity implements InterfaceMainActivity.ViewActivity{
    private UserAdapter userAdapter;

    private   Toolbar toolbar;

    InterfaceMainActivity.PresenterActivity presenterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_top);

        init();
        presenterActivity.bringRetrofitResUsers();
    }

    public void init(){
        presenterActivity = new PresenterMainActivity(this);
        userAdapter = new UserAdapter();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void valorList(ArrayList<Users> users) {
        userAdapter.addUsers(users);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                userAdapter.filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}