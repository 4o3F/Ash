package moe.exusiaimoe.network;

import java.net.HttpURLConnection;
import java.net.URL;

public class Mojang {
    public static boolean MojangUserNameExist(String username) {
        //String url = "https://api.mojang.com/users/profiles/minecraft/" + username;
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if (status == 204) {
                con.disconnect();
                return false;
            } else if (status == 200) {
                con.disconnect();
                return true;
            } else {
                con.disconnect();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
