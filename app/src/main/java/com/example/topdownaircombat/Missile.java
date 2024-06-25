package com.example.topdownaircombat;

import android.graphics.Canvas;
import android.graphics.Paint;

class Missile {
    public int x, y, radius, color;
    public Missile(int x, int y, int radius, int color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    public void update() {
        y -= 30;  // Speed of missile
    }
    public boolean isOffScreen() {
        return y < 0;
    }
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }
}
