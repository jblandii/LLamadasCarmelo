package com.jblandii.llamadascarmelo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Date;

/**
 * Created by jairo on 01/02/2018.
 */

public abstract class ReceptorLlamadas extends BroadcastReceiver {

    /*
    Necesitamos el uso de variables estáticas para recordar datos entre instancias
     */

    private static int ultimoEstado = TelephonyManager.CALL_STATE_IDLE;
    private static Date horaLlamadaInicio;
    private static boolean entrante;
    private static String numeroGuardado;


    @Override
    public void onReceive(Context context, Intent intent) {

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            numeroGuardado = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String numeroTelefono = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int estado = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                estado = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                estado = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                estado = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, estado, numeroTelefono);
        }
    }


    protected void onOutgoingCallStarted(Context ctx, String numeroTelefono, Date inicioLlamada) {
    }

    protected void onOutgoingCallEnded(Context ctx, String numeroTelefono, Date inicioLlamada, Date finLlamada) {
    }

    protected void onIncomingCallEnded(Context ctx, String numeroTelefono, Date inicioLlamada, Date finLlamada) {
    }

    protected void onMissedCall(Context ctx, String numeroTelefono, Date inicioLlamada) {
    }

    protected void onIncomingCallStarted(Context ctx, String numeroTelefono, Date inicioLlamada) {
    }

    public void onCallStateChanged(Context context, int estado, String numeroTelefono) {
        if (ultimoEstado == estado) {
            return;
        }
        switch (estado) {
            case TelephonyManager.CALL_STATE_RINGING:
                entrante = true;
                numeroGuardado = numeroTelefono;
                horaLlamadaInicio = new Date();
                onIncomingCallStarted(context, numeroTelefono, horaLlamadaInicio);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (ultimoEstado != TelephonyManager.CALL_STATE_RINGING) {
                    horaLlamadaInicio = new Date();
                    entrante = false;
                    onOutgoingCallStarted(context, numeroGuardado, horaLlamadaInicio);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (ultimoEstado == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, numeroGuardado, horaLlamadaInicio);
                } else if (entrante) {
                    onIncomingCallEnded(context, numeroGuardado, horaLlamadaInicio, new Date());
                } else {
                    onOutgoingCallEnded(context, numeroGuardado, horaLlamadaInicio, new Date());
                }
                break;
        }
        ultimoEstado = estado;
    }
}