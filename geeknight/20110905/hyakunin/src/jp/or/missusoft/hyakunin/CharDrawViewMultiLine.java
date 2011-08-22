package jp.or.missusoft.hyakunin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CharDrawViewMultiLine extends View {
	private String mMessage = "";
	private Paint mPaint;
	private int width;
    private int height;
    int charSize;
    private static final int TOP_SPACE = 18;
    private static final int BOTTOM_SPACE = 18;
    private static final int FONT_SIZE = 30;

	public CharDrawViewMultiLine(Context context,AttributeSet attrs) {  
		  super(context,attrs);
		  // 文字色  
		  mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      mPaint.setTextSize(FONT_SIZE);
	      mPaint.setColor(Color.WHITE);
	} 
	
	 @Override
	    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
	        super.onLayout(changed, left, top, right, bottom);

	        width = getWidth();
	        height = getHeight();
	    }
	
	public void setText(String string) {
		this.mMessage = string;
	}
	
	protected void onDraw(Canvas canvas) {  
	    float fontSpacing = mPaint.getFontSpacing();
        float lineSpacing = fontSpacing * 2;
        float x = width   - lineSpacing;
        float y = TOP_SPACE ; //+ fontSpacing ;
        String[] s = mMessage.split("");
        boolean newLine = false;
        
		super.onDraw(canvas);
		
		for (int i = 1; i <= mMessage.length(); i++) {
            newLine = false;

            // 文字描画
            canvas.drawText(s[i], x, y + oneCharHeight(fontSpacing,FONT_SIZE) , mPaint);

            if (y + (fontSpacing *2) > height - BOTTOM_SPACE) {
                // もう文字が入らない場合
                newLine = true;
            } else if(s[i].equals(Character.toString((char) 0d))){
            	// 改行コード検出
                newLine = true;
            } else {
                // まだ文字が入る場合
                newLine = false;
            }

            if (newLine) {
            	// 改行処理
                x -= lineSpacing;
                y = TOP_SPACE ; // + fontSpacing;
            } else {
                // 文字を送る
                y += fontSpacing;
            }
		}
	}
		
	
	
	

		  
	/* 
	 * 中心点から描く 
	 */  
	private int oneCharHeight(float fontSpacing,int charSize) {  
	 return (int) ((fontSpacing / 2) + (charSize /2));  
	}  
}


