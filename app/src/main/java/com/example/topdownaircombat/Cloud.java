package com.example.topdownaircombat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

class Cloud {
    private int x, y, size, color;
    Bitmap bmp;
    public Cloud(int x, int y, int size, int color, Context context) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
        bmp = Bitmap.createScaledBitmap(bmp, 150, 150, false);
    }
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawBitmap(bmp, x, y, paint);
    }
}