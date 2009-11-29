package jp.android.fukuoka.tegaky;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class TegakyView extends View {
	private Bitmap tegakyBitmap;
	private Canvas tegakyCanvas;
	private Path linePath;
	private Paint linePaint;
	private int nextPointX;
	private int nextPointY;
    
	public TegakyView(Context context) {
		super(context);
		
		linePath = new Path();
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(2);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setColor(Color.BLUE);
	}
	
	public void setFirstPoint(float x, float y) {
		linePath.moveTo(x, y);
		nextPointX = (int)x;
		nextPointY = (int)y;
	}

	public void setNextPoint(float x,float y){
		nextPointX = (int)x;
		nextPointY = (int)y;
		linePath.lineTo(nextPointX, nextPointY);
		tegakyCanvas.drawPath(linePath, linePaint);
	}
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawBitmap(tegakyBitmap, 0, 0, null);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//描画用ビットマップ
		tegakyBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
		//描画用キャンバス
		tegakyCanvas = new Canvas(tegakyBitmap);
		tegakyCanvas.drawColor(Color.WHITE);
	}
	
	public Bitmap getBitmap(){
		return tegakyBitmap;
	}
}
