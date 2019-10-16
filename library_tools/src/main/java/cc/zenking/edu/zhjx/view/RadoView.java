package cc.zenking.edu.zhjx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


import com.yuanhy.library_tools.util.SystemUtile;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import cc.zenking.edu.zhjx.R;

/**
 * 自定义雷达图控件
 */
public class RadoView extends View {

	private int count = 1;

	private int[] radius = new int[]{100};//各个外接圆的变数

	private int maxRadius = 180;//最大半径
	private int maxValue = 0;

	private int[] marks = new int[count];
	private String[] keys = new String[count];

	LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

	private Paint paintLine;//画线
	private Paint rayPaintLine;// 画射线

	private Paint paintMarkLine;//分值连线
	private Paint paintMarkPoint;//分值画点

	private TextPaint paintText;//绘制文字

	private double x;//当前点的横坐标
	private double y; //当前点的纵坐标
	private double lastX; //上一次的坐标
	private double lastY;
	private double firstX; //上一次的坐标
	private double firstY;
	private double centerX = maxRadius;
	private double centerY = maxRadius;
	private int textSize = 30;// 文字的大小
	private int textColor = Color.WHITE;// 文字颜色
	private int polygonColor = Color.WHITE;// 多边形颜色
	private int rayColor = Color.WHITE;// 射线颜色
	private int connectLineColor = Color.parseColor("#53d16e");// 点和点的连接线的颜色
	private int lineWith = 2;
	Context context;

	public RadoView(Context context) {
		super(context);
		this.context = context;
		maxRadius = SystemUtile.getPX(context, R.dimen.dp_90);
		textSize = SystemUtile.getPX(context, R.dimen.dp_15);
		lineWith = SystemUtile.getPX(context, R.dimen.dp_1);
		;
	}

	public RadoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		maxRadius = SystemUtile.getPX(context, R.dimen.dp_90);
		textSize = SystemUtile.getPX(context, R.dimen.dp_15);
		lineWith = SystemUtile.getPX(context, R.dimen.dp_1);
	}

	public RadoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		maxRadius = SystemUtile.getPX(context, R.dimen.dp_90);
		textSize = SystemUtile.getPX(context, R.dimen.dp_15);
		lineWith = SystemUtile.getPX(context, R.dimen.dp_1);
	}

	public void setColors(int textColor, int polygonColor, int rayColor, int connectLineColor) {
		this.textColor = textColor;
		this.polygonColor = polygonColor;
		this.rayColor = rayColor;
		this.connectLineColor = connectLineColor;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public void setMaxRadius(int radius) {
		this.maxRadius = radius;
	}

	public void setMaxValue(int value) {
		maxValue = value;
	}

	public void setLineWith(int with) {
		lineWith = with;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取中心
		centerX = this.getWidth() / 2;
		centerY = this.getHeight() / 2;

		// 根据最大半径和个数，设置各个半径
		int average = maxRadius / count;
		radius = new int[count];
		for (int i = 0; i < count; i++) {
			if (i == count - 1) {
				radius[i] = maxRadius;//保证最后一个与最大半径相等
			} else {
				radius[i] = average * (i + 1);
			}
		}

		double littleAngle = 360 / count;

		marks = new int[count];
		keys = new String[count];
		Iterator iterator = map.entrySet().iterator();
		int j = 0;
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Integer value = (Integer) entry.getValue();
			marks[j] = value;
			keys[j] = key;
			j++;
		}

		paintLine = new Paint();
		paintLine.setColor(polygonColor);
		paintLine.setStyle(Paint.Style.STROKE);
		paintLine.setStrokeWidth(lineWith);
		paintLine.setAntiAlias(true);

		rayPaintLine = new Paint();
		rayPaintLine.setColor(rayColor);
		rayPaintLine.setStyle(Paint.Style.STROKE);
		rayPaintLine.setStrokeWidth(lineWith);
		rayPaintLine.setAntiAlias(true);

		paintText = new TextPaint();
		paintText.setColor(textColor);
		paintText.setTextSize(textSize);
		paintText.setAntiAlias(true);

		for (int i = 0; i < radius.length; i++) {
			drawStroke(canvas, littleAngle, radius[i]);
		}

		//分数点
		paintMarkPoint = new Paint();
		paintMarkPoint.setColor(connectLineColor);
		paintMarkPoint.setStyle(Paint.Style.FILL);
		paintMarkPoint.setAntiAlias(true);

		//评分点的连线
		paintMarkLine = new Paint();
		paintMarkLine.setAntiAlias(true);
		paintMarkLine.setColor(connectLineColor);
		paintMarkLine.setStyle(Paint.Style.FILL_AND_STROKE);
		paintMarkLine.setAlpha(60);
		paintMarkLine.setAntiAlias(true);

		Path path = new Path();
		path.reset();
		if (maxValue != 0) {
			for (int i = 0; i < marks.length; i++) {
				x = getPointX(littleAngle * i, maxRadius * marks[i] / maxValue);
				y = getPointY(littleAngle * i, maxRadius * marks[i] / maxValue);
				canvas.drawCircle((float) x, (float) y, SystemUtile.getPX(context, R.dimen.dp_2_5), paintMarkPoint);
				if (i == 0) {
					path.moveTo((float) x, (float) y);
				} else {
					path.lineTo((float) x, (float) y);
				}
				paintLine.setColor(connectLineColor);// 将线的颜色修改为连接线的颜色
				paintLine.setStrokeWidth(lineWith + SystemUtile.getPX(context, R.dimen.dp_0_5));
				if (i == 0) {
					firstX = x;
					firstY = y;
				} else if (i == marks.length - 1) {
					canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y, paintLine);
					canvas.drawLine((float) x, (float) y, (float) firstX, (float) firstY, paintLine);
				} else {
					canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y, paintLine);
				}
				lastX = x;
				lastY = y;
			}
			canvas.drawPath(path, paintMarkLine);
		}
	}

	/**
	 * 绘制多边形，radius为其外接圆半径
	 *
	 * @param canvas
	 * @param littleAngle
	 * @param radius
	 */
	private void drawStroke(Canvas canvas, double littleAngle, int radius) {
		for (int i = 0; i < count; i++) {
			Paint paint = new Paint();
			paint.setColor(polygonColor);
			paint.setStyle(Paint.Style.FILL);
			x = getPointX(littleAngle * i, radius);//当前顶点的坐标
			y = getPointY(littleAngle * i, radius);
			canvas.drawPoint((float) x, (float) y, paint);
			if (maxValue != 0) {
				canvas.drawLine((float) centerX, (float) centerY, (float) x, (float) y, rayPaintLine);
			}
			if (i > 0) {
				canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y, paintLine);
			}
			if (i == (count - 1)) { //如果是最后一个顶点，则将其与第一个顶点进行连线
				canvas.drawLine((float) x, (float) y, (float) getPointX(0, radius), (float) getPointY(0, radius), paintLine);
			}
			lastX = x;
			lastY = y; //记录上个顶点的坐标，方便进行连线
			if (radius == maxRadius) {
				double x0 = getPointX(littleAngle * i, radius + SystemUtile.getPX(context, R.dimen.dp_25));
				double y0 = getPointY(littleAngle * i, radius + SystemUtile.getPX(context, R.dimen.dp_25));
				y0 -= SystemUtile.getPX(context, R.dimen.dp_25);
				if (keys[i] == null) {
					return;
				}
				StaticLayout layout = new StaticLayout(keys[i], paintText, textSize * 6, Layout.Alignment.ALIGN_CENTER, 1.0F,
						0.0F, true);
				// 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
				canvas.save();
				canvas.translate((float) (x0 - layout.getWidth() / 2), (float) (y0 + textSize));//从100，100开始画
				layout.draw(canvas);
				canvas.restore();//别忘了restore

				//如果是最外层的园，则加上文字
//                canvas.drawText(keys[i], (float) (x0 - 20), (float) (y0 + 20), paintText);
			}
		}
	}


	/**
	 * 得到需要计算的角度
	 *
	 * @param angle 角度，例：30.60.90
	 * @return res
	 */
	private double getNewAngle(double angle) {
		double res = angle;
		if (angle >= 0 && angle <= 90) {
			res = 90 - angle;
		} else if (angle > 90 && angle <= 180) {
			res = angle - 90;
		} else if (angle > 180 && angle <= 270) {
			res = 270 - angle;
		} else if (angle > 270 && angle <= 360) {
			res = angle - 270;
		}
		return res;
	}


	/**
	 * 若以圆心为原点，返回该角度顶点的所在象限
	 *
	 * @param angle
	 * @return
	 */
	private int getQr(double angle) {
		int res = 0;
		if (angle >= 0 && angle <= 90) {
			res = 1;
		} else if (angle > 90 && angle <= 180) {
			res = 2;
		} else if (angle > 180 && angle <= 270) {
			res = 3;
		} else if (angle > 270 && angle <= 360) {
			res = 4;
		}
		return res;
	}

	/**
	 * 返回多边形顶点X坐标
	 *
	 * @param angle
	 * @return
	 */
	private double getPointX(double angle, double radius) {
		double newAngle = getNewAngle(angle);
		double res = 0;
		double width = radius * Math.cos(newAngle / 180 * Math.PI);
		int qr = getQr(angle);
		switch (qr) {
			case 1:
			case 2:
				res = centerX + width;
				break;
			case 3:
			case 4:
				res = centerX - width;
				break;
			default:
				break;
		}
		return res;
	}


	/**
	 * 返回多边形顶点Y坐标
	 */
	private double getPointY(double angle, double radius) {
		double newAngle = getNewAngle(angle);
		double height = radius * Math.sin(newAngle / 180 * Math.PI);
		double res = 0;
		int qr = getQr(angle);
		switch (qr) {
			case 1:
			case 4:
				res = centerY - height;
				break;
			case 2:
			case 3:
				res = centerY + height;
				break;
			default:
				break;
		}
		return res;
	}

	public void setMap(LinkedHashMap<String, Integer> map) {
		this.map = map;
		this.count = map.size() == 0 ? 1 : map.size();
		maxValue = 0;
		for (Integer i : map.values()) {
			if (i > maxValue) {
				maxValue = i;
			}
		}
		invalidate();
	}

	public LinkedHashMap<String, Integer> getMap() {
		return map;
	}

	public int getCount() {
		return count;
	}
}