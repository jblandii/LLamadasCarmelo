package com.jblandii.llamadascarmelo;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jairo on 01/02/2018.
 */

public interface ServicioRegistroLlamadasOnline {
    @POST("llamadas")
    Call<Void> add(@Body Llamadas llamadas);
}