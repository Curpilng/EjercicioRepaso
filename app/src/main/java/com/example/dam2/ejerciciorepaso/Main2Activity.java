package com.example.dam2.ejerciciorepaso;

/*
    Sergio Vera Leal
    Versión 1.0
 */

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.Arrays;

public class Main2Activity extends Activity {
    //Declaramos variables.
    Button continua;
    Spinner sProvincias,sLocalidades;
    ArrayAdapter<String> adap_provincias;
    ArrayAdapter<CharSequence> adap_localidades;
    EditText iNombre;
    Switch sNotificacion;
    String provincia ="";
    String localidad="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Buscamos los id en el xml.
        continua = (Button) findViewById(R.id.continua);
        sProvincias = (Spinner) findViewById(R.id.sProvincias);
        sLocalidades = (Spinner) findViewById(R.id.sLocalidades);
        iNombre = (EditText) findViewById(R.id.iNombre);
        sNotificacion = (Switch) findViewById(R.id.sNotificacion);

        //Llamamos al método.
        llenarProvincias();

        //Llamamos al método.
        llenarLocalidades();

        //Llamamos al método.
        continua();
    }

    //Método que recoge la información y nos devuelve al Activity 1.
    public void continua(){
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                Bundle b = new Bundle();

                b.putString("nombre",iNombre.getText().toString());
                b.putString("provincia", provincia);
                b.putString("localidad", localidad);
                b.putBoolean("notificacion", sNotificacion.isChecked());

                i.putExtras(b);

                setResult(RESULT_OK, i);
                finish();
            }
        });
    }


    //Método que rellena el spinner Provincias.
    public void llenarProvincias(){
        //Llenamos el array con la información almacenada en el xml.
        String array_provincias[] = getResources().getStringArray(R.array.provincias);

        //Creamos el adaptador para el spinner.
        adap_provincias = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array_provincias);

        //Ponemos el adaptador en el spinner.
        sProvincias.setAdapter(adap_provincias);
    }

    //Método que rellena el spinner Localidades.
    public void llenarLocalidades(){
        //Creamos el adaptador para el spinner.
        adap_localidades = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item);

        sProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Recogemos todas las 'id' de los arrays almacenados en el xml.
                TypedArray ta = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);

                //Llenamos el array con la información almacenada en el xml dependiendo de que provincia hemos seleccionado.
                CharSequence array_localidades[] = ta.getTextArray(position);

                //Recogemos en una variable la provincia seleccionada.
                provincia = sProvincias.getSelectedItem().toString();

                //Borramos y rellenamos el spinner.
                adap_localidades.clear();
                adap_localidades.addAll(array_localidades);

                //Ponemos el adaptador en el spinner.
                sLocalidades.setAdapter(adap_localidades);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sLocalidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Recogemos en una variable la localidad seleccionada.
                localidad = sLocalidades.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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
