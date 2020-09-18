package com.github.shima710.rai;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ibm.icu.text.Transliterator;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;

public class r2j implements Listener{

    public static Rai plugin;
    public r2j(Rai instance) { plugin = instance; }

    // GoogleAPI
    static final String GOOGLE_API = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text=";


    @EventHandler
    public static void r2j(AsyncPlayerChatEvent event) throws IOException {
        // contentR=変換前ローマ字文字列
        String contentR = event.getMessage();

        // ICU4J ローマ字-平仮名変換モード
        Transliterator trans = Transliterator.getInstance("Latin-Hiragana");

        // contentJ=変後平仮名文字列
        String contentH = trans.transliterate(contentR);

        // Googleに送信
        URL url = new URL(GOOGLE_API + URLEncoder.encode(contentH, "UTF-8"));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String json = CharStreams.toString(reader);
        String contentJ = parseJson(json);

        // 返還後をsend
        event.setMessage(ChatColor.WHITE + contentJ + ChatColor.GRAY + " ("+ contentR +")");
    }

    public static String parseJson(String json) {
        StringBuilder result = new StringBuilder();
        Iterator pj = ((JsonArray)(new Gson()).fromJson(json, JsonArray.class)).iterator();

        while(pj.hasNext()) {
            JsonElement response = (JsonElement)pj.next();
            result.append(response.getAsJsonArray().get(1).getAsJsonArray().get(0).getAsString());
        }
        return result.toString();
    }

}
