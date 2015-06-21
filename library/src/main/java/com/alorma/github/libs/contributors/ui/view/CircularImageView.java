package com.alorma.github.libs.contributors.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CircularImageView extends ImageView {

	private boolean isSelected;
	private int canvasSize;

	// Objects used for the actual drawing
	private BitmapShader shader;
	private Bitmap image;
	private Paint paint;
	private ColorFilter selectorFilter;
	private Rect rect;

	public CircularImageView(Context context) {
		this(context, null);
	}

	public CircularImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		isInEditMode();
		// Initialize paint objects
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// Don't draw anything without an image
		if (image == null)
			return;

		// Nothing to draw (Empty bounds)
		if (image.getHeight() == 0 || image.getWidth() == 0) {
			return;
		}

		// Compare canvas sizes
		int oldCanvasSize = canvasSize;

		canvasSize = canvas.getWidth();
		if (canvas.getHeight() < canvasSize)
			canvasSize = canvas.getHeight();

		// Reinitialize shader, if necessary
		if (oldCanvasSize != canvasSize) {

		}
		refreshBitmapShader();
		// Apply shader to paint
		paint.setShader(shader);

		// Keep track of selectorStroke/border width
		int outerWidth = 0;

		// Get the exact X/Y axis of the view
		int center = canvasSize / 2;

		// Draw the circular image itself
		canvas.drawCircle(center + outerWidth, center + outerWidth, ((canvasSize - (outerWidth * 2)) / 2) - 4.0f, paint);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// Check for clickable state and do nothing if disabled
		if (!this.isClickable()) {
			this.isSelected = false;
			return super.onTouchEvent(event);
		}

		// Set selected state based on Motion Event
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.isSelected = true;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_SCROLL:
			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_CANCEL:
				this.isSelected = false;
				break;
		}

		// Redraw image and return super type
		this.invalidate();
		return super.dispatchTouchEvent(event);
	}

	public void invalidate(Rect dirty) {
		super.invalidate(dirty);
		image = drawableToBitmap(getDrawable());
	}

	public void invalidate(int l, int t, int r, int b) {
		super.invalidate(l, t, r, b);
		image = drawableToBitmap(getDrawable());
	}

	@Override
	public void invalidate() {
		image = drawableToBitmap(getDrawable());
		super.invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// The parent has determined an exact size for the child.
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// The parent has not imposed any constraint on the child.
			result = canvasSize;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = canvasSize;
		}

		return (result + 2);
	}

	/**
	 * Convert a drawable object into a Bitmap
	 *
	 * @param drawable
	 * @return Cirucular Bitmap
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) { // Don't do anything without a proper drawable
			return null;
		} else if (drawable instanceof BitmapDrawable) { // Use the getBitmap() method instead if BitmapDrawable
			return ((BitmapDrawable) drawable).getBitmap();
		}

		try {
			// Create Bitmap object out of the drawable
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Reinitializes the shader texture used to fill in
	 * the Circle upon drawing.
	 */
	public void refreshBitmapShader() {
		shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
	}

	public boolean isSelected() {
		return this.isSelected;
	}
}