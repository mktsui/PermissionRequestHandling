package com.techops.mankitt.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private List<Case> cases;
    private RecyclerView case_rv;
    public static final String REQ_MESSAGE = "com.techops.mankitt.permission.REQ";

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
        cases.add(new Case("Sample 1", "If permission is denied, action continues without re-triggering the request, and no context is provided"));
        cases.add(new Case("Sample 2", "If permission is denied, context is provided. However the action continues without re-triggering the request"));
        cases.add(new Case("Sample 3", "If permission is denied, the request is re-triggered without giving context"));
        cases.add(new Case("Sample 4", "If 'Always deny' option is selected, no context is provided and the user is redirected to app setting directly"));
        cases.add(new Case("Sample 5", "If 'Always deny' option is selected, user is told to enable the permission from app settings manually"));
    }

    private void initializeCaseRVAdapter(){
        Case_RVAdapter adapter = new Case_RVAdapter(cases);
        case_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new Case_RVAdapter.CaseClickListener(){
            @Override
            public void onItemClick(int position, View v){
                reqPermission(position);
            }
        });
    }


    private void goNextView(int position){
        String caseId = cases.get(position).identifier;
        Intent intent = new Intent(this, PermissionCaseActivity.class);
        intent.putExtra(REQ_MESSAGE,caseId);
        startActivity(intent);
    }

    private void showContextDialog(final int dialogType, final int position){
        String contextContent = null;
        switch (dialogType) {
            case 1:
                contextContent = "The action continues without the permission";
                break;
            case 2:
                contextContent = "Go to the setting page and enable the permission";
                break;
        }
        AlertDialog.Builder contextDialogBuilder = new AlertDialog.Builder(this);
        contextDialogBuilder.setTitle("Permission request is denied");
        contextDialogBuilder.setMessage(contextContent)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (dialogType == 1){
                            goNextView(position);
                        }
                    }
                });
        AlertDialog contextDialog = contextDialogBuilder.create();
        contextDialog.show();
    }

    private void reqPermission(int position){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},position);
    }

    private void reqAction(int reqType){
        switch (reqType) {
            case 0:
                goNextView(reqType);
                break;
            case 1:
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showContextDialog(1, reqType);
                }
                break;
            case 2:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                     PackageManager.PERMISSION_GRANTED) {
                    goNextView(reqType);
                } else if (!(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA))) {
                    reqPermission(reqType);
                }
                break;
            case 3:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
                    goNextView(reqType);
                } else if (!(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA))) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
            case 4:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
                    goNextView(reqType);
                } else if (!(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA))) {
                    showContextDialog(2, reqType);
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        reqAction(requestCode);
    }

    private void checkPermission(){
        int permissionReq = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if ((permissionReq == PackageManager.PERMISSION_GRANTED) ||
             !(ActivityCompat.shouldShowRequestPermissionRationale(this,
                     Manifest.permission.CAMERA))) {
            showResetDialog();
        }
    }

    private void showResetDialog(){
        AlertDialog.Builder resetDialogBuilder = new AlertDialog.Builder(this);
        resetDialogBuilder.setTitle("Camera Permission is already set");
        resetDialogBuilder.setMessage("To make sure samples behave correctly, please reset the permission")
                          .setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                                  Intent intent = new Intent();
                                  intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                  Uri uri = Uri.fromParts("package", getPackageName(), null);
                                  intent.setData(uri);
                                  startActivity(intent);
                              }
                          })
                          .setNegativeButton("Do nothing", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
        AlertDialog resetDialog = resetDialogBuilder.create();
        resetDialog.show();
    }

    @Override
    public void onStart(){
        super.onStart();
        checkPermission();
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
