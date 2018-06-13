package com.example.list.yyutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.list.yyutil.util.Common;
import com.example.list.yyutil.util.RuntimePermissionsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RuntimePermissionsManager runtimePermissionsManager = new RuntimePermissionsManager(this, true);
        runtimePermissionsManager.requestPermissions(RuntimePermissionsManager.requestedPermissions, new RuntimePermissionsManager.RequestCallback() {
            @Override
            public void requestSuccess() {
                Common.log("permission request ok");
            }

            @Override
            public void requestFailed() {
                Common.log("permission request ok");
                finish();
            }
        });
    }
}
