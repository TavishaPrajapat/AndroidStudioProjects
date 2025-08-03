package com.tavisha.moviehub;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class PosterGenerator {

    private static final Map<String, Integer> genreColors = new HashMap<>();
    static {
        genreColors.put("Action", Color.parseColor("#A0937D"));
        genreColors.put("Comedy", Color.parseColor("#E7D4B5"));
        genreColors.put("Drama", Color.parseColor("#F6E6CB"));
        genreColors.put("Sci-Fi", Color.parseColor("#B6C7AA"));
        genreColors.put("Horror", Color.parseColor("#A0937D"));
        genreColors.put("Romance", Color.parseColor("#E7D4B5"));
    }

    public static Drawable generatePoster(Context context, Movie movie) {
        return new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Rect bounds = getBounds();
                int width = bounds.width();
                int height = bounds.height();

                // Draw background
                Paint backgroundPaint = new Paint();
                backgroundPaint.setColor(getColorForGenre(movie.getGenres().get(0)));
                canvas.drawRect(bounds, backgroundPaint);

                // Draw text
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(36);
                textPaint.setAntiAlias(true);

                String text = movie.getName();
                StaticLayout textLayout = StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, width - 20)
                        .setAlignment(Layout.Alignment.ALIGN_CENTER)
                        .setLineSpacing(0, 1.0f)
                        .setIncludePad(false)
                        .build();

                canvas.save();
                canvas.translate(10, (height - textLayout.getHeight()) / 2f);
                textLayout.draw(canvas);
                canvas.restore();
            }

            @Override
            public void setAlpha(int alpha) {}

            @Override
            public void setColorFilter(android.graphics.ColorFilter colorFilter) {}

            @Override
            public int getOpacity() {
                return android.graphics.PixelFormat.OPAQUE;
            }
        };
    }

    private static int getColorForGenre(String genre) {
        return genreColors.getOrDefault(genre, Color.parseColor("#F6E6CB"));
    }
}

