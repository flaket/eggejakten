package io.flakstad.eggejakten;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DistanceView extends SurfaceView implements SurfaceHolder.Callback {

	final String TAG = "DistanceView";

	private DistanceThread thread;

	public DistanceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new DistanceThread(holder, context, new Handler() {
		});
	}

	class DistanceThread extends Thread {
		final String TAG = "DistanceThread";

		SurfaceHolder mSurfaceHolder;
		Handler mHandler;
		Context mContext;
		boolean mRun;
		Paint textPaint, distancePaint;
		private int alpha, red, green, blue;
		private int distance;
		String messageText = "..PŒskeeggene lokaliseres..";

		public DistanceThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			mHandler = handler;
			mContext = context;
			mSurfaceHolder = surfaceHolder;
			distancePaint = new Paint();
			distancePaint.setARGB(255, 255, 255, 255);
			distancePaint.setTextSize(150);
			distancePaint.setTextAlign(Align.CENTER);
			textPaint = new Paint();
			textPaint.setTextAlign(Align.CENTER);
			textPaint.setTextSize(30);
			textPaint.setARGB(255, 255, 255, 255);
			setDistance(-1);
		}

		@Override
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						if (mRun)
							doDraw(c);
					}
				} finally {
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}

		private void doDraw(Canvas canvas) {
			if (getDistance() == -1) {
				canvas.drawColor(Color.BLACK);
				canvas.drawText(messageText, (canvas.getWidth() / 2),
						canvas.getHeight() / 2, textPaint);
			} else {
				canvas.drawColor(Color.BLACK);
				canvas.drawText(Integer.toString(getDistance()),
						(canvas.getWidth() / 2), canvas.getHeight() / 2,
						distancePaint);
			}
		}

		public void setRunning(boolean b) {
			mRun = b;
		}

		public int getAlpha() {
			return alpha;
		}

		public void setAlpha(int alpha) {
			this.alpha = alpha;
		}

		public int getRed() {
			return red;
		}

		public void setRed(int red) {
			this.red = red;
		}

		public int getGreen() {
			return green;
		}

		public void setGreen(int green) {
			this.green = green;
		}

		public int getBlue() {
			return blue;
		}

		public void setBlue(int blue) {
			this.blue = blue;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

	}

	public DistanceThread getThread() {
		return thread;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}
