package jp.android.fukuoka.tapstar;

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
	
	private int starNum = 10;
	/*private Bitmap[] starAry;
	private float px = 0;
	private float py = 0;*/
	
	private int _alpha = 100;
	
	private float touchX = 0;
	private float touchY = 0;
	
	public TapStarView(Context context)
	{
		super(context);
		
		Resources r = getResources();
		bm = BitmapFactory.decodeResource(r, R.drawable.icon);
		
		holder = getHolder();
		holder.addCallback(this);
		holder.setFixedSize(getWidth(), getHeight());
		
		
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
			
			for (int i = 0; i <= starNum; i++)
			{
				float posX = (float) (touchX + Math.random() * 100 - 50);
				float posY = (float) (touchY + Math.random() * 100 - 50);
				//starAry[i] = bm;
				canvas.drawBitmap(bm, posX-bm.getWidth()/2, posY-bm.getHeight()/2, paint);
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
		touchX = (int)e.getX();
		touchY = (int)e.getY();
		_alpha = 255;
		return true;
	}
}
