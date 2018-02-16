package sergioapps.pronosticapp.asynctask;

import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import sergioapps.pronosticapp.R;
import sergioapps.pronosticapp.adaptador.AdapterDatos;
import sergioapps.pronosticapp.adaptador.Atributos;
import sergioapps.pronosticapp.layouts.MainActivity;
import sergioapps.pronosticapp.layouts.Splash;

/**
 * Created by drago on 15/02/2018.
 */

public class refreshTime extends AsyncTask<String,Void,String> {

    String result="";
    URL url ;
    HttpURLConnection connectionpag=null;
    ArrayList<Atributos>listaCompleta;

    @Override
    protected String doInBackground(String... urls) {

        try{
            url =new URL(urls[0]);
            connectionpag = (HttpURLConnection) url.openConnection();
            InputStream in = connectionpag.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while (data != -1){
                char current = (char)data;
                result += current;
                data = reader.read();
            }
            return result;


        }catch (Exception e){
            Log.e("ERROR","ERROR EN BACKGROUND");
        }



        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {

            JSONObject jsonObject = new JSONObject(result);
            String city = jsonObject.getString("timezone");
            MainActivity.city.setText(String.valueOf(city));
            Time time = new Time(Time.getCurrentTimezone());
            time.setToNow();
            int hour2 = time.hour;
            String hour = String.valueOf(hour2);

            Log.e("ERROR",String.valueOf(hour));
            if(hour.contains("4" )|| hour.contains("5" )||hour.contains("6" )|| hour.contains("7")){
                MainActivity.imToday.setImageResource(R.mipmap.manana);
            }
            
            if(hour.contains("8" )|| hour.contains("9" )||hour.contains("10" )|| hour.contains("11" )||
            hour.contains("12" )|| hour.contains("13" )||hour.contains("14" )|| hour.contains("15" )||
                    hour.contains("16")){
                MainActivity.imToday.setImageResource(R.mipmap.dia);
            }
            if(hour.contains("17" )|| hour.contains("18" )||hour.contains("19" )){
                MainActivity.imToday.setImageResource(R.mipmap.tarde);
            }
            if(hour.contains("20" )|| hour.contains("21" )||hour.contains("22" )|| hour.contains("23" )||
                    hour.contains("0" )|| hour.contains("1" )||hour.contains("2" )|| hour.contains("3")){
                Log.e("ERROR",String.valueOf("ENTRA"));
                MainActivity.imToday.setImageResource(R.mipmap.noche);
            }
            JSONObject current = jsonObject.getJSONObject("currently");
            MainActivity.textdate.setText(String.valueOf(time.monthDay)+"/"+
            String.valueOf(time.month+1)+"/"+String.valueOf(time.year));
            String state1= current.getString("icon");
            MainActivity.textgrados.setText(String.valueOf(current.getDouble("temperature"))+"°");
            MainActivity.txt1.setText("Humidity: "+String.valueOf(current.getDouble("humidity")));
            MainActivity.txt2.setText("WindSpeed: "+String.valueOf(current.getDouble("windSpeed")));
            MainActivity.txt3.setText("UV_Index: "+String.valueOf(current.getDouble("uvIndex")));
            switch (state1){
                case "clear-day":
                   MainActivity.imstate.setImageResource(R.drawable.a1);
                    break;
                case "clear-night":
                    MainActivity.imstate.setImageResource(R.drawable.a2);
                    break;
                case "rain":
                    MainActivity.imstate.setImageResource(R.drawable.a3);
                    break;
                case "snow":
                    MainActivity.imstate.setImageResource(R.drawable.a4);
                    break;
                case "sleet":
                    MainActivity.imstate.setImageResource(R.drawable.a5);
                    break;
                case "wind":
                    MainActivity.imstate.setImageResource(R.drawable.a6);
                    break;
                case "fog":
                    MainActivity.imstate.setImageResource(R.drawable.a7);
                    break;
                case "cloudy":
                    MainActivity.imstate.setImageResource(R.drawable.a8);
                    break;
                case "partly-cloudy-day":
                    MainActivity.imstate.setImageResource(R.drawable.a9);
                    break;
                case "partly-cloudy-night":
                    MainActivity.imstate.setImageResource(R.drawable.a10);
                    break;
                case "hail":
                    MainActivity.imstate.setImageResource(R.drawable.a11);
                    break;
                case "thunderstorm":
                    MainActivity.imstate.setImageResource(R.drawable.a12);
                    break;
                case "tornado":
                    MainActivity.imstate.setImageResource(R.drawable.a13);
                    break;
            }




            JSONObject daily = new JSONObject(jsonObject.getString("daily"));
            JSONObject listDaily = jsonObject.getJSONObject("daily");
            JSONArray listData = listDaily.getJSONArray("data");
            int sizelist = listData.length();
            listaCompleta =new ArrayList<>();
            String estado="";
            String fecha="";

            for(int i=0;i<sizelist;i++){
                JSONObject objeto = listData.getJSONObject(i);
                estado= objeto.getString("icon");
                fecha = String.valueOf(time.monthDay+1+i)+"/"+
                        String.valueOf(time.month+1)+"/"+String.valueOf(time.year);
                String temperatura = String.valueOf(objeto.getDouble("temperatureHigh"));
                Log.i("ERROR",fecha);
                switch (estado){
                    case "clear-day":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a1));
                        break;
                    case "clear-night":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a2));
                        break;
                    case "rain":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a3));
                        break;
                    case "snow":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a4));
                        break;
                    case "sleet":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a5));
                        break;
                    case "wind":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a6));
                        break;
                    case "fog":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a7));
                        break;
                    case "cloudy":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a8));
                        break;
                    case "partly-cloudy-day":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a9));
                        break;
                    case "partly-cloudy-night":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a10));
                        break;
                    case "hail":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a11));
                        break;
                    case "thunderstorm":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a12));
                        break;
                    case "tornado":
                        listaCompleta.add(new Atributos(fecha,temperatura+"°",R.drawable.a13));
                        break;
                }

            }
            AdapterDatos adapter = new AdapterDatos(listaCompleta);
            MainActivity.recyclerView.setAdapter(adapter);


            Log.e("ERROR","NINGUN ERROR");

        } catch (Exception e) {
            Log.e("ERROR","ERROR EN despues");
        }

    }
}
