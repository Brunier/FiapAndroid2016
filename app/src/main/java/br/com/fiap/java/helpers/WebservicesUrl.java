package br.com.fiap.java.helpers;

/**
 * Created by lucario on 9/28/16.
 */
public enum WebservicesUrl {
    Login("http://192.168.0.113:1225/login"),
    Cadastrar("http://192.168.0.113:1225/cadastrar"),
    Noticias("http://192.168.0.113:1225/noticias");

    private String url;
    //private String IP = "192.168.0.113";
    //private String IP = "10.0.2.2";

    WebservicesUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }


}
