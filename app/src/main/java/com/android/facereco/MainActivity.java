package com.android.facereco;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import com.kongqw.permissionslibrary.PermissionsManager;

public class MainActivity extends BaseActivity {
    private PermissionsManager mPermissionsManager;
    // 要校验的权限
    private final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    // 识别请求码
    private final int REQUEST_CODE_DETECTION = 0;
    // 追踪请求码
    private final int REQUEST_CODE_TRACK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionsManager = new PermissionsManager(this) {
            @Override
            public void authorized(int i) {
                switch (i){
                    case REQUEST_CODE_DETECTION:
                        startActivity(new Intent(MainActivity.this,ObjectDetectingActivity.class));
                        break;
                    case REQUEST_CODE_TRACK:
                        startActivity(new Intent(MainActivity.this,ObjectTrackingActivity.class));
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void noAuthorization(int i, String[] strings) {
                showPermissionDialog();
            }

            @Override
            public void ignore(int i) {
                //Android 6.0 以下不进行权限检查
                authorized(i);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsManager.recheckPermissions(requestCode,permissions,grantResults);
    }

    //目标检测
    public  void onDetecting(View view){
        mPermissionsManager.checkPermissions(REQUEST_CODE_DETECTION,PERMISSIONS);
    }
    //目标追踪
    public  void onTracking(View view){
        mPermissionsManager.checkPermissions(REQUEST_CODE_TRACK,PERMISSIONS);
    }
}
