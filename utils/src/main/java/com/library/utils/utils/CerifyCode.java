package com.library.utils.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * 生成图片验证码
 */
public class CerifyCode {
    private static CerifyCode bmpCode;

    public static CerifyCode getInstance() {
        if (bmpCode == null)
            bmpCode = new CerifyCode();
        return bmpCode;
    }

    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    //默认设置
    private static final int DEFAULT_CODE_LENGTH = 4;//生成代码的长度
    private static final int DEFAULT_FONT_SIZE = 25;//字体大小
    private static final int DEFAULT_LINE_NUMBER = 2;//默认的行号
    private static final int BASE_PADDING_LEFT = 5, RANGE_PADDING_LEFT = 30, BASE_PADDING_TOP = 15, RANGE_PADDING_TOP = 20;//验证码在图片的位置
    private static final int DEFAULT_WIDTH = 150, DEFAULT_HEIGHT = 40;//生成验证码的宽高
    //设置由布局xml决定
    //画布的宽度和高度
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
    //验证码在图片的位置
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;
    //字符,行数;字体大小
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;
    //variables
    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    /**
     * @return 验证码
     */
    //
    public String getCode() {
        return code;
    }

    /**
     * 验证码图片
     * 超链接 URLSpan
     * 文字背景颜色 BackgroundColorSpan
     * 文字颜色
     * 字体大小 AbsoluteSizeSpan
     * 粗体、斜体 StyleSpan
     * 删除线 StrikethroughSpan
     * 下划线 UnderlineSpan
     * 图片 ImageSpan
     * http://blog.csdn.net/ah200614435/article/details/7914459
     */

    public Bitmap createBitmap() {
        padding_left = 0;
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bp);
        code = createCode();
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(font_size);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        for (int i = 0; i < line_number; i++) {
            drawLine(canvas, paint);
        }
        canvas.save();//保存
        canvas.restore();//
        return bp;
    }


    //创建验证码
    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
//      paint.setUnderlineText(true); //true为下划线，false为非下划线
//      paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }
}
