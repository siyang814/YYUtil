package com.example.list.yyutil.util.photo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.list.yyutil.R;

import java.util.ArrayList;

public class LSImagePickerPreviewDeleteActivity extends FragmentActivity implements CheckBoxChange, ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
//    private LSImagePickerPreviewAdapter adapter;
    private PhotoImageGralleryDeleteAdapter adapter;
    private ArrayList<String> totalUris;
    private ArrayList<String> selectedUris;
    private int position;
    private Button checkBox;

    private ImageView okButton;
    private TextView countView, title;

    String curretUrl;

    boolean isReply, isEdit;
    protected ImageView titleLeftImage;

    private String className;

    private Handler handle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handle = new Handler();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lsimage_picker_preview_delete);

        isReply = getIntent().getBooleanExtra("isReply", false);

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        className = getIntent().getStringExtra("CLASSNAME");

        findViewById(R.id.iv_title_bg).setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.gallery);

        okButton = (ImageView) findViewById(R.id.okButton);
        countView = (TextView) findViewById(R.id.countView);
        countView.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.title);

        titleLeftImage = (ImageView) findViewById(R.id.titleLeftImage);
        View titleLeft = findViewById(R.id.titleLeft);
        if (titleLeft != null) {
            titleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftAction();
                }
            });
        }

        if (titleLeftImage != null) {
            titleLeftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftAction();
                }
            });
        }

        if (savedInstanceState != null) {
            totalUris = (ArrayList<String>) savedInstanceState.getSerializable("totalUri");
            selectedUris = (ArrayList<String>) savedInstanceState.getSerializable("selectUri");
            position = savedInstanceState.getInt("position");
        } else {
            totalUris = (ArrayList<String>) getIntent().getSerializableExtra("totalUri");
            selectedUris = (ArrayList<String>) getIntent().getSerializableExtra("selectUri");

            position = getIntent().getIntExtra("position", 0);
        }
        if (selectedUris == null) {
            selectedUris = new ArrayList<String>();
        }

        adapter = new PhotoImageGralleryDeleteAdapter(this, totalUris);

        checkBox = (CheckBox) findViewById(R.id.checkbox);

        checkBox.setOnClickListener(this);
//      如果是编辑页面进来， 不展示
        checkBox.setVisibility(isEdit ? View.GONE : View.VISIBLE);


        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(position + 1);
        if (selectedUris != null && selectedUris.contains(totalUris.get(position))) {
            checkBox.setSelected(true);
        } else {
            checkBox.setSelected(false);
        }

//        countView.setText((position+1) + "/" + totalUris.size());
        title.setText((position+1) + "/" + totalUris.size());

        curretUrl = totalUris.get(position);



    }

    protected void leftAction() {
        if ( isEdit )
        {
            Intent intent = null;
//                        执行关闭
            if ( !TextUtils.isEmpty(className))
            {
                intent = new Intent();
                intent.setClassName(this, className);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            intent.putExtra("uris", totalUris);
            intent.putExtra("isEdit", isEdit);
            startActivity(intent);
            finish();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("uris", selectedUris);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("totalUri", totalUris);
        outState.putSerializable("selectUri", selectedUris);
        outState.putInt("position", position);
    }


    @Override
    public void onCheckBoxChange(String url) {
        if (selectedUris.contains(url)) {
            selectedUris.remove(url);
            checkBox.setSelected(false);
        } else {
            if (selectedUris.size() >= LSImagePicker.MAX_COUNT) {
                Toast.makeText(this, "你最多能选择" + LSImagePicker.MAX_COUNT + "张照片", Toast.LENGTH_LONG).show();
//                ToastUtil.getInstance().showSelectImage9Toast(this, LSImagePicker.MAX_COUNT+"");
                return;
            }
            selectedUris.add(url);
            checkBox.setSelected(true);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if ( position == 0 )
        {
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(currentPosition, false);
                }
            }, 100);

            return;
        }
        String url = totalUris.get(position - 1);
//        countView.setText((position+1) + "/" + totalUris.size());
        title.setText((position) + "/" + totalUris.size());
        if (!selectedUris.contains(url)) {
            checkBox.setSelected(false);
        } else {
            checkBox.setSelected(true);
        }
        curretUrl = url;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    int currentPosition;
    private boolean preview ()
    {
        if ( isEdit )
        {

            totalUris.remove(curretUrl);

            if ( totalUris.size() == 0 )
            {
                leftAction();
                return isEdit;
            }
            currentPosition = viewPager.getCurrentItem();

            adapter.setList(totalUris);

            viewPager.setCurrentItem(0);

//            if ( currentPosition == totalUris.size() ){
//                viewPager.setCurrentItem(currentPosition);
//            }
//            else if ( currentPosition + 1 <= totalUris.size() )
//            {
//                viewPager.setCurrentItem( currentPosition + 1 );
//            }
//            else
//            {
//                viewPager.setCurrentItem(currentPosition);
//            }



        }
        return isEdit;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        leftAction();
    }

    boolean isclick = false;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.okButton) {
            if ( isclick )
            {
                return;
            }
            isclick = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        isclick = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            if (selectedUris != null && selectedUris.size() > 0) {
                Intent intent = null;

//                是编辑进来的
                if ( preview() )
                {
                    return;
                }

                if ( !TextUtils.isEmpty(className))
                {
                    intent = new Intent();
                    intent.setClassName(this, className);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }

                intent.putExtra("uris", selectedUris);
                intent.putExtra("isEdit", isEdit);
                startActivity(intent);
                finish();
            }
        } else if (v.getId() == R.id.checkbox) {
            if (checkBox.isSelected()) {
                onCheckBoxChange(curretUrl);
            } else {
                onCheckBoxChange(curretUrl);
            }
        }
    }
}
