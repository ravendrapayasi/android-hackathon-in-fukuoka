package jp.android.fukuoka.tapstar;

import java.lang.reflect.Array;

import android.content.res.Resources;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.*;

public class TapStarView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private SurfaceHolder holder;
	private Thread thread;
	private Bitmap bm;
	private Bitmap star;
	
	private int starNum = 10;
	private float[][] starMoves = new float[starNum][2];
	/*private Bitmap[] starAry;
	private float px = 0;
	private float py = 0;*/
	
	private int _alpha = 100;
	
	private float touchX = 0;
	private float touchY = 0;
	float[] starPosX = new float[starNum];
	float[] starPosY = new float[starNum];
	
	float moveX = 0;
	float moveY = 0;
	
	public TapStarView(Context context)
	{
		super(context);
		
		Resources r = getResources();
		bm = BitmapFactory.decodeResource(r, R.drawable.icon);
		star = BitmapFactory.decodeResource(r, R.drawable.star);
		
		holder = getHolder();
		holder.addCallback(this);
		holder.setFixedSize(getWidth(), getHeight());
		
		for(int i=0; i < starNum; i++){
			starMoves[i][0] = (float)(Math.random() * 1000 - 500);
			starMoves[i][1] = (float)(Math.random() * 1000 - 500);
		}
		
	}
	
	public void surfaceCreated(SurfaceHolder holder)
	{
		thread = new Thread(this);
		thread.start();
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
	{
		
	}
	
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		thread = null;
	}
	

	
	public void run()
	{
		Canvas canvas;
		while(thread != null)
		{
			Paint paint = new Paint();
			//paint.setAntiAlias(true);
			//paint.setTextSize(16);
			paint.setAlpha(_alpha);
			
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			
			for (int i = 0; i < starNum; i++)
			{
				starPosX[i] += (float) ((touchX + starMoves[i][0] - starPosX[i]) * 0.01);
				starPosY[i] += (float) ((touchY + starMoves[i][1] - starPosY[i]) * 0.01);
				//starAry[i] = bm;
				canvas.drawBitmap(star, starPosX[i]-star.getWidth()/2, starPosY[i]-star.getHeight()/2, paint);
			}
			
			if(_alpha <= 0)
			{
				_alpha = 0;
			}
			else
			{
				_alpha -= 1;
			}
			
			//canvas.drawText("TouchXY="+touchX+" / "+touchY, 0, 20, paint);
			
			
			//px += (touchX - px) * 0.1;
			//py += (touchY - py) * 0.1;
			
			/*if(touchX == (int)px && touchY == (int)py)
			{
				canvas.drawText("stop", 0, 40, paint);
				canvas.drawColor(Color.WHITE);
			}*/
			
			
			holder.unlockCanvasAndPost(canvas);
			
			
			
			try
			{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			touchX = (int)e.getX();
			touchY = (int)e.getY();
			for(int i=0; i < starNum; i++){
				starPosX[i] = touchX;
				starPosY[i] = touchY;
			}
			_alpha = 255;
		}
		return true;
	}
}
