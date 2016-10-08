package br.com.fiap.java.helpers;

/**
 * Created by lucario on 9/28/16.
 */
public enum WebservicesUrl {
    Login("http://13.65.98.255:1226/login"),
    Cadastrar("http://13.65.98.255:1226/cadastrar"),
    Noticias("http://13.65.98.255:1226/noticias");

    private String url;
    //private String IP = "192.168.0.113";
    //private String IP = "10.0.2.2";a

    WebservicesUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }


}
