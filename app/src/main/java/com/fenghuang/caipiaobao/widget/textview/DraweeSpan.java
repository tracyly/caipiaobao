package com.fenghuang.caipiaobao.widget.textview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.ref.WeakReference;

/**
 * @ Author  QinTian
 * @ Date  2019/11/21- 12:30
 * @ Describe
 */
public class DraweeSpan extends DynamicDrawableSpan implements DeferredReleaser.Releasable {
    private static final String TAG = "DraweeSpan";

    private final DeferredReleaser mDeferredReleaser;
    private final ForwardingDrawable mActualDrawable;
    private CloseableReference<CloseableImage> mFetchedImage;
    private DataSource<CloseableReference<CloseableImage>> mDataSource;
    private boolean mIsRequestSubmitted;
    private Drawable mDrawable;
    private Drawable mPlaceHolder;
    private DraweeTextView mAttachedView;
    private String mImageUri;
    private Point mLayout = new Point();
    private Rect mMargin = new Rect();
    private boolean mIsAttached;
    private boolean mShouldShowAnim = false;

    /**
     * Use {@link Builder} to build a DraweeSpan.
     */
    public DraweeSpan(String uri, int verticalAlignment, Drawable placeHolder, boolean showAnim) {
        super(verticalAlignment);
        mImageUri = uri;
        mShouldShowAnim = showAnim;
        mDeferredReleaser = DeferredReleaser.getInstance();
        mPlaceHolder = placeHolder;
        // create forwarding drawable with placeholder
        mActualDrawable = new ForwardingDrawable(mPlaceHolder);
    }

    protected void layout() {
        mActualDrawable.setBounds(0, 0, mLayout.x, mLayout.y);
    }

    @Override
    public Drawable getDrawable() {
        return mActualDrawable;
    }

    private int ALIGN_FONTCENTER = 2;
    private WeakReference<Drawable> mDrawableRef;

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.descent - fmPaint.ascent;
            int drHeight = rect.bottom - rect.top;
            int centerY = fmPaint.ascent + fontHeight / 2;

            fontMetricsInt.ascent = centerY - drHeight / 2;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = centerY + drHeight / 2;
            fontMetricsInt.descent = fontMetricsInt.bottom;
        }
        return rect.right;
//        Drawable d = getDrawable();
//        Rect rect = d.getBounds();
//
//        if (fm != null) {
//            fm.ascent = -rect.bottom - mMargin.top;
//            fm.descent = 0;
//
//            fm.top = fm.ascent;
//            fm.bottom = 0;
//        }
//
//        return rect.right + mMargin.left + mMargin.right;
//        Rect rect = d.getBounds();
//        if (fm != null) {
//            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
//            int fontHeight = fmPaint.bottom - fmPaint.top;
//            int drHeight = rect.bottom - rect.top;
//
//            int top = drHeight / 2 - fontHeight / 4;
//            int bottom = drHeight / 2 + fontHeight / 4;
//
//            fm.ascent = -bottom;
//            fm.top = -bottom;
//            fm.bottom = top;
//            fm.descent = top;
//        }
//        return rect.right;
    }

    public void setImage(Drawable drawable) {
        if (mDrawable != drawable) {
            releaseDrawable(mDrawable);
            setDrawableInner(drawable);
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
            mDrawable = drawable;
        }
    }

    private void setDrawableInner(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        mActualDrawable.setDrawable(drawable);
    }

    public void reset() {
        setDrawableInner(mPlaceHolder);
    }

    public void onAttach(@NonNull DraweeTextView view) {
        mIsAttached = true;
        if (mAttachedView != view) {
            mActualDrawable.setCallback(null);
            if (mAttachedView != null) {
                throw new IllegalStateException("has been attached to view:" + mAttachedView);
            }
            mAttachedView = view;
            setDrawableInner(mDrawable);
            mActualDrawable.setCallback(mAttachedView);
        }
        mDeferredReleaser.cancelDeferredRelease(this);
        if (!mIsRequestSubmitted) {
            submitRequest();
        } else if (mShouldShowAnim && mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).start();
        }
    }

    private void submitRequest() {
        if (TextUtils.isEmpty(getImageUri())) {
            return;
        }

        mIsRequestSubmitted = true;
        final String id = getId();
        mDataSource = fetchDecodedImage();
        DataSubscriber<CloseableReference<CloseableImage>> subscriber
                = new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                boolean isFinished = dataSource.isFinished();
                CloseableReference<CloseableImage> result = dataSource.getResult();
                if (result != null) {
                    onNewResultInternal(id, dataSource, result, isFinished);
                } else if (isFinished) {
                    onFailureInternal(id, dataSource, new NullPointerException(), /* isFinished */ true);
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                onFailureInternal(id, dataSource, dataSource.getFailureCause(), /* isFinished */ true);
            }
        };
        mDataSource.subscribe(subscriber, UiThreadImmediateExecutorService.getInstance());
    }

    @VisibleForTesting
    protected DataSource<CloseableReference<CloseableImage>> fetchDecodedImage() {
        ImagePipelineFactory factory;
        try {
            factory = ImagePipelineFactory.getInstance();
        } catch (NullPointerException e) {
            // Image pipeline is not initialized
            ImagePipelineFactory.initialize(mAttachedView.getContext().getApplicationContext());
            factory = ImagePipelineFactory.getInstance();
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(getImageUri()))
                .setImageDecodeOptions(ImageDecodeOptions.newBuilder().setDecodePreviewFrame(true).build())
                .build();
        return factory.getImagePipeline().fetchDecodedImage(request, null);
    }

    @NonNull
    public String getImageUri() {
        return mImageUri;
    }

    protected String getId() {
        return String.valueOf(getImageUri().hashCode());
    }

    private void onFailureInternal(String id,
                                   DataSource<CloseableReference<CloseableImage>> dataSource,
                                   Throwable throwable, boolean isFinished) {
        if (FLog.isLoggable(Log.WARN)) {
            FLog.w(DraweeSpan.class, id + " load failure", throwable);
        }
        // ignored this result
        if (!getId().equals(id)
                || dataSource != mDataSource
                || !mIsRequestSubmitted) {
            dataSource.close();
            return;
        }
        if (isFinished) {
            mDataSource = null;
            // Set the previously available image if available.
            setDrawableInner(mDrawable);
        }
    }

    private void onNewResultInternal(String id,
                                     DataSource<CloseableReference<CloseableImage>> dataSource,
                                     CloseableReference<CloseableImage> result,
                                     boolean isFinished) {
        // ignored this result
        if (!getId().equals(id)
                || dataSource != mDataSource
                || !mIsRequestSubmitted) {
            CloseableReference.closeSafely(result);
            dataSource.close();
            return;
        }
        Drawable drawable;
        try {
            drawable = createDrawable(result);
        } catch (Exception exception) {
            CloseableReference.closeSafely(result);
            onFailureInternal(id, dataSource, exception, isFinished);
            return;
        }
        CloseableReference previousImage = mFetchedImage;
        Drawable previousDrawable = mDrawable;
        mFetchedImage = result;
        try {
            // set the new image
            if (isFinished) {
                mDataSource = null;
                setImage(drawable);
            }
        } finally {
            if (previousDrawable != null && previousDrawable != drawable) {
                releaseDrawable(previousDrawable);
            }
            if (previousImage != null && previousImage != result) {
                CloseableReference.closeSafely(previousImage);
            }
        }
    }

    private Drawable createDrawable(CloseableReference<CloseableImage> result) {
        CloseableImage closeableImage = result.get();
        if (closeableImage instanceof CloseableStaticBitmap) {
            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
            BitmapDrawable bitmapDrawable = createBitmapDrawable(closeableStaticBitmap.getUnderlyingBitmap());
            return (closeableStaticBitmap.getRotationAngle() != 0 && closeableStaticBitmap.getRotationAngle() != -1
                    ? new OrientedDrawable(bitmapDrawable, closeableStaticBitmap.getRotationAngle()) : bitmapDrawable);
        } else if (closeableImage instanceof CloseableAnimatedImage) {
            if (mShouldShowAnim) {
                DrawableFactory factory = ImagePipelineFactory.getInstance().getAnimatedDrawableFactory(mAttachedView.getContext());
                if (factory != null) {
                    return factory.createDrawable(closeableImage);
                }
            }
            AnimatedImageResult image = ((CloseableAnimatedImage) closeableImage).getImageResult();
            int frame = image.getFrameForPreview();
            CloseableReference<Bitmap> bitmap = null;
            if (frame >= 0) {
                bitmap = image.getDecodedFrame(frame);
            }
            if (bitmap == null) {
                bitmap = image.getPreviewBitmap();
            }
            if (bitmap != null && bitmap.get() != null) {
                BitmapDrawable bitmapDrawable = createBitmapDrawable(bitmap.get());
                return bitmapDrawable;
            }
        }
        throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
    }

    protected BitmapDrawable createBitmapDrawable(Bitmap bitmap) {
        BitmapDrawable drawable;
        if (mAttachedView != null) {
            final Context context = mAttachedView.getContext();
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        } else {
            // can't happen
            drawable = new BitmapDrawable(null, bitmap);
        }
        return drawable;
    }

    public void onDetach() {
        if (!mIsAttached)
            return;
        if (mShouldShowAnim && mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).stop();
        }
        mActualDrawable.setCallback(null);
        mAttachedView = null;
        reset();
        mDeferredReleaser.scheduleDeferredRelease(this);
    }

    @Override
    public void release() {
        mIsRequestSubmitted = false;
        mIsAttached = false;
        mAttachedView = null;
        if (mDataSource != null) {
            mDataSource.close();
            mDataSource = null;
        }
        if (mDrawable != null) {
            releaseDrawable(mDrawable);
        }
        mDrawable = null;
        if (mFetchedImage != null) {
            CloseableReference.closeSafely(mFetchedImage);
            mFetchedImage = null;
        }
    }

    void releaseDrawable(@Nullable Drawable drawable) {
        if (drawable instanceof Animatable && ((Animatable) drawable).isRunning()) {
            ((Animatable) drawable).stop();
        }
        if (drawable instanceof DrawableWithCaches) {
            ((DrawableWithCaches) drawable).dropCaches();
        }
    }

    /**
     * DraweeSpan builder.
     */
    public static class Builder {
        String uri;
        int width = 100;
        int height = 100;
        int verticalAlignment = ALIGN_BOTTOM;
        Drawable placeholder;
        boolean showAnim;
        Rect margin = new Rect();

        public Builder(String uri) {
            this(uri, false);
        }

        /**
         * Construct drawee span builder.
         *
         * @param uri           image uri.
         * @param alignBaseline true to set {@link #ALIGN_BASELINE}, otherwise {@link #ALIGN_BOTTOM} .
         */
        public Builder(String uri, boolean alignBaseline) {
            this.uri = uri;
            if (uri == null) {
                throw new NullPointerException("Attempt to create a DraweeSpan with null uri string!");
            }
            if (alignBaseline) {
                this.verticalAlignment = ALIGN_BASELINE;
            }
        }

        /**
         * @param width  width of this span, px
         * @param height height of this span, px
         */
        public Builder setLayout(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * You can set margin in left, right and top in px. Bottom is in baseline.
         */
        public Builder setMargin(int margin) {
            this.margin.set(margin, margin, margin, 0);
            return this;
        }

        /**
         * You can set margin in left, right and top in px. Bottom is in baseline.
         */
        public Builder setMargin(int left, int top, int right) {
            this.margin.set(left, top, right, 0);
            return this;
        }

        /**
         * @param placeholder The drawable shows on loading image {@code uri}
         */
        public Builder setPlaceHolderImage(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /**
         * Show anim in animate drawable.
         *
         * @param showAnim If set true, gif image would animate immediately.
         */
        public Builder setShowAnimaImmediately(boolean showAnim) {
            this.showAnim = showAnim;
            return this;
        }

        public DraweeSpan build() {
            if (placeholder == null) {
                placeholder = new ColorDrawable(Color.TRANSPARENT);
                placeholder.setBounds(0, 0, width, height);
            }
            DraweeSpan span = new DraweeSpan(uri, verticalAlignment, placeholder, showAnim);
            span.mLayout.set(width, height);
            span.mMargin.set(margin.left, margin.top, margin.right, 0);
            span.layout();
            return span;
        }
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getCachedDrawable();
        canvas.save();
        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
        int fontHeight = fmPaint.descent - fmPaint.ascent;
        int centerY = y + fmPaint.descent - fontHeight / 2;
        int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
//        super.draw(canvas, text, start, end, x + mMargin.left, top, y, bottom, paint);
//        //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
//        // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
//        // drawable的流对象，并最终获取drawable对象
//        Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
//        canvas.save();
//
//        //获取画笔的文字绘制时的具体测量数据
//        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
//
//        //系统原有方法，默认是Bottom模式)
//        int transY = bottom - drawable.getBounds().bottom;
//        if (mVerticalAlignment == ALIGN_BASELINE) {
//            transY -= fm.descent;
//        } else if (mVerticalAlignment == ALIGN_FONTCENTER) {    //此处加入判断， 如果是自定义的居中对齐
//            //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
//            // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
//            transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
//        }
//
//        canvas.translate(x, transY);
//        drawable.draw(canvas);
//        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;
        if (wr != null) {
            d = wr.get();
        }

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }
}
