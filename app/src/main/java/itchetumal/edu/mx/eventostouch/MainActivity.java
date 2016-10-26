package itchetumal.edu.mx.eventostouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    float circCoordX = 0, circCoordY = 0;
            //, coordIniX = 0, coordIniY = 0;
    String mensaje = "Sin evento";
    Path ruta = new Path();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutP = (LinearLayout) findViewById(R.id.layoutPrinc);

        Lienzo areaDibujo = new Lienzo(this);

        layoutP.addView(areaDibujo);
    }

    class Lienzo extends View {

        float x1, y1;
        boolean clickOrigen = false;

        public Lienzo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            Paint pincel = new Paint();

            if (!clickOrigen) {
                x1 = (float) (Math.random() * this.getMeasuredWidth());
                y1 = (float) (Math.random() * this.getMeasuredHeight());

                pincel.setColor(Color.RED);
                canvas.drawCircle(x1, y1, 40, pincel);

                //dibujado de origen
                pincel.setColor(Color.MAGENTA);
                pincel.setStrokeWidth(3);
                canvas.drawLine(x1, y1 - 100, x1, y1 + 100, pincel);
                canvas.drawLine(x1 - 100, y1, x1 + 100, y1, pincel);
            }

            pincel.setAntiAlias(true);

            pincel.setColor(Color.BLACK);
            canvas.drawCircle(circCoordX, circCoordY, 40, pincel);

            pincel.setTextSize(20);
            canvas.drawText("X = " + circCoordX + " Y = " + circCoordY,
                    0, this.getMeasuredHeight() - 25, pincel);
            canvas.drawText(mensaje, 0, this.getMeasuredHeight()-10, pincel);
            //canvas.drawLine(coordIniX, coordIniY, circCoordX, circCoordY, pincel);
            canvas.drawPath(ruta, pincel);
        }

        //Métodos para eventos Touch
        @Override
        public boolean onTouchEvent(MotionEvent evento){

            circCoordX = evento.getX();
            circCoordY = evento.getY();

            if (evento.getAction() == MotionEvent.ACTION_DOWN){
                //coordIniX = evento.getX();
                //coordIniY = evento.getY();
                ruta.moveTo(circCoordX, circCoordY);
                mensaje = "Evento Down";

                if (Math.sqrt(Math.pow(circCoordX-x1, 2) + Math.pow(circCoordY-y1,2)) <= 20){
                    mensaje = "Diste click en el círculo rojo";
                    clickOrigen = true;
                }
                else clickOrigen = false;
                //validación click en origen
                //coordenadas del origen
            }

            if (evento.getAction() == MotionEvent.ACTION_UP){
                ruta.reset();
                clickOrigen = false;
                mensaje = "Evento Up";
            }

            if (evento.getAction() == MotionEvent.ACTION_MOVE){
                mensaje = "Evento Move";

                ruta.lineTo(circCoordX, circCoordY);
            }

            this.invalidate();

            return true;
        }
    } //Lienzo

}













