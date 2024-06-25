package com.example.topdownaircombat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

class EnemyPlane {
    private int x, y, width, height, color;
    Bitmap bmp;
    public EnemyPlane(int x, int y, int width, int height, int color, Context context) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyplane);
        bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
    }
    public void update(int screenHeight, int screenWidth) {
        y += 5;  // Speed of enemy plane
        if (y > screenHeight) {
            reset(screenWidth);  // Pass screenWidth to reset method
        }
    }
    public void reset(int screenWidth) {
        Random random = new Random();
        x = random.nextInt(screenWidth - width);  // Reset position at random x within the screen
        y = -height;  // Position above the screen
    }
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawBitmap(bmp, x, y, paint);
    }
    public boolean checkCollisionWithMissile(Missile missile) {
        return  ((missile.x > x -missile.radius) &&
                (missile.x < x + width+missile.radius) &&
                (missile.y >= y - missile.radius) &&
                ( missile.y< y+height+missile.radius));
    }
    public boolean checkCollisionWithMyAirplane(MyPlane airplane) {
        return x < airplane.getX() + airplane.width &&
                x + width > airplane.getX() &&
                y < airplane.getY() + airplane.height &&
                y + height > airplane.getY();
    }
}

