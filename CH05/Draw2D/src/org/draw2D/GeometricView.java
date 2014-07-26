package org.draw2D;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
public class GeometricView extends View {
	private ShapeDrawable[] shapes;
	public GeometricView(Context context) {
		super(context);
		makeShapes();
	}
	
	public GeometricView(Context context, AttributeSet attrs){
		super(context, attrs);
		makeShapes();
	}	
	public void makeShapes(){
		shapes = new ShapeDrawable[4];
		shapes[0] = new ShapeDrawable(new OvalShape());
		shapes[0].setBounds(10, 10, 100, 150);
		shapes[1] = new ShapeDrawable(new OvalShape());
		shapes[1].setBounds(120, 10, 260, 100);
		shapes[2] = new ShapeDrawable(new RectShape());
		shapes[2].setBounds(10, 170, 100, 310);
		shapes[3] = new ShapeDrawable(new RectShape());
		shapes[3].setBounds(120, 170, 260, 260);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		for (ShapeDrawable shape : shapes) {
			int r = (int) (256 * Math.random());
			int g = (int) (256 * Math.random());
			int b = (int) (256 * Math.random());			
			shape.getPaint().setARGB(255, r, g, b); 
			shape.draw(canvas);
		}
	}
}
