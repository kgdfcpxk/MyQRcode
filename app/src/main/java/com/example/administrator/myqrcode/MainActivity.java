package com.example.administrator.myqrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity {

    public static final int ACTIVITY_RESULT_CODE = 0;
    private Button mBtnScan,mBtnMake;
    private TextView mTVText;
    private EditText mETMakeText;
    private ImageView mIVMakeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, ACTIVITY_RESULT_CODE);
            }
        });
        mBtnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mETMakeText.getText().toString();
                if (text != null && text.length() > 0) {
                    Bitmap qrCode = EncodingUtils.createQRCode(text, 500, 500, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                    mIVMakeImg.setImageBitmap(qrCode);
                } else {
                    Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            if (resultCode == RESULT_OK) {
                String result = extras.getString("result");
                mTVText.setText(result);
            } else {
                Toast.makeText(this, "失败==" + resultCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        // 扫码
        mBtnScan = (Button) findViewById(R.id.id_main_btn_scan);
        mTVText = (TextView) findViewById(R.id.id_main_tv_text);
        // 生成二维码
        mBtnMake = (Button) findViewById(R.id.id_main_btn_make);
        mETMakeText = (EditText) findViewById(R.id.id_main_et_make_text);
        mIVMakeImg = (ImageView) findViewById(R.id.id_main_iv_make_img);
    }
}
