package br.com.fiap.java.helpers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lucario on 9/29/16.
 */
public class WebServiceHelper {
    public static String getRequest(String urlString) {
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if(connection.getResponseCode() == 200) {
                BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linha = "";
                StringBuilder resposta = new StringBuilder();
                while((linha = stream.readLine()) != null) {
                    resposta.append(linha);
                }

                connection.disconnect();
                return resposta.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String postRequest(String urlString, JSONObject json) {
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json.toString());
            writer.close();
            os.close();

            if(connection.getResponseCode() == 200) {
                BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linha = "";
                StringBuilder resposta = new StringBuilder();
                while((linha = stream.readLine()) != null) {
                    resposta.append(linha);
                }

                connection.disconnect();
                return resposta.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
