package com.fenghuang.caipiaobao.widget.lighter.parameter;

import android.app.Activity;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.fenghuang.caipiaobao.widget.lighter.Lighter;
import com.fenghuang.caipiaobao.widget.lighter.shape.LighterShape;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 */
public class LighterParameter {
    private int highlightedViewId;
    private View highlightedView;
    private RectF highlightedViewRect;

    private int tipLayoutId;
    private View tipView;

    private LighterShape lighterShape;

    private float shapeXOffset;
    private float shapeYOffset;

    private int tipViewRelativeDirection;
    private MarginOffset tipViewRelativeMarginOffset;
    private Animation tipViewDisplayAnimation;

    private LighterParameter() {
    }

    public RectF getHighlightedViewRect() {
        return highlightedViewRect;
    }

    /**
     * Set the rect of highlighted view.
     */
    public void setHighlightedViewRect(RectF highlightedViewRect) {
        this.highlightedViewRect = highlightedViewRect;
    }

    public int getTipViewRelativeDirection() {
        return tipViewRelativeDirection;
    }

    /**
     * Set the direction of the tip view relative to the highlighted view.
     */
    public void setTipViewRelativeDirection(int tipViewRelativeDirection) {
        this.tipViewRelativeDirection = tipViewRelativeDirection;
    }

    public Animation getTipViewDisplayAnimation() {
        return tipViewDisplayAnimation;
    }

    /**
     * Set animation of the tip view.
     */
    public void setTipViewDisplayAnimation(Animation tipViewDisplayAnimation) {
        this.tipViewDisplayAnimation = tipViewDisplayAnimation;
    }

    public MarginOffset getTipViewRelativeMarginOffset() {
        return tipViewRelativeMarginOffset;
    }

    /**
     * Set the offset of the tip view's margin relative to the highlighted view.
     *
     * @param marginOffset The margin offset values.
     */
    public void setTipViewRelativeMarginOffset(MarginOffset marginOffset) {
        this.tipViewRelativeMarginOffset = marginOffset;
    }

    public int getHighlightedViewId() {
        return highlightedViewId;
    }

    /**
     * Set the id of the view that will be highlighted. <br>
     * If you use {@link Lighter#with(Activity)}, will call {@link Activity#findViewById(int)} <br>
     * If you use {@link Lighter#with(ViewGroup)} )}, will call {@link ViewGroup#findViewById(int)}
     *
     * @param highlightedViewId
     */
    public void setHighlightedViewId(int highlightedViewId) {
        this.highlightedViewId = highlightedViewId;
    }

    public View getHighlightedView() {
        return highlightedView;
    }

    /**
     * Set the view that will be highlighted.
     *
     * @param highlightedView The highlighted view.
     */
    public void setHighlightedView(View highlightedView) {
        this.highlightedView = highlightedView;
    }

    public int getTipLayoutId() {
        return tipLayoutId;
    }

    /**
     * Set the layout id of the tip layout. <br>
     * <p>
     * Will call {@link android.view.LayoutInflater#inflate(int, ViewGroup, boolean)} to create the
     * tip layout. <br>
     *
     * @param tipLayoutId The layout id of tip.
     */
    public void setTipLayoutId(int tipLayoutId) {
        this.tipLayoutId = tipLayoutId;
    }

    public View getTipView() {
        return tipView;
    }

    /**
     * Set the tip view
     *
     * @param tipView The tip view.
     */
    public void setTipView(View tipView) {
        this.tipView = tipView;
    }

    public LighterShape getLighterShape() {
        return lighterShape;
    }

    /**
     * Set the shape of the wrapped highlight view
     *
     * @param lighterShape The highlighted shape.
     */
    public void setLighterShape(LighterShape lighterShape) {
        this.lighterShape = lighterShape;
    }

    public float getShapeXOffset() {
        return shapeXOffset;
    }

    /**
     * Set the x-axis offset of the shape rect({@link LighterShape#setViewRect(RectF)}), default is 0  <br>
     * <p>
     * The final rect width is new RectF{rect.left - shapeXOffset, top, rect.right + shapeXOffset, bottom}
     *
     * @param shapeXOffset The x-axis offset value.
     */
    public void setShapeXOffset(float shapeXOffset) {
        this.shapeXOffset = shapeXOffset;
    }

    public float getShapeYOffset() {
        return shapeYOffset;
    }

    /**
     * Set the y-axis offset of the shape rect ({@link LighterShape#setViewRect(RectF)}), default is 0 <br>
     * <p>
     * The final rect width is new RectF{rect.left, top - shapeYOffset, rect.right, bottom + shapeYOffset}
     *
     * @param shapeYOffset The y-axis offset value.
     */
    public void setShapeYOffset(float shapeYOffset) {
        this.shapeYOffset = shapeYOffset;
    }

    /**
     * Help to build {@link LighterParameter}
     */
    public static class Builder {
        private LighterParameter mLighterParameter;

        public Builder() {
            mLighterParameter = new LighterParameter();
        }

        /**
         * Set the id of the view that will be highlighted. <br>
         * If you use {@link Lighter#with(Activity)}, will call {@link Activity#findViewById(int)} <br>
         * If you use {@link Lighter#with(ViewGroup)} )}, will call {@link ViewGroup#findViewById(int)}
         *
         * @param highlightedViewId
         */
        public Builder setHighlightedViewId(int highlightedViewId) {
            mLighterParameter.setHighlightedViewId(highlightedViewId);
            return this;
        }

        /**
         * Set the view that will be highlighted.
         *
         * @param highlightedView The highlighted view.
         */
        public Builder setHighlightedView(View highlightedView) {
            mLighterParameter.setHighlightedView(highlightedView);
            return this;
        }

        /**
         * Set the layout id of the tip layout. <br>
         * <p>
         * Will call {@link android.view.LayoutInflater#inflate(int, ViewGroup, boolean)} to create the
         * tip layout. <br>
         *
         * @param tipLayoutId The layout id of tip.
         */
        public Builder setTipLayoutId(int tipLayoutId) {
            mLighterParameter.setTipLayoutId(tipLayoutId);
            return this;
        }

        /**
         * Set the tip view
         *
         * @param tipView The tip view.
         */
        public Builder setTipView(View tipView) {
            mLighterParameter.setTipView(tipView);
            return this;
        }

        /**
         * Set the shape of the wrapped highlight view
         *
         * @param lighterShape The highlighted shape.
         */
        public Builder setLighterShape(LighterShape lighterShape) {
            mLighterParameter.setLighterShape(lighterShape);
            return this;
        }

        /**
         * Set the x-axis offset of the shape rect({@link LighterShape#setViewRect(RectF)}), default is 10  <br>
         * <p>
         * The final rect width is new RectF{rect.left - shapeXOffset, top, rect.right + shapeXOffset, bottom}
         *
         * @param xOffset The x-axis offset value.
         */
        public Builder setShapeXOffset(float xOffset) {
            mLighterParameter.setShapeXOffset(xOffset);
            return this;
        }

        /**
         * Set the y-axis offset of the shape rect ({@link LighterShape#setViewRect(RectF)}), default is 10 <br>
         * <p>
         * The final rect width is new RectF{rect.left, top - shapeYOffset, rect.right, bottom + shapeYOffset}
         *
         * @param yOffset The y-axis offset value.
         */
        public Builder setShapeYOffset(float yOffset) {
            mLighterParameter.setShapeYOffset(yOffset);
            return this;
        }

        /**
         * Set the direction of the tip view relative to the highlighted view.
         *
         * @param tipViewRelativeDirection Must be {@link Direction}
         */
        public Builder setTipViewRelativeDirection(int tipViewRelativeDirection) {
            mLighterParameter.setTipViewRelativeDirection(tipViewRelativeDirection);
            return this;
        }

        /**
         * Set the offset of the tip view's margin relative to the highlighted view.
         *
         * @param marginOffset The margin offset values.
         */
        public Builder setTipViewRelativeOffset(MarginOffset marginOffset) {
            mLighterParameter.setTipViewRelativeMarginOffset(marginOffset);
            return this;
        }

        /**
         * Set animation of the tip view.
         */
        public Builder setTipViewDisplayAnimation(Animation tipViewDisplayAnimation) {
            mLighterParameter.setTipViewDisplayAnimation(tipViewDisplayAnimation);
            return this;
        }

        public LighterParameter build() {
            return mLighterParameter;
        }
    }
}
