package io.flakstad.eggejakten;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ColorView extends SurfaceView implements SurfaceHolder.Callback {

	private ColorThread thread;

	public ColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new ColorThread(holder, context, new Handler() {
		});
	}

	class ColorThread extends Thread {
		public String TAG = "ColorThread";
		SurfaceHolder mSurfaceHolder;
		Handler mHandler;
		Context mContext;
		boolean mRun;
		Paint paint;
		private int alpha, red, green, blue;
		private int distance;
		private int temp;

		public ColorThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			Log.d(TAG, "created color thread");
			mHandler = handler;
			mContext = context;
			mSurfaceHolder = surfaceHolder;
			paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(150);
			setAlpha(255);
			setRed(0);
			setGreen(0);
			setBlue(0);
		}

		@Override
		public void run() {
			while (mRun) {
				Log.d(TAG, "	looping in ColorThread..");
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						if (mRun)
							doDraw(c);
					}
				} finally {
					if (c != null) {
						Log.d(TAG, "Unlocking and posting canvas.");
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}

		private void doDraw(Canvas canvas) {
			Log.d(TAG, "DRAWING");
			// canvas.drawARGB(getAlpha(), getRed(), getGreen(), getBlue());
			canvas.drawColor(Color.BLACK);
			canvas.drawText(Integer.toString(getDistance()),
					canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
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

	public ColorThread getThread() {
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
