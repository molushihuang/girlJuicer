package com.anthole.quickdev.ui.MultiImageSelecter;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class demo {
	
//	public final static int REQUEST_IMAGE = 100;
//	ArrayList<String> mSelectPath;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_test);
//		ButterKnife.bind(this);
//	}
//
//	
//
//
//	@OnClick(R.id.btn_choose)
//	public void choosePic(){
//		Intent intent = new Intent(TestActivity.this, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//        // 最大可选择图片数量
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//        // 默认选择
//        if(mSelectPath != null && mSelectPath.size()>0){
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//        }
//        startActivityForResult(intent, REQUEST_IMAGE);
//	}
//	
//	@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE){
//			if (resultCode == RESULT_OK) {
//				mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//			}
//        }
//    }
}
