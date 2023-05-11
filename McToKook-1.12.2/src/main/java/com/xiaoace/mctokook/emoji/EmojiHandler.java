package com.xiaoace.mctokook.emoji;

import com.xiaoace.mctokook.McToKook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class EmojiHandler {

    private final LinkedHashMap<String,Character> emojis;
    private final McToKook mod;

    public LinkedHashMap<String,Character> getEmojis(){
        return emojis;
    }

    public EmojiHandler(McToKook mod){
        this.mod = mod;

        emojis = new LinkedHashMap<String, Character>();

        load(mod);
    }

    public void disable(){
        emojis.clear();
    }

    public void load(McToKook mod){
        disable();
        loadEmojis();
    }

    private void loadEmojis(){

        char emojiChar = '가';

        try {
            InputStream listInput = getClass().getResourceAsStream("/list.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                if (line.startsWith("#")){
                    continue;
                }
                emojis.put(line, emojiChar++);
            }
            bufferedReader.close();
            listInput.close();
        }catch (Exception e){
            McToKook.logger.warn("An error occurred while loading emojis. More info below.");
            e.printStackTrace();
        }

    }

    public String toEmoji(String message){
        for (String key : getEmojis().keySet()){
            message = message.replace(key, getEmojis().get(key).toString());
        }
        return message;
    }
}
