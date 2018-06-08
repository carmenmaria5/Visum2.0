package website.timrobinson.opencvtutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.graphics.ColorUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ComplementarioActivity extends Base {

    //--- VARIABLES Y CONSTANTES -------------------------------------------------------------------
    TextView tvComplementario;
    TextView complementario;
    int rgbComplementario;
    private Bitmap prenda1;
    private String colorPrenda1;

    private Bitmap prenda2;
    private String colorPrenda2;

    private boolean segundaPrenda;
    private boolean primerTouch;

    //--- METODOS -------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complementario);

        initViews();
        btn_captura.setEnabled(false);
        primerTouch = true;
        btn_captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (segundaPrenda) {
                    prenda2 = foto;
                    colorPrenda2 = rgb;
                    Toast.makeText(ComplementarioActivity.this, "Segunda prenda capturada", Toast.LENGTH_SHORT).show();
                    btn_captura.setEnabled(false);
                } else {
                    prenda1 = foto;
                    colorPrenda1 = rgb;
                    segundaPrenda = true;
                    Toast.makeText(ComplementarioActivity.this, "Primera prenda capturada", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        tvComplementario = (TextView) findViewById(R.id.tvComplementario);
//        complementario = (TextView) findViewById(R.id.complementario);
    }

    private void setText() {
        muestra.setTextColor(rgbComplementario);

//        complementario.setBackgroundColor(rgbComplementario);
//        complementario.setText(Integer.toString(rgbComplementario));
//
//        complementario.setText(Color.red(rgbComplementario)+", "+Color.green(rgbComplementario)+", "+Color.blue(rgbComplementario));
//        complementario.setTextColor(Color.rgb((int)r, (int)g, (int)b));
    }

    //*** ALGORITMO ******************************************************************
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);

//
//        if (tHSL[0] + 180.0 > 360) {
//            tHSL[0] = (tHSL[0] + 180) % 360;
//        } else {
//            tHSL[0] += 180;
//        }
//
//        rgbComplementario = ColorUtils.HSLToColor(tHSL);
//
//        setText();

        if (primerTouch) {
            btn_captura.setEnabled(true);
        }


        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_analogo: {
                Intent i = new Intent(ComplementarioActivity.this, CombinarActivity.class);



                ByteArrayOutputStream bytesPrenda1 = new ByteArrayOutputStream();
                prenda1.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda1);
                String pathPrenda1 = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), prenda1, "Title", null);
                i.putExtra("fotoPrenda1", Uri.parse(pathPrenda1).toString());

                i.putExtra("colorPrenda1", colorPrenda1);

                ByteArrayOutputStream bytesPrenda2 = new ByteArrayOutputStream();
                prenda2.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda2);
                String pathPrenda2 = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), prenda2, "Title", null);
                i.putExtra("fotoPrenda2", pathPrenda2.toString());
                i.putExtra("colorPrenda2", colorPrenda2);
                startActivity(i);
                break;
            }
//            case R.id.menu_complementario: {
//                Intent i = new Intent(this, ComplementarioActivity.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.menu_complementariops:{
//                Intent i = new Intent(this, ComplementariaPorSeparadoActivity.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.menu_tetrada:{
//                Intent i = new Intent(this, TetradaActivity.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.menu_triada:{
//                Intent i = new Intent(this, TriadaActivity.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.menu_cuadrado:{
//                Intent i = new Intent(this, CuadradoActivity.class);
//                startActivity(i);
//                break;
//            }
        }
        return super.onOptionsItemSelected(item);
    }
}
