package com.example.topdownaircombat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(gameView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gameView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public class GameView extends View implements SensorEventListener {
        private List<Cloud> clouds;
        private List<EnemyPlane> enemyPlanes;
        private MyPlane myplane;
        private Missile missile;
        private Paint paint;
        private Random random = new Random();
        private int screenWidth, screenHeight;
        private int score = 0;
        private boolean isGameOver = false;

        public GameView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);

            myplane = new MyPlane(500, 1500, 150, 150, Color.BLUE, context);
            clouds = new ArrayList<>();
            enemyPlanes = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                enemyPlanes.add(new EnemyPlane(random.nextInt(700), -100, 100, 100, Color.RED, context));
            }

            for (int i = 0; i < 15; i++) {
                clouds.add(new Cloud(random.nextInt(700), random.nextInt(2000), 100, Color.LTGRAY, context));
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            screenWidth = w;
            screenHeight = h;
//            myAirplane.setScreenWidth(screenWidth);
            myplane.setScreenSize(screenWidth, screenHeight);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!isGameOver) {
                canvas.drawColor(Color.CYAN);

                for (Cloud cloud : clouds) {
                    cloud.draw(canvas, paint);
                }

                synchronized (enemyPlanes) {
                    for (EnemyPlane enemy : enemyPlanes) {
                        enemy.update(screenHeight, screenWidth);
                        enemy.draw(canvas, paint);
                        if (missile != null && enemy.checkCollisionWithMissile(missile)) {
                            enemy.reset(screenWidth);  // Ensure we pass screenWidth here as well
                            missile = null;
                            score++;
                        }
                        if (enemy.checkCollisionWithMyAirplane(myplane)) {
                            isGameOver = true;
                        }
                    }
                }

                if (missile != null) {
                    missile.update();
                    missile.draw(canvas, paint);
                    if (missile.isOffScreen()) {
                        missile = null;
                    }
                }

                myplane.draw(canvas, paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(40);
                canvas.drawText("Score: " + score, 20, 50, paint);
            } else {
                paint.setTextSize(60);
                canvas.drawText("Game Over", screenWidth / 2 - 100, screenHeight / 2, paint);
            }
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && missile == null && !isGameOver) {
                missile = new Missile(myplane.getX() + myplane.width / 2, myplane.getY(), 10, Color.YELLOW);
                return true;
            }
            return super.onTouchEvent(event);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (!isGameOver) {
                myplane.move(event.values[0], event.values[1]);
                invalidate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Not used
        }
    }
}
