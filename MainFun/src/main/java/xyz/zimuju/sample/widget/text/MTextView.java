package xyz.zimuju.sample.widget.text;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MTextView extends AppCompatTextView {
    private final String namespace = "http://edu.bit.zb";
    private String text;
    private float textSize;
    private Paint paint1 = new Paint();
    private float paddingLeft;
    private float paddingRight;
    private float textShowWidth;
    private int textColor;

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        text = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
        textSize = attrs.getAttributeIntValue(namespace, "textSize", 15);
        textColor = attrs.getAttributeIntValue(namespace, "textColor", Color.WHITE);
        paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
        paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        // setBackgroundColor(Color.BLACK);
        textShowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - paddingLeft - paddingRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lineCount = 0;
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);

            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }
            canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
                    (lineCount + 1) * textSize, paint1);
            drawedWidth += charWidth;
        }
        setHeight((lineCount + 1) * (int) textSize + 5);
    }
}
