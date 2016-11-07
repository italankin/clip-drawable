package test.textclipdrawable;

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
        int textSize = res.getDimensionPixelSize(R.dimen.textSize);
        TextClipDrawable drawable = new TextClipDrawable.Builder(bitmap)
                .setText("5")
                .setTextSize(textSize)
                .setTextGravity(Gravity.CENTER)
                .build();
        view.setImageDrawable(drawable);
    }

}
