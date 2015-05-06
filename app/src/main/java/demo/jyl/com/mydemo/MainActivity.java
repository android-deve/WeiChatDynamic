package demo.jyl.com.mydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import demo.jyl.com.mydemo.data.DataCenter;
import demo.jyl.com.mydemo.model.Comment;
import demo.jyl.com.mydemo.model.Dynamic;
import demo.jyl.com.mydemo.view.CircleImageView;
import demo.jyl.com.mydemo.view.EditButtonView;
import demo.jyl.com.mydemo.view.GridViewInScroll;
import demo.jyl.com.mydemo.view.ListViewInScroll;


public class MainActivity extends ActionBarActivity implements EditButtonView.OnButtonClickListener {

    private ListView listView;
    private DisplayImageOptions imageLodeOoptions;
    private EditButtonView editButtonView;
    private EditText editText;
    private InputMethodManager inputMethMgr;
    private String TAG = "TEST";
    private int mPosition;
    private List<Dynamic> dynamicList;
    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoader();
        listView = (ListView)findViewById(R.id.listview);
        editButtonView = (EditButtonView)findViewById(R.id.editButtonView);
        editButtonView.setOnButtonClickListener(this);
        editText = editButtonView.getEditText();
        inputMethMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        dynamicList = DataCenter.getInstance().getDynamicList(this);
        mAdapter = new ListViewAdapter(this, dynamicList);
        listView.setAdapter(mAdapter);

    }


    /**初始化ImageLoader*/
    private void initImageLoader() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        imageLodeOoptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_background)
                .showImageOnFail(R.drawable.default_background)
                /*.resetViewBeforeLoading(true)*/
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
    }

    /**dp转成px*/
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onButtonClick() {
        Log.e(TAG, "onButtonClick");
        String content = editButtonView.getContent();
        if(TextUtils.isEmpty(content)) {
            Toast.makeText(this, getString(R.string.please_input_content), Toast.LENGTH_SHORT).show();
            return;
        }
        Dynamic dynamic = dynamicList.get(mPosition);
        if(dynamic != null){
            List<Comment> commentList = dynamic.getCommentList();
            Comment comment = new Comment();
            comment.setUsername(DataCenter.getInstance().getRandomValue(getResources().getStringArray(R.array.user_name)));// 随机设置一个名字
            comment.setContent(content);
            commentList.add(comment);
            mAdapter.notifyDataSetChanged();
            hideSoftInput(editText);
        }
    }

    class ListViewAdapter extends ArrayAdapter<Dynamic> implements View.OnClickListener {

        public ListViewAdapter(Context context, List<Dynamic> list) {
            super(context, 0, list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_layout, null);
            }
            Dynamic dynamic = getItem(position);
            ImageView imagePraise = (ImageView)convertView.findViewById(R.id.imagePraise);
            ImageView imageComment = (ImageView)convertView.findViewById(R.id.imageComment);
            TextView username = (TextView)convertView.findViewById(R.id.username);
            username.setText(dynamic.getName());
            imagePraise.setOnClickListener(ListViewAdapter.this);
            imageComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPosition = position;
                    showInputSoft();
                }
            });

            final CircleImageView imageLogo = (CircleImageView)convertView.findViewById(R.id.imageLogo);
            int width = dip2px(getContext(), getContext().getResources().getDimension(R.dimen.logo_width));
            ImageSize imageSize = new ImageSize(width, width);// 设置图片大小
            ImageLoader.getInstance().loadImage(dynamic.getLogoUrl(), imageSize, imageLodeOoptions, new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    imageLogo.setImageBitmap(loadedImage);
                }
            });

            GridViewInScroll gridView = (GridViewInScroll)convertView.findViewById(R.id.gridView);
            ListViewInScroll commentList = (ListViewInScroll)convertView.findViewById(R.id.commentList);

            GridViewAdapter mAdapter = new GridViewAdapter(getContext(), dynamic.getImageList());
            CommentAdatpter commentAdatpter = new CommentAdatpter(getContext(), dynamic.getCommentList());
            gridView.setAdapter(mAdapter);
            commentList.setAdapter(commentAdatpter);
            return convertView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imagePraise:
                    Toast.makeText(getContext(), "点赞", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**图片GridView*/
    class GridViewAdapter extends ArrayAdapter<String>{
        private DisplayImageOptions imageLodeOoptions;
        public GridViewAdapter(Context context, List<String> datas) {
            super(context, 0, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item_layout, null);
            }
            String url = getItem(position);
            final ImageView imageview = (ImageView)convertView.findViewById(R.id.imageview);
            int width = dip2px(getContext(), getContext().getResources().getDimension(R.dimen.gridview_item_width));
            ImageSize imageSize = new ImageSize(width, width);// 设置图片大小
            ImageLoader.getInstance().displayImage(url, imageview);

          /*  ImageLoader.getInstance().loadImage(url, imageSize, imageLodeOoptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    Log.e("TEST", imageUri);
                    imageview.setImageBitmap(loadedImage);
                }
            });*/
            return convertView;
        }
    }

    /**评论ListView*/
    class CommentAdatpter extends ArrayAdapter<Comment>{
        public CommentAdatpter(Context context, List<Comment> commentList) {
            super(context, 0, commentList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item_layout, null);
            }
            Comment comment = getItem(position);
            TextView username = (TextView)convertView.findViewById(R.id.username);
            TextView content = (TextView)convertView.findViewById(R.id.comment);

            username.setText(comment.getUsername());
            content.setText(comment.getContent());
            return convertView;
        }
    }

    private void showInputSoft(){
        editButtonView.setVisibility(View.VISIBLE);
        editText.requestFocus();
        inputMethMgr.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /** 下面两个方法，参考：http://blog.csdn.net/mad1989/article/details/25069821 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        Log.e(TAG, "inputMethMgr.isActive() = " + inputMethMgr.isActive());
        Log.e(TAG, "inputMethMgr.isActive(editText) = " + inputMethMgr.isActive(editText));

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
            View v = getCurrentFocus();
            Log.e(TAG, "dispatchTouchEvent getCurrentFocus = " + getCurrentFocus());
            if (isShouldHideInput(editText, ev)) {
                if (inputMethMgr != null) {
                    hideSoftInput(v);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void hideSoftInput(View v) {
        inputMethMgr.hideSoftInputFromWindow(v.getWindowToken(), 0);// 隐藏软键盘
        editButtonView.setVisibility(View.GONE);
        editText.clearFocus();
        editText.setText("");
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditButtonView)) {
            int[] leftTop = { 0, 0 };
//            Log.e(TAG,)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
