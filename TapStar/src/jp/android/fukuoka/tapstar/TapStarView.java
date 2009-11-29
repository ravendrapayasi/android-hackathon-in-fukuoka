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
	private float px = 0;
	private float py = 0;
	//private int vx = 10;
	//private int vy = 10;
	
	private float touchX = 0;
	private float touchY = 0;
	
	public TapStarView(Context context)
	{
		super(context);
		
		//-----[画像の読み込み]
		Resources r = getResources();
		bm = BitmapFactory.decodeResource(r, R.drawable.icon);
		
		//-----[サーフェイスホルダーの生成]
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
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(bm, px-bm.getWidth()/2, py-bm.getHeight()/2, null);
			holder.unlockCanvasAndPost(canvas);
			
			//-----[タッチの描画]
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setTextSize(16);
			
			canvas.drawText("TouchXY="+touchX+" / "+touchY, 0, 20, paint);
			
			//-----[移動]
			/*if(px<0 || getWidth()<px) vx = -vx;
			if(py<0 || getHeight()<py) vy = -vy;
			px+=vx;
			py+=vy;*/
			
			px += (touchX - px) * 0.1;
			py += (touchY - py) * 0.1;
			
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
		return true;
	}
}
