package com.cy.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class CleanEditTextContent extends EditText
{
    private Drawable dRight;
    private Rect rBounds;
    public CleanEditTextContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
    }
    public CleanEditTextContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CleanEditTextContent(Context context) {
        super(context);
    }
	@Override
    public void setCompoundDrawables(Drawable left, Drawable top,
            Drawable right, Drawable bottom){
		if(right !=null)
            dRight = right;

		super.setCompoundDrawables(left, top, right, bottom);  
    }
   
	@Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_UP && dRight!=null)
        {
            rBounds = dRight.getBounds();
            final int x = (int)event.getX();
//            final int y = (int)event.getY();
//            System.out.println("x:/y: "+x+"/"+y);
//            System.out.println("bounds: "+rBounds.left+"/"+rBounds.right+"/"+rBounds.top+"/"+rBounds.bottom);
            //check to make sure the touch event was within the bounds of the drawable
//            if(x>=(this.getRight()-rBounds.width()) && x<=(this.getRight()-this.getPaddingRight())
//                    && y>=this.getPaddingTop() && y<=(this.getHeight()-this.getPaddingBottom()))
            if(x>=(this.getWidth()-rBounds.right))
            {
//                System.out.println("-------------touch");
                this.setText("");
                event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
            }
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void finalize() throws Throwable
    {
    	System.out.println("finalize 运行");
        dRight = null;
        rBounds = null;
        super.finalize();
    }
}
