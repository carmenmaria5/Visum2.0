package website.timrobinson.opencvtutorial;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmosCombinaciones {

    private static boolean hayCombinacion = false;

    private static List<float[]> lColores;

    public static boolean combinan(float[] tHSL1, float[] tHSL2) {

        lColores = new ArrayList<float[]>();

        generarColores(tHSL1);
        float huePrenda = tHSL2[0];
        int i = 0;
        Float rango = 12.0f;
        int rangoBN = 15;
        Boolean maxAlcanzado = false;

        int color1 = ColorUtils.HSLToColor(tHSL1);
        int color2 = ColorUtils.HSLToColor(tHSL2);

        int r = Color.red(color1);
        int g = Color.green(color1);
        int b = Color.blue(color1);

        int r2 = Color.red(color2);
        int g2 = Color.green(color2);
        int b2 = Color.blue(color2);

        if (Color.red(color1) == 72 && Color.green(color1) == 37 && Color.blue(color1) == 31) {
            hayCombinacion = false;

        } else {

            //Comprobamos si los valores de RGB están en un rago de +-10 para así comprobar si son
            // negro, blanco o gris y decir que combinan con todos los colores
            if (bt(Color.red(color1), Color.green(color1) - 10, Color.green(color1) + 10)) {
                if (bt(Color.red(color1), Color.blue(color1) - 10, Color.blue(color1) + 10)) {
                    hayCombinacion = true;

                }
            }


            if (bt(Color.red(color2), Color.green(color2) - rangoBN, Color.green(color2) + rangoBN)) {
                if (bt(Color.red(color2), Color.blue(color2) - rangoBN, Color.blue(color2) + rangoBN)) {
                    if (bt(Color.blue(color2), Color.green(color2) - rangoBN, Color.green(color2) + rangoBN)) {
                        hayCombinacion = true;

                    }
                }
            }

            while (!hayCombinacion && !maxAlcanzado) {
                float hueColor = lColores.get(i)[0];


                if (bt(huePrenda, hueColor - rango, hueColor + rango)) {

                    hayCombinacion = true;
                }

                if (i == (lColores.size()-1)){
                    maxAlcanzado = true;
                }

                i++;



            }
        }

        return hayCombinacion;
    }

    private static void generarColores(float[] tHSL) {
        complementario(Arrays.copyOf(tHSL, tHSL.length));
/*        analogo(Arrays.copyOf(tHSL, tHSL.length));
        complementarioPorSeparado(Arrays.copyOf(tHSL, tHSL.length));
        cuadrado(Arrays.copyOf(tHSL, tHSL.length));
        tetradaRectangular(Arrays.copyOf(tHSL, tHSL.length));
        triada(Arrays.copyOf(tHSL, tHSL.length));*/
    }

    public static boolean bt(float i, float min, float max) {
        return (i >= min && i <= max);
    }

    //--- COMPLEMENTARIO -------------------------------------------
    private static void complementario(float[] tHSL) {
        if (tHSL[0] + 180.0 > 360) {
            tHSL[0] = (tHSL[0] + 180) % 360;
        } else {
            tHSL[0] += 180;
        }

        lColores.add(tHSL);
    }

    //--- ANÁLOGO --------------------------------------------------
    public static void analogo(float[] tHSL) {

        float[] rgbAnalogo = new float[2];

        //Guardo el color inicial
        float hue = tHSL[0];

        if (tHSL[0] + 30.0 > 360) {
            tHSL[0] = (tHSL[0] + 30) % 360;
        } else {
            tHSL[0] += 30.0;
        }

        float[] hslAnalogo1 = Arrays.copyOf(tHSL, tHSL.length);

        tHSL[0] = hue;

        if (tHSL[0] - 30.0 < 0) {
            tHSL[0] = Math.abs(tHSL[0]);
        } else {
            tHSL[0] -= 30.0;
        }

        float[] hslAnalogo2 = Arrays.copyOf(tHSL, tHSL.length);

        lColores.add(hslAnalogo1);
        lColores.add(hslAnalogo2);
    }

    //--- COMPLEMENTARIO POR SEPARADO --------------------------------------------------
    public static void complementarioPorSeparado(float[] tHSL) {

        //Calculo el complementario
        tHSL[0] = (tHSL[0] + 180) % 360;

        //Guardo el complementario del color inicial
        float hueComplementario = tHSL[0];

        //Calculo el primer valor
        if (hueComplementario + 30.0 > 360) {
            hueComplementario = (hueComplementario + 30) % 360;
        } else {
            hueComplementario += 30.0;
        }

        float[] comp1 = new float[3];
        comp1[0] = hueComplementario;
        comp1[1] = tHSL[1];
        comp1[2] = tHSL[2];

        lColores.add(comp1);

        hueComplementario = tHSL[0];

        //Calculo el segundo valor
        if (hueComplementario - 30.0 < 0) {
            hueComplementario = Math.abs(tHSL[0]);
        } else {
            hueComplementario -= 30.0;
        }

        float[] comp2 = new float[3];
        comp2[0] = hueComplementario;
        comp2[1] = tHSL[1];
        comp2[2] = tHSL[2];

        lColores.add(comp2);
    }

    //--- CUADRADO --------------------------------------------------
    public static void cuadrado(float[] tHSL) {

        //Guardo el color original para utilizarlo después
        float hue = tHSL[0];

        //Calculo el primer color
        if (tHSL[0] + 90.0 > 360) {
            tHSL[0] = (tHSL[0] + 90) % 360;
        } else {
            tHSL[0] += 90.0;
        }

        //Guardo el color calculado
        float[] cuadrado1 = new float[3];
        cuadrado1[0] = tHSL[0];
        cuadrado1[1] = tHSL[1];
        cuadrado1[2] = tHSL[2];

        lColores.add(cuadrado1);

        //Vuelvo a partir del color original para buscar el segundo color
        tHSL[0] = hue;

        //Calculo el complementario del original, obteniendo así el segundo
        //color buscado
        tHSL[0] = (tHSL[0] + 180) % 360;

        //Guardo el color calculado
        float[] cuadrado2 = new float[3];
        cuadrado2[0] = tHSL[0];
        cuadrado2[1] = tHSL[1];
        cuadrado2[2] = tHSL[2];

        lColores.add(cuadrado2);

        //Vuelvo a partir del color original para buscar el segundo color
        tHSL[0] = hue;

        if (tHSL[0] + 270.0 > 360) {
            tHSL[0] = (tHSL[0] + 270) % 360;
        } else {
            tHSL[0] += 270.0;
        }

        //Guardo el color calculado
        float[] cuadrado3 = new float[3];
        cuadrado3[0] = tHSL[0];
        cuadrado3[1] = tHSL[1];
        cuadrado3[2] = tHSL[2];

        lColores.add(cuadrado3);
    }

    //--- TÉTRADA RECTANGULAR --------------------------------------------------
    public static void tetradaRectangular(float[] tHSL) {

        //Guardo el color original para utilizarlo después
        float hue = tHSL[0];

        //Calculo el complementario del original, obteniendo así el primer color
        //buscado
        tHSL[0] = (tHSL[0] + 180) % 360;

        //Guardo el color calculado
        float[] tetrada1 = new float[3];
        tetrada1[0] = tHSL[0];
        tetrada1[1] = tHSL[1];
        tetrada1[2] = tHSL[2];

        lColores.add(tetrada1);

        //Vuelvo a partir del color original para buscar el segundo color para
        //realizar la segunda díada, es decir, el que está separado del primero
        //60º
        tHSL[0] = hue;

        if (tHSL[0] + 60.0 > 360) {
            tHSL[0] = (tHSL[0] + 60) % 360;
        } else {
            tHSL[0] += 60.0;
        }

        //Guardo el color calculado
        float[] tetrada2 = new float[3];
        tetrada2[0] = tHSL[0];
        tetrada2[1] = tHSL[1];
        tetrada2[2] = tHSL[2];

        lColores.add(tetrada2);

        //Calculo el complementario de éste último color
        tHSL[0] = tetrada1[0];

        if (tHSL[0] + 180.0 > 360) {
            tHSL[0] = (tHSL[0] + 180) % 360;
        } else {
            tHSL[0] += 180;
        }

        //Guardo el último color calculado
        float[] tetrada3 = new float[3];
        tetrada3[0] = tHSL[0];
        tetrada3[1] = tHSL[1];
        tetrada3[2] = tHSL[2];

        lColores.add(tetrada3);
    }

    //--- TRIADA --------------------------------------------------
    public static void triada(float[] tHSL) {

        for (int i = 0; i < 2; i++) {
            if (tHSL[0] + 120.0 > 360) {
                tHSL[0] = (tHSL[0] + 120) % 360;
            } else {
                tHSL[0] += 120.0;
            }

            lColores.add(Arrays.copyOf(tHSL, tHSL.length));
        }

    }

}
