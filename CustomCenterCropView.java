package com.example.liqiang.mytest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liqiang on 2015/8/6.
 */
public class CustomCenterCropView extends View{

    private int imageSrc;
    public CustomCenterCropView(Context context) {
        super(context);

    }

    public CustomCenterCropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CenterCropViewStyle);
            imageSrc = typedArray.getResourceId(R.styleable.CenterCropViewStyle_imageSrc, 0);
            typedArray.recycle();
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap resizedBitmap;
        //加载需要操作的图片，这里是一张图片
        if(imageSrc==0){
            return;
        }
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), imageSrc);

        //获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        //定义预转换成的图片的宽度和高度
        int viewWidth =getWidth();
        int viewHeight =getHeight();

        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) viewWidth) / width;
        float scaleHeight = ((float) viewHeight) / height;


        // 高宽比之差
        int temp = (viewHeight / viewWidth) - (height/ width);
        if(temp>0){
        // 缩放图片动作
            int width1 = (height * viewWidth) / viewHeight;
            resizedBitmap = Bitmap.createBitmap(bitmapOrg, (width-width1)/2, 0,width1, height);
        }else{
            int height1 = (viewHeight * width) / viewWidth;
            resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, (height-height1)/2,width, height1);
        }

        //画图
        Paint mPaint = new Paint();
        Rect mRect  = new Rect(0,0,viewWidth,viewHeight);
        canvas.drawBitmap(resizedBitmap, mRect,mRect, mPaint);

    }
    //设置图片资源
    public void setImageSrc(int resourseId){
        imageSrc=resourseId;
        postInvalidate();
    }


}
