package be.alexandre01.limbo.connection.packet;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:57
*/
public class Packet {
    @Getter private final byte id;
    @Getter private int length = 0;

    @Getter private List<Object> datas = new Vector<>();

    public Packet(byte id) {
        this.id = id;
        length += String.valueOf(id).length();
    }

    private void writeString(String s){
        System.out.println(" string size: " +  (String.valueOf(s.length()).length()));
        String utf8 = new String(s.getBytes(), StandardCharsets.UTF_8);

        int utf16Length = utf8.codePointCount(0, utf8.length());
        datas.add(utf16Length);
        datas.add(utf8);
        length += utf8.length() + String.valueOf(utf16Length).length();
    }


    protected void write(Object o){
        if(o instanceof String){
            writeString((String)o);
        }else{
            datas.add(o);
            length += String.valueOf(o).length();
        }
    }

}
