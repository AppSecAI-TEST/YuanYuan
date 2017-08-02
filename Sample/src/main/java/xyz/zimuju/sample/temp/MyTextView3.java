package xyz.zimuju.sample.temp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyTextView3 extends AppCompatTextView {
	private final String namespace = "http://edu.bit.zb";
	private String text;
	private Paint mPaint = new Paint();
	private float textSize = 30;
	private float paddingLeft;
	private float paddingRight;
	private float layoutWidth;
	private float layoutHeight;
	private float layout_x = 20;
	private float textDisplayWidth;

	public MyTextView3(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(30);

		paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
		paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
		textDisplayWidth = layoutWidth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		text = this.getText().toString();
		char[] textCharArray = text.toCharArray();

		// 已绘的宽度
		float drawedWidth = 0;
		float charWidth = 0;
		int lineCount = 0;

		for (int i = 0; i < textCharArray.length; i++) {
			charWidth = mPaint.measureText(textCharArray, i, 1);
			if (textDisplayWidth - drawedWidth < charWidth) {
				lineCount++;
				drawedWidth = 0;
			}
			canvas.drawText(textCharArray, i, 1, layout_x + paddingLeft + drawedWidth, (lineCount + 1) * textSize, mPaint);
			drawedWidth += charWidth;
		}
		setHeight((lineCount + 1) * (int) textSize + 5);
	}
}
