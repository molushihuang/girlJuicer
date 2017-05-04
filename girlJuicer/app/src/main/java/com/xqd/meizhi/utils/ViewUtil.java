package com.xqd.meizhi.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

import java.io.File;
import java.lang.reflect.Field;

/**
 */
public final class ViewUtil {
    private static final String TAG = ViewUtil.class.getSimpleName();

    public static void RemoveParent(View view) {
        ViewParent vp = view.getParent();
        if (null != vp) {
            ((ViewGroup) vp).removeView(view);
        }
    }

  

    public static void showMeasuredCustomDialog(Dialog dialog, int layout) {
        showMeasuredCustomDialog(dialog, layout, true);
    }

    public static void showMeasuredCustomDialog(Dialog dialog, int layout, boolean cancelable) {
        Window dialogWindow = dialog.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layout);
        WindowManager w = dialogWindow.getWindowManager();
        DisplayMetrics dms = new DisplayMetrics();
        w.getDefaultDisplay().getMetrics(dms);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int) (dms.widthPixels * 0.85);
        p.height = dms.heightPixels;
        dialogWindow.setAttributes(p);
        if (!cancelable) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                try {
                    field.set(dialog, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showCustomAlertDialog(AlertDialog dialog, int layout, boolean cancelable) {
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        dialog.setContentView(layout);
        WindowManager w = dialogWindow.getWindowManager();
        DisplayMetrics dms = new DisplayMetrics();
        w.getDefaultDisplay().getMetrics(dms);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int) (dms.widthPixels * 0.8);
        dialogWindow.setAttributes(p);

        if (!cancelable) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                try {
                    field.set(dialog, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showCustomDialog(Dialog dialog, int layout) {
        showCustomDialog(dialog, layout, true);
    }

    public static void showCustomDialog(Dialog dialog, int layout, boolean cancelable) {
        showCustomDialog(dialog, layout, cancelable, null, null, null);
    }

    public static void showCustomDialog(Dialog dialog, int layout, boolean cancelable, Integer type) {
        showCustomDialog(dialog, layout, cancelable, type, null, null);
    }

    /**
     * 显示自定义对话框的方法
     *
     * @param dialog
     * @param builder {@link Builder} a builder to control the dialog's property
     */
    public static void showCustomDialog(Dialog dialog, Builder builder) {
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        if (builder.getWindowType() != null)
            dialogWindow.setType(builder.getWindowType());
        if (null != builder.getGravity()) {
            dialogWindow.setGravity(builder.getGravity());
        }
        if (null != builder.getWindowAnimation())
            dialogWindow.setWindowAnimations(builder.getWindowAnimation());
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (builder.getLayout() != null)
            dialog.setContentView(builder.getLayout());
        if (builder.getLayoutView() != null)
            dialog.setContentView(builder.getLayoutView());
        WindowManager w = dialogWindow.getWindowManager();
        DisplayMetrics dms = new DisplayMetrics();
        w.getDefaultDisplay().getMetrics(dms);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        if (null != builder.getDialogWidth() && builder.getDialogWidth() != 0)
            p.width = builder.getDialogWidth();
        else p.width = (int) (dms.widthPixels * 0.85);
        if (null != builder.getDialogHeight() && builder.getDialogHeight() != 0)
            p.height = builder.getDialogHeight();
        else p.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogWindow.setAttributes(p);
        if (!builder.isCancelable()) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                try {
                    field.set(dialog, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示自定义对话框的方法
     *
     * @param dialog
     * @param layout
     * @param cancelable 是否可以取消
     * @param type       Window Type {@link WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY}
     * @see #showCustomDialog(Dialog dialog, ViewUtil.Builder builder)
     */
    @Deprecated
    public static void showCustomDialog(Dialog dialog, int layout, boolean cancelable, Integer type, Integer dialogWidth, Integer dialogHeight) {
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        if (type != null)
            dialogWindow.setType(type);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layout);
        WindowManager w = dialogWindow.getWindowManager();
        DisplayMetrics dms = new DisplayMetrics();
        w.getDefaultDisplay().getMetrics(dms);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        if (null != dialogWidth)
            p.width = dialogWidth;
        else p.width = (int) (dms.widthPixels * 0.85);
        if (null != dialogHeight)
            p.height = dialogHeight;
        else p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        if (!cancelable) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                try {
                    field.set(dialog, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static void viewHighlighted(View view, boolean isParentState, boolean selected) {
        view.setDuplicateParentStateEnabled(isParentState);
        view.setSelected(selected);
        view.setFocusable(selected);
        view.setPressed(selected);
    }

    public static int convertPXToDIP(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    public static int convertDIPToPX(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    

    private static float _convertDimensionPixel(String st, DisplayMetrics metrics, String unitStr, int unitInt) {
        if (st.endsWith(unitStr)) {
            float value = Float.valueOf(st.substring(0, st.length() - unitStr.length()));
            return TypedValue.applyDimension(unitInt, value, metrics);
        }
        return -1;
    }

   
    public static void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            if(ViewGroup.class.isAssignableFrom(listViewItem.getClass())){//处理listViewItem是ViewGroup子类
//                ViewGroup viewGroup = (ViewGroup) listViewItem;
//                for(int j=0;j<viewGroup.getChildCount();j++){
//                    viewGroup.getChildAt(j).measure(0,0);
//                }
//            }
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void fixGridViewHeight(GridView gridView, float columns, int verticalSpacing) {
        ListAdapter adapter = gridView.getAdapter();
        int totalHeight = 0;
        if (adapter == null || adapter.getCount() == 0)
            return;
        View viewItem;
        int row = (int) Math.ceil(adapter.getCount() / columns);

        for (int i = 0; i < row; i++) {
            viewItem = adapter.getView(i, null, gridView);
            viewItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            totalHeight += viewItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, totalHeight);
            params.height = totalHeight;

        } else params.height = totalHeight;
        verticalSpacing = ViewUtil.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalSpacing);
        int otherSpacing = verticalSpacing * (row - 1);
        params.height += otherSpacing;
        gridView.setLayoutParams(params);
    }

    /*
     * Returns the navigation bar height for the current layout configuration.
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    /**
     * Returns the view container for the ActionBar.
     *
     * @return
     */
    public View getActionBarView(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");

        return view.findViewById(resId);
    }

  
   


    /**
     * @param type  TypedValue...
     * @param value ֵ
     */
    public static int applyDimension(int type, int value) {
        DisplayMetrics dms = new DisplayMetrics();
        dms.setToDefaults();
        int rv = (int) TypedValue.applyDimension(type, value, dms);
        return rv;
    }

   

    private static boolean filePathCheck(String filePath) {
        File dirs = new File(filePath);
        if (!dirs.exists()) {
            boolean re = dirs.mkdirs();
            if (!re) return false;
        }
        return true;


    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取ActionBar的高度
     * Returns the status bar height for the current layout configuration.
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))// 如果资源是存在的、有效的
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

  /*  public static int getStatusBarHeight(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Rect re = new Rect();
        view.getWindowVisibleDisplayFrame(re);
        int h = re.top;
        return h;
    }

    /*
     * Returns the status bar height for the current layout configuration.
     *
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }*/

    public static void setViewMargin(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (params == null)
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        view.setLayoutParams(params);

    }

    /**
     * resize the view
     * 调整视图的大小
     *
     * @param view   视图
     * @param width  宽度
     * @param height 高度
     */
    public static void resizeView(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(width, height);
        } else {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

   
    public static void resizeAbsView(View view, int width, int height) {
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new AbsListView.LayoutParams(width, height);
        } else {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 使父组件不要拦截子View的Touch事件<br>
     * <ul>
     * <li>当ScrollView嵌套EditText，EditText超过maxlines后不能正常上下滚动，需要父级view不拦截子view的touch事件</li>
     * </ul>
     *
     * @param view   目标view
     * @param parent view的最外层的父级
     */
    public static void disallowParentInterceptTouchEvent(View view, final ViewParent parent) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //  Auto-generated method stub
                parent.requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        parent.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 根据图片大小缩放缩放图片保证不变形
     *
     * @param bitmap
     * @param imageView
     */
    public static void scaleBitmap(Bitmap bitmap, ImageView imageView, int width, int height) {

        //计算最佳缩放倍数,以填充宽高为目标
        float scaleX = (float) width / bitmap.getWidth();
        float scaleY = (float) height / bitmap.getHeight();
        float bestScale = scaleX > scaleY ? scaleX : scaleY;
        //以填充高度的前提下，计算最佳缩放倍数

        float subX = (width - bitmap.getWidth() * bestScale) / 2;
        float subY = (height - bitmap.getHeight() * bestScale) / 2;

        Matrix imgMatrix = new Matrix();
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        //缩放最佳大小
        imgMatrix.postScale(bestScale, bestScale);
        //移动到居中位置显示
        imgMatrix.postTranslate(subX, subY);
        //设置矩阵
        imageView.setImageMatrix(imgMatrix);

        imageView.setImageBitmap(bitmap);
    }

    /**
     * 根据屏幕比例返回 对应 的图片高度
     *
     * @param bitmap
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public static float getScaledHeight(Bitmap bitmap, int screenWidth, int screenHeight) {
        //计算最佳缩放倍数,以填充宽高为目标
        float bmpWidth = bitmap.getWidth();
        float bmpHeight = bitmap.getHeight();
        float bitmapHeight = screenWidth * bmpHeight / bmpWidth;
        return bitmapHeight;
        //以填充高度的前提下，计算最佳缩放倍数
    }

    /**
     * OverView:<br>
     * a builder for Dialog Configuration.<br>
     * <ul>
     * <li>Set the dialog width</li>
     * <li>Set the dialog height</li>
     * <li>Set the dialog cancelable</li>
     * <li>Set the dialog's layout</li>
     * <li>Set the dialog's window type</li>
     * <li>Set the dialog's window gravity</li>
     * </ul>
     */
    public static class Builder {
        private Integer dialogWidth;
        private Integer dialogHeight;
        private boolean cancelable;
        private Integer layout;
        private Integer gravity;
        private View layoutView;
        private int windowAnimation;

        public Integer getGravity() {
            return gravity;
        }

        public Builder setGravity(Integer gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * Window Type {@link WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY}
         */
        private Integer windowType;

        public Integer getWindowType() {
            return windowType;
        }

        /**
         * Window Type {@link WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY}
         *
         * @param windowType
         * @return
         */
        public Builder setWindowType(Integer windowType) {
            this.windowType = windowType;
            return this;
        }

        public Integer getDialogWidth() {
            return dialogWidth;
        }

        public Builder setDialogWidth(Integer dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public Builder setDialogAnimation(int dialogAnimation) {
            this.windowAnimation = dialogAnimation;
            return this;
        }

        public Integer getDialogHeight() {
            return dialogHeight;
        }

        public Builder setDialogHeight(Integer dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }


        public View getLayoutView() {
            return layoutView;
        }

        public Builder setLayout(Integer layout) {
            this.layout = layout;
            return this;
        }

        public Integer getLayout() {
            return layout;
        }

        public Builder setLayoutView(View layoutView) {
            this.layoutView = layoutView;
            return this;
        }

        public Integer getWindowAnimation() {
            return windowAnimation;
        }

        public void setWindowAnimation(int anim) {
            this.windowAnimation = anim;
        }
    }


    //Device
    public static class mdpi {
        public static final int width = 480;
        public static final int height = 800;
    }

    public static class hdpi {
        public static final int width = 720;
        public static final int height = 1280;
    }

    public static class xhdpi {
        public static final int width = 1080;
        public static final int height = 1290;
    }
}
