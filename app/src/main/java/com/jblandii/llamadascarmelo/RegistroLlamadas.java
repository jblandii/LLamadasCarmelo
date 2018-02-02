package com.jblandii.llamadascarmelo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by jairo on 01/02/2018.
 */

public class RegistroLlamadas extends ReceptorLlamadas {

    @Override
    protected void onIncomingCallStarted(Context ctx, String numero, Date inicioLlamada) {
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String numero, Date inicioLlamada) {
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String numeroTelefono, Date inicioLlamada, Date finLlamada) {
        Log.v("mensaje", "llamada entrante " + numeroTelefono + " " + inicioLlamada.toString() + " - " + finLlamada.toString());
        Intent intent = new Intent(ctx, ServicioRegistroLLamadas.class);
        intent.putExtra("tipo", "entrante");
        intent.putExtra("numeroTelefono", numeroTelefono);
        intent.putExtra("inicioLlamada", inicioLlamada.getTime());
        intent.putExtra("finLlamada", finLlamada.getTime());
        ctx.startService(intent);
    }

    @Override
    protected void onMissedCall(Context ctx, String numeroTelefono, Date inicioLlamada) {
        Log.v("mensaje", "llamada perdida " + numeroTelefono + " " + inicioLlamada.toString());
        Intent intent = new Intent(ctx, ServicioRegistroLLamadas.class);
        intent.putExtra("tipo", "perdida");
        intent.putExtra("numeroTelefono", numeroTelefono);
        intent.putExtra("inicioLlamada", inicioLlamada.getTime());
        intent.putExtra("finLlamada", inicioLlamada.getTime());
        ctx.startService(intent);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String numeroTelefono, Date inicioLlamada, Date finLlamada) {
        Log.v("mensaje", "llamada saliente " + numeroTelefono + " " + inicioLlamada.toString() + " - " + finLlamada.toString());
        Intent intent = new Intent(ctx, ServicioRegistroLLamadas.class);
        intent.putExtra("tipo", "saliente");
        intent.putExtra("numeroTelefono", numeroTelefono);
        intent.putExtra("inicioLlamada", inicioLlamada.getTime());
        intent.putExtra("finLlamada", finLlamada.getTime());
        ctx.startService(intent);
    }
}
