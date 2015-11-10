package com.example.dam2.ejerciciorepaso;

/*
    Sergio Vera Leal
    Versión 1.0
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //Declaramos variables.
    TextView nPulsaciones,resultado;
    Button bPulsa;
    int contador = 0;
    int contador2 = 0;
    final int MAIN2ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buscamos los id en el xml.
        nPulsaciones = (TextView) findViewById(R.id.nPulsaciones);
        resultado = (TextView) findViewById(R.id.resultado);
        bPulsa = (Button) findViewById(R.id.bPulsa);


        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(getIntent().getExtras()==null){
            //Borramos la notificación.
            nm.cancel(2);
        }else{
            //Borramos la notificación.
            nm.cancel(2);

            //Cogemos el bundle y rellenamos datos.
            Bundle bundle = getIntent().getExtras();
            nPulsaciones.setText(String.valueOf(bundle.getInt("contador")));
            contador = bundle.getInt("contador");
            bPulsa.setEnabled(true);
        }

        //Llamamos al método.
        pulsarBoton();

    }

    //Método que controla el funcionamiento del botón.
    public void pulsarBoton(){

        bPulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contador2 < 5) {
                    //La cuenta de los contadores.
                    contador2 = contador2 + 1;
                    contador = contador + 1;

                    nPulsaciones.setText(String.valueOf(contador));
                    //Llamamos al método para que nos muestre la notificación simple.
                    notificarPulsaciones(contador);

                    if(contador2==5){
                        //Ponemos contador2 a 0 para seguir llevando la cuenta de que no pase de 5 clicks.
                        contador2 = 0;

                        //Lanzamos el siguiente Activity.
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivityForResult(i, MAIN2ACTIVITY);
                    }
                }

            }
        });
    }

    //Método para realizar la notificación simple.
    public void notificarPulsaciones(int contador){
        Notification.Builder ncb = new Notification.Builder(this);

        ncb.setContentTitle("Pulsaciones");
        ncb.setContentText(String.valueOf(contador));
        ncb.setSmallIcon(R.mipmap.ic_launcher);

        ncb.setAutoCancel(true);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, ncb.build());



    }
    

    //Método que recoge el resultado del segundo activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case MAIN2ACTIVITY:
                resultado(resultCode, data);
                break;
        }
    }


    //Método que recoge la información que se ha enviado desde el segundo activity.
    public void resultado(int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            //Recogemos el bundle.
            Bundle b = data.getExtras();
            String nombre = b.getString("nombre");
            String provincia = b.getString("provincia");
            String localidad = b.getString("localidad");

            //Si el usuario quiere notificación se hará una cosa o otra.
            if(b.getBoolean("notificacion")){
                //Llamamos al método que crea una notificación expandida.
                notificacionExpandida(nombre,provincia,localidad);
                resultado.setText("Elige continuar o resetear");
                bPulsa.setEnabled(false);
            }else{
                resultado.setText(nombre+", "+localidad+"("+provincia+")"+", has pulsado "+String.valueOf(contador)+" veces");
            }

        }else{
            Toast.makeText(getApplicationContext(),"Error en el Main2Activity",Toast.LENGTH_LONG).show();
        }
    }

    //Método que crea la notificación expandida.
    public void notificacionExpandida(String nombre, String provincia, String localidad){
        Notification.Builder ncb2 = new Notification.Builder(this);

        ncb2.setContentTitle("Notificación");
        ncb2.setContentText("Resultados");
        ncb2.setSmallIcon(R.mipmap.ic_launcher);

        Notification.InboxStyle iS = new Notification.InboxStyle();
        iS.addLine(nombre);
        iS.addLine(localidad + "(" + provincia + ")");
        iS.addLine("Has pulsado " + String.valueOf(contador) + " veces");

        ncb2.setStyle(iS);


        //Acción continuar.
        Intent i3 = new Intent(this,MainActivity.class);
        Bundle b2 = new Bundle();
        b2.putInt("contador", contador);
        i3.putExtras(b2);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, i3, PendingIntent.FLAG_UPDATE_CURRENT);

        ncb2.setContentIntent(pendingIntent);
        ncb2.addAction(R.mipmap.ic_launcher, "Continuar", pendingIntent);

        //Acción resetear.
        Intent i2 = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 3, i2, PendingIntent.FLAG_UPDATE_CURRENT);

        ncb2.setContentIntent(pendingIntent2);
        ncb2.addAction(R.mipmap.ic_launcher, "Resetear", pendingIntent2);

        NotificationManager nm2 = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm2.notify(2, ncb2.build());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
