package com.jblandii.llamadascarmelo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jairo on 01/02/2018.
 */

public class ServicioRegistroLLamadas extends Service {
    public ServicioRegistroLLamadas() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tipo = intent.getStringExtra("tipo");
        String numeroTelefono = intent.getStringExtra("numeroTelefono");

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
        Date inicioLlamada = new Date();
        inicioLlamada.setTime(intent.getLongExtra("inicioLlamada", 0));

        Date finLlamada = new Date();
        finLlamada.setTime(intent.getLongExtra("finLlamada", 0));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://llamadas-jblandii.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioRegistroLlamadasOnline onlineService = retrofit.create(ServicioRegistroLlamadasOnline.class);
        Call<Void> llamada = onlineService.add(new Llamadas(tipo, numeroTelefono, inicioLlamada, finLlamada));

        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("mensaje", "Se ha agregado la llamada al registro.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("mensaje","No se ha podido agregar la llamada.");
            }
        });

        return START_REDELIVER_INTENT;
    }
}
