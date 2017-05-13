package o.thor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ooooo(View view) {
//        if (((PlayView) view).isAnim())
//            ((PlayView) view).stopAnim();
//        else
            ((PlayView) view).startAnim();
    }
}
