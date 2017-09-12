package com.xqd.meizhi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anthole.quickdev.R;
import com.bumptech.glide.Glide;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donglua on 15/6/21.
 */
public class PicturePagerAdapter extends PagerAdapter {

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
        void onItemLongClik(String url);
    }

    public PhotoViewClickListener listener;

    private List<String> paths = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PicturePagerAdapter(Context mContext, List<String> paths) {
        this.mContext = mContext;
        this.paths = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_photo_preview, container, false);

        final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.iv_pager);

        final String path = paths.get(position);


        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                if (listener != null) {
                    listener.OnPhotoTapListener(view, v, v1);
                }
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener!=null){
                    listener.onItemLongClik(path);
                }
                return false;
            }
        });

        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .fitCenter()
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }



    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewGroup)container).removeView((View) object);
        object = null;
    }
}
