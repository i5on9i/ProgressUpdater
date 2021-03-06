package com.example.samples.progressui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.samples.R;

/**
 * Created with IntelliJ IDEA.
 * User: namh
 * Date: 13. 8. 28
 * Time: 오전 9:50
 * To change this template use File | Settings | File Templates.
 */
public class ProgressView extends TextView {
    //Sizes (with defaults)

    private int rimWidth = 20;


    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //Paints
    private Paint rimPaint = new Paint();
    private Paint textPaint = new Paint();


    //Colors (with defaults)
    private int rimColor = 0xff33b5e5;

    // Rect
    private RectF circleBounds = new RectF();

    int progress = 0;
    int max = 100;


    public ProgressView(Context context) {
        super(context);
        init(context, null, 0);

    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.ProgressView, defStyle, 0);

            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.ProgressView_progress_color:
                        rimColor = a.getColor(attr, rimColor);
                        break;
                    case R.styleable.ProgressView_progress_max:
                        max = a.getColor(attr, max);
                        break;
                }
            }
            a.recycle();
        }
        setupPaints();
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //Draw the circle type progress
        canvas.drawArc(circleBounds, -90, 360 * progress / max, true, rimPaint);


        super.onDraw(canvas);

    }

    //----------------------------------
    //Setting up stuff
    //----------------------------------
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // As for setupBounds(), using onAttatchedToWindow() is not appropriate for some view such as Gallery
        // because, after onAttatchedToWindow() is invoked, {@ref: View.mMeasuredWidth}
        // is determined.
        setupBounds();

    }


    private void setupPaints() {

        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Paint.Style.FILL);
        rimPaint.setStrokeWidth(rimWidth);


    }

    private void setupBounds() {
        paddingTop = this.getPaddingTop();
        paddingBottom = this.getPaddingBottom();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();

        // FYI
        // For this.getMeasuredWidth(),
        // setupBounds() is moved to onMeasure()
        int layoutWidth = this.getLayoutParams().width;
        if (layoutWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
            layoutWidth = this.getMeasuredWidth();
        }

        circleBounds = new RectF(paddingLeft,
                paddingTop,
                layoutWidth - paddingRight,
                this.getLayoutParams().height - paddingBottom);


    }


    public void setProgress(int i) {
        progress = i;
        invalidate();
    }

}
