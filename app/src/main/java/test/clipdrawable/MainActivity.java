package test.clipdrawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = (ImageView) findViewById(R.id.view);
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.unnamed);
        ClipDrawable drawable = new ClipDrawable.Builder(bitmap)
                .setText("5")
                .setTextSize(res.getDimensionPixelSize(R.dimen.textSize))
                .setTextGravity(Gravity.CENTER)
                .build();
        view.setImageDrawable(drawable);
    }

}
