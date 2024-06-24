package be.alexandre01.limbo.connection.packet;

import lombok.Getter;

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
       // length += String.valueOf(id).length();
    }

    private void writeString(String s){
        System.out.println(" string size: " +  (String.valueOf(s.length()).length()));
        datas.add(s.length());
        datas.add(s);
        length += s.length() + (String.valueOf(s.length()).length());
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
