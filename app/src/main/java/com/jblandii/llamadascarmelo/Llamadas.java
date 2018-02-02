package com.jblandii.llamadascarmelo;

import java.util.Date;

/**
 * Created by jairo on 01/02/2018.
 */

public class Llamadas {

    private String tipoLLamada, numeroTelefono;
    private Date inicioLlamada, finLlamada;

    public Llamadas(String tipoLLamada, String numeroTelefono, Date inicioLlamada, Date finLlamada) {
        this.tipoLLamada = tipoLLamada;
        this.numeroTelefono = numeroTelefono;
        this.inicioLlamada = inicioLlamada;
        this.finLlamada = finLlamada;
    }
}
