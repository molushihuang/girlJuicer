package com.anthole.quickdev.ui.MultiImageSelecter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.anthole.quickdev.R;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.anthole.quickdev.ui.MultiImageSelecter.adapter.PhotoPagerAdapter;
import com.anthole.quickdev.ui.ViewPagerFixed;

import java.util.List;

public class PhotoPreviewActivity extends Activity implements PhotoPagerAdapter.PhotoViewClickListener {
	
	public static final String EXTRA_PHOTOS = "extra_photos";
	public static final String EXTRA_CURRENT_ITEM = "extra_current_item";
	private int currentItem = 0;
	private PhotoPagerAdapter mPagerAdapter;
	ViewPagerFixed mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_preview);
		SystemBarTintInvoke.apply(this, R.color.true_black, true);
		List<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
		mViewPager=(ViewPagerFixed)findViewById(R.id.vp_photos);
		currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
		mPagerAdapter = new PhotoPagerAdapter(this, pathArr);
		mPagerAdapter.setPhotoViewClickListener(this);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(currentItem);
		mViewPager.setOffscreenPageLimit(1);

		mViewPager
				.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
											   float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageSelected(int position) {

					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});
	}

	@Override
	public void OnPhotoTapListener(View view, float v, float v1) {
		finish();
		
	}


}
