package com.example.topdownaircombat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

class MyPlane {
    public int x, y, width, height, color;
    Bitmap bmp;
    public int screenWidth, screenHeight;
    public MyPlane(int x, int y, int width, int height, int color, Context context) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.myplane);
        bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
    }

    public void setScreenSize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawBitmap(bmp, x, y, paint);
    }

    public void move(float dx, float dy) {
        // Horizontal movement
        x -= dx * 4; // Adjust multiplier based on sensitivity preference
        if (x < 0) x = 0;
        if (x > screenWidth - width) x = screenWidth - width;

        // Vertical movement
        y += dy * 3; // Adjust multiplier based on sensitivity preference
        if (y < 0) y = 0;
        if (y > screenHeight - height) y = screenHeight - height;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}