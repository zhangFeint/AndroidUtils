package com.library.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.library.utils.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import okhttp3.HttpUrl;


/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\11\1 0001 16:49
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class ViewUtils {
    private static ViewUtils viewUtils;
    public int visible = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, invisible = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;//EditText显示（隐藏）密码

    /**
     * 单例模式
     */
    public static ViewUtils getInstance() {
        if (viewUtils == null) {
            viewUtils = new ViewUtils();
        }
        return viewUtils;
    }

    /**
     * 解决ScrollView嵌套Gridview，listview显示不是顶部冲突的解决方法，在ScrollView的父布局中添加如下属性：
     * android:focusable="true"
     * android:focusableInTouchMode="true
     *
     * @param scrollView
     */
    public void setScrollView(ScrollView scrollView) {
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
    }


    /**
     * EditText显示（隐藏）密码   显示：true  隐藏：false
     *
     * @param b
     */
    public void showHidePassword(EditText editText, boolean b) {
        if (b) {
            editText.setInputType(visible);
        } else {
            editText.setInputType(invisible);
        }
    }


    /**
     * TextView 多种颜色显示
     * setMulticolorTextHtml(tv_guize,str);
     *
     * @param textView
     * @param str      "我分享了装备[#CC33FF]轩辕剑[#GGGGGG],而且[#FF0000]这[#GGGGGG][#FF7F00]是[#GGGGGG][#FFFF00]七[#GGGGGG][#00FF00]彩[#GGGGGG][#00FFFF]的[#GGGGGG][#0000FF]颜[#GGGGGG][#8B00FF]色[#GGGGGG],你喜欢吗？70";
     */
    public void setMulticolorText(TextView textView, String str) {
        String REG = "(\\[(\\#[0-9a-fA-F]{6,8})\\])(((?!\\[\\#).)*)(\\[\\#[G]{6,8}\\])"; // 要替换字符串的正则
        String html = str.replaceAll(REG, "<font color=$2>$3</font>");// 替换指定的字符串为html标签
        textView.setText(Html.fromHtml(html));  // 显示到TextView上
    }

    /**
     * TextView 多种颜色显示
     *
     * @param textView
     * @param text     "备注:签收人(张三)";
     * @param colorArr new int[]{Color.RED, Color.BLUE}
     * @param startArr new int[]{0, 7}
     * @param endArr   new int[]{2, 9}
     */
    public static void setMulticolorText(TextView textView, String text, int[] colorArr, int[] startArr, int[] endArr) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (int i = 0; i < colorArr.length; i++) {//备注:显示的是蓝色，，第一参数是颜色，2，3是字符窜下标开始——结束位置，模式
            style.setSpan(new ForegroundColorSpan(colorArr[i]), startArr[i], endArr[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(style);
    }


    /**
     * TextView 多种颜色与点击事件
     *
     * @param context
     * @param str
     * @param strt
     * @param end
     * @param colorId
     */
    public void setTextviewSpanColors(final Context context, String str, int strt, int end, final int colorId) {
        SpannableString spanableInfo = new SpannableString(str);
        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //在此处理点击事件
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(context, colorId)); //设置颜色
            }
        }, strt, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 改变矢量图形的颜色
     *
     * @param activity
     * @param resId    图片id
     * @param colour   颜色
     * @return
     */
    public Drawable resetVectorgraphColor(Activity activity, @DrawableRes int resId, int colour) {
        VectorDrawableCompat vectorDrawableCompat4 = VectorDrawableCompat.create(activity.getResources(), resId, activity.getTheme());
        vectorDrawableCompat4.setTint(activity.getResources().getColor(colour)); //你需要改变的颜色
        return vectorDrawableCompat4;
    }
    /**
     * 获取ListView对应的Adapter
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//         listView.getDividerHeight();//获取子项间分隔符占用的高度
//         params.height;//最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 动态控制gridview的高度
     */
    public void setGridViewHeightBasedOnChildren(GridView gridView, int number) {
        //获取listview的适配器
        ListAdapter listAdapter = gridView.getAdapter();
        int numCollumn = number;
        //item的高度
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if (i % numCollumn == 0) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
    /**
     * @param activity
     * @param gridView
     * @param size                   数据大小
     * @param itemsHorizontalSpacing 列表项水平间距  10
     */
    //gridview横向布局方法
    public void setGridViewHorizontalLayout(Activity activity, GridView gridView, int size, int itemwidth, int itemsHorizontalSpacing) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int itemWidth = (int) (itemwidth * density);
        int allWidth = (int) (itemwidth * size * density) + (size * itemsHorizontalSpacing);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);// 设置GirdView布局参数
        gridView.setColumnWidth(itemWidth);// 列表项宽
        gridView.setHorizontalSpacing(itemsHorizontalSpacing);// 列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);//总长度
    }

    /**
     * webview 显示HTML代码
     *
     * @param webview
     * @param baseUrl 网址
     * @param data    HTML代码
     */
    public void setWebViewHTML(final WebView webview, String baseUrl, String data) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        //设置自适应屏幕，两者合用
        webview.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        webview.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setBlockNetworkImage(false);
        webview.getSettings().setDefaultFontSize(44);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                webview.measure(w, h); //重新测量
            }
        });
        webview.loadDataWithBaseURL(baseUrl, data, "text/html", "UTF-8", null);
    }

    /**
     * Textview显示Html代码  需要universal-image-loader-1.9.5.jar 依赖包
     * https://blog.csdn.net/qq_36455052/article/details/78734734
     * 下载第三方https://github.com/nostra13/Android-Universal-Image-Loader
     *
     * @param activity
     * @param textView
     * @param string   html代码
     */
    public void setTextViewHTML(Activity activity, TextView textView, String string) {
        ImageLoader imageLoader = ImageLoader.getInstance();//ImageLoader需要实例化
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        URLImageParser imageGetter = new URLImageParser(textView);
        textView.setText(Html.fromHtml(string, imageGetter, null));
    }

    public class URLImageParser implements Html.ImageGetter {
        TextView mTextView;
        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText());
                        }
                    });
            return urlDrawable;
        }
    }

    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    /**
     * 控件锚点
     *
     * @param scrollView
     * @param view
     */
    public void setViewTop(ScrollView scrollView, View view) {
        int y = view.getTop();  //顶部距离父容器的Y轴距离
        scrollView.smoothScrollTo(0, y);
    }

    /**
     * listview 数据为空时 显示图片
     * @param activity
     * @param listView
     * @param textView
     * @param drawableId
     * @param text
     */
    public void setListViewEmptyView(Activity activity, ListView listView, TextView textView, @DrawableRes int drawableId, String text) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(activity, drawableId), null, null);
        textView.setText(text);
        listView.setEmptyView(textView);
    }

    /**
     *  下拉列表适配器
     * @param context
     * @param spinner
     * @param adapter
     * @param list
     */
    public void loadSpinnerAdapter(Context context,Spinner spinner, ArrayAdapter<String> adapter, List<String> list){
        //适配器
        adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        //设置样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(adapter);
    }
}
