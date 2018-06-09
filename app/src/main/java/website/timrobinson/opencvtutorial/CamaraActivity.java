package website.timrobinson.opencvtutorial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import website.timrobinson.opencvtutorial.armario.ArmarioActivity;
import website.timrobinson.opencvtutorial.armario.PrendaActivity;

public class CamaraActivity extends Base {

    //--- VARIABLES Y CONSTANTES -------------------------------------------------------------------
    TextView tvComplementario;
    TextView complementario;
    int rgbComplementario;
    ImageView ivCaptura;


    private Bitmap prenda1;
    private String colorPrenda1;

    private Bitmap prenda2;
    private String colorPrenda2;

    //Datos de la imagen capturada
    private Bitmap imagen;
    private String colorImagen;

    private boolean segundaPrenda;
    private boolean primerTouch;

    //--- METODOS -------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        ivCaptura = (ImageView) findViewById(R.id.ivCaptura);

        initViews();
        btn_captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!primerTouch) {
                    Toast.makeText(CamaraActivity.this, R.string.msg_selec_color, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CamaraActivity.this, R.string.msg_img_capturada, Toast.LENGTH_SHORT).show();

                    imagen = foto;
                    colorImagen = rgb;


                    ByteArrayOutputStream bytesPrenda1 = new ByteArrayOutputStream();
                    imagen.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda1);


                    final String pathImagen = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), rotarImagen(imagen, 90), "Title", null);

                    ivCaptura.setImageURI(Uri.parse(pathImagen));

//                    // create a Dialog component
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CamaraActivity.this);
                    alertDialogBuilder.setTitle(R.string.title_guardar_img);
                    alertDialogBuilder.setMessage(R.string.msg_guardar_img).setCancelable(false).setPositiveButton(R.string.msg_si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Llama a la actividad de "Añarir al armario" para guardar la imagen
                            Intent i = new Intent(CamaraActivity.this, PrendaActivity.class);

                            i.putExtra("fotoPrenda", Uri.parse(pathImagen).toString());

                            i.putExtra("colorPrenda", colorImagen);
                            startActivity(i);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
                    alertDialogBuilder.show();


                }


//


            }
        });

//        tvComplementario = (TextView) findViewById(R.id.tvComplementario);
//        complementario = (TextView) findViewById(R.id.complementario);
    }

    //Método para rotar la imagen, porque OpenCV la pone girada por defecto
    public static Bitmap rotarImagen(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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

        primerTouch = true;

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cuadrado: {
                Intent i = new Intent(CamaraActivity.this, ArmarioActivity.class);


//                ByteArrayOutputStream bytesPrenda1 = new ByteArrayOutputStream();
//                prenda1.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda1);
//                String pathPrenda1 = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), prenda1, "Title", null);
//                i.putExtra("fotoPrenda1", Uri.parse(pathPrenda1).toString());
//
//                i.putExtra("colorPrenda1", colorPrenda1);
//
//                ByteArrayOutputStream bytesPrenda2 = new ByteArrayOutputStream();
//                prenda2.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda2);
//                String pathPrenda2 = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), prenda2, "Title", null);
//                i.putExtra("fotoPrenda2", pathPrenda2.toString());
//                i.putExtra("colorPrenda2", colorPrenda2);
                startActivity(i);
                break;
            }
//            case R.id.menu_complementario: {
//                Intent i = new Intent(this, CamaraActivity.class);
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
//                Intent i = new Intent(this, ArmarioActivity.class);
//                startActivity(i);
//                break;
//            }
        }
        return super.onOptionsItemSelected(item);
    }
}
