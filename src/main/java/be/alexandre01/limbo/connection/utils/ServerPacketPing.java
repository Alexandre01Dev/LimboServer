package be.alexandre01.limbo.connection.utils;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 22:30
*/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;

@Getter @AllArgsConstructor @NoArgsConstructor
public class ServerPacketPing {
    // structure
    /*
    {
    "version": {
        "name": "1.19.4",
        "protocol": 762
    },
    "players": {
        "max": 100,
        "online": 5,
        "sample": [
            {
                "name": "thinkofdeath",
                "id": "4566e69f-c907-48ee-8d71-d7ba5aa00d20"
            }
        ]
    },
    "description": {
        "text": "Hello, world!"
    },
    "favicon": "data:image/png;base64,<data>",
    "enforcesSecureChat": false,
    "previewsChat": false
}
     */

    private String versionName;
    private int protocol;
    private Integer maxPlayers;
    private Integer onlinePlayers;
    private String playerName;
    private String playerId;
    private String description;
    private String favicon;
    private Boolean enforcesSecureChat;
    private Boolean previewsChat;

    @Override
    public String toString() {
        // to json
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");

        stringBuilder.append("    \"version\": {\n");
        if(versionName != null)
            stringBuilder.append("        \"name\": \"").append(versionName).append("\",\n");
        stringBuilder.append("        \"protocol\": ").append(protocol).append("\n");
        stringBuilder.append("    },\n");
        if(maxPlayers != null && onlinePlayers != null){
            stringBuilder.append("    \"players\": {\n");
            stringBuilder.append("        \"max\": ").append(maxPlayers).append(",\n");
            stringBuilder.append("        \"online\": ").append(onlinePlayers).append(",\n");

            stringBuilder.append("        \"sample\": [");

        if(playerName != null && playerId != null){
            stringBuilder.append("\n");
            stringBuilder.append("            {\n");
            stringBuilder.append("                \"name\": \"").append(playerName).append("\",\n");
            stringBuilder.append("                \"id\": \"").append(playerId).append("\"\n");
            stringBuilder.append("            }\n");
        }
        stringBuilder.append("]\n");

        stringBuilder.append("    }");
        }
        if(description != null){
            if(maxPlayers != null && onlinePlayers != null){
                stringBuilder.append(",\n");
            }
           // stringBuilder.append(",\n");
            stringBuilder.append("    \"description\": {\n");
            stringBuilder.append("        \"text\": \"").append(description).append("\"\n");
            stringBuilder.append("    }");
        }

        boolean sep = false;
        if(favicon != null){
            stringBuilder.append(",\n");
            sep = true;
            stringBuilder.append("    \"favicon\": \"").append(favicon).append("\"");
        }

        if(enforcesSecureChat != null && previewsChat != null){
            if(!sep){
                stringBuilder.append(",\n");
                sep = true;
            }
            if(favicon != null){
                stringBuilder.append(",\n");
            }
            stringBuilder.append("    \"enforcesSecureChat\": ").append(enforcesSecureChat).append(",\n");
            stringBuilder.append("    \"previewsChat\": ").append(previewsChat).append("\n");
        }else {
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");

        return stringBuilder.toString();
        // pretty print
        //return gson.toJson(jsonObject);
    }



    public static class Builder {
        ServerPacketPing serverPacketPing = new ServerPacketPing();

        public Builder players(int maxPlayers, int onlinePlayers, String playerName, String playerId) {
            serverPacketPing.maxPlayers = maxPlayers;
            serverPacketPing.onlinePlayers = onlinePlayers;
            serverPacketPing.playerName = playerName;
            serverPacketPing.playerId = playerId;
            return this;
        }

        public Builder version(String versionName, int protocol) {
            serverPacketPing.versionName = versionName;
            serverPacketPing.protocol = protocol;
            return this;
        }

        public Builder description(String description) {
            serverPacketPing.description = description;
            return this;
        }


        public Builder favicon(String favicon) {
            serverPacketPing.favicon = "data:image/png;base64," + favicon;
            return this;
        }
        public Builder favicon(InputStream favicon) {
            // data:image/png;base64,<data>
            try {
                ByteBuf byteBuf = Unpooled.buffer();
                BufferedImage bufferedImage = ImageIO.read(favicon);
                ImageIO.write(bufferedImage, "PNG", new ByteBufOutputStream(byteBuf));

                ByteBuf encode = io.netty.handler.codec.base64.Base64.encode(byteBuf);
                serverPacketPing.favicon = "data:image/png;base64," + encode.toString(StandardCharsets.UTF_8).replaceAll("\n", "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
        public Builder favicon(File favicon) throws FileNotFoundException {
           // data:image/png;base64,<data>
            FileInputStream fileInputStream = new FileInputStream(favicon);
            return favicon(fileInputStream);
        }

        public Builder chat(Boolean enforcesSecureChat, Boolean previewsChat) {
            serverPacketPing.enforcesSecureChat = enforcesSecureChat;
            serverPacketPing.previewsChat = previewsChat;
            return this;
        }

        public ServerPacketPing build() {
            return serverPacketPing;
        }
    }


}
