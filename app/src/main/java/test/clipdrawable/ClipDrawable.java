package test.clipdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.Gravity;

public class ClipDrawable extends Drawable {

    private final Paint paint;
    private final Path path;

    private ClipDrawable(Bitmap bitmap, Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
        setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    public static class Builder {
        private final Bitmap bitmap;
        private final TextPaint paint;
        private String text = "";
        private int gravity = Gravity.CENTER;

        public Builder(Bitmap bitmap) {
            this.bitmap = bitmap;
            this.paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            this.paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        }

        public Builder setText(String text) {
            this.text = text != null ? text : "";
            return this;
        }

        public Builder setTextSize(float size) {
            paint.setTextSize(size);
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            paint.setTypeface(typeface);
            return this;
        }

        public Builder setTextGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public ClipDrawable build() {
            Path textPath = new Path();
            // get text path
            paint.getTextPath(text, 0, text.length(), 0, 0, textPath);
            RectF textRect = new RectF();
            // compute bounds of text path (to apply gravity)
            textPath.computeBounds(textRect, false);
            Rect textPathRect = new Rect();
            textPathRect.set((int) textRect.left, (int) textRect.top, (int) textRect.right, (int) textRect.bottom);

            // apply gravity to text
            Rect parentRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect textRectOffsets = new Rect();
            Gravity.apply(gravity, textPathRect.width(), textPathRect.height(), parentRect, textRectOffsets);
            // rect, computed by Paint.getTextPath() has special offsets within its coordinates, so we
            // need take them into account when offsetting the path
            textPath.offset(textRectOffsets.left - textPathRect.left, textRectOffsets.top - textPathRect.top);

            // create resulting path
            Path resultPath = new Path();
            resultPath.addRect(parentRect.left, parentRect.top, parentRect.right, parentRect.bottom,
                    Path.Direction.CW);
            resultPath.op(textPath, Path.Op.DIFFERENCE);
            return new ClipDrawable(bitmap, paint, resultPath);
        }

    }

}
