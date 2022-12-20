package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.btnRecuperar);
        textoResultado = findViewById(R.id.texto);

        botaoRecuperar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask task = new MyTask();
                String urlApi = "https://blockchain.info/ticker";
                task.execute(urlApi);

            }
        });
    }
    public class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String stringURL = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer stringBuffer = new StringBuffer();
            try {
                URL url = new URL(stringURL);
                HttpsURLConnection conexao = (HttpsURLConnection)  url.openConnection();
                inputStream = conexao.getInputStream();
                //Lê os dados em Bytes e decodificar para caracteres.
                inputStreamReader = new InputStreamReader(inputStream);
                //Objeto para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String linha = "";
                while ((linha = reader.readLine()) != null) {
                    stringBuffer.append(linha);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuffer.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textoResultado.setText(s);
        }
    }
}

