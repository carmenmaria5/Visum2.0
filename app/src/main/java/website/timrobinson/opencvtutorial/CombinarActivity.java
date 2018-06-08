package website.timrobinson.opencvtutorial;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CombinarActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        Uri imgURIPrenda1 = Uri.parse(getIntent().getStringExtra("fotoPrenda1"));
        mImageView.setImageURI(imgURIPrenda1);
        Uri imgURIPrenda2 = Uri.parse(getIntent().getStringExtra("fotoPrenda2"));
        mImageView2.setImageURI(imgURIPrenda2);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        mImageView3 = (ImageView) findViewById(R.id.imageView3);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // TODO 18/06/08
                break;
            default:
                break;
        }
    }
}
