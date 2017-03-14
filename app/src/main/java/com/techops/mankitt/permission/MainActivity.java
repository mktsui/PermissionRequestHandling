package com.techops.mankitt.permission;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Case> cases;
    private RecyclerView case_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        case_rv = (RecyclerView)findViewById(R.id.case_rv);
        case_rv.setLayoutManager(new LinearLayoutManager(this));
        case_rv.setHasFixedSize(true);

        initializeCase();
        initializeCaseRVAdapter();
    }

    private void initializeCase(){
        cases = new ArrayList<>();
        // Add new cases to cardview
        cases.add(new Case("Sample1", "ID: 1", R.drawable.sample_img));
        cases.add(new Case("Sample1", "ID: 1", R.drawable.sample_img));
    }

    private void initializeCaseRVAdapter(){
        Case_RVAdapter adapter = new Case_RVAdapter(cases);
        case_rv.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
