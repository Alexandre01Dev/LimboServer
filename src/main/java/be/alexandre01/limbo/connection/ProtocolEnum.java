package be.alexandre01.limbo.connection;

import java.util.Optional;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 18:03
*/
public enum ProtocolEnum {
    OLD(0),
    V1_7(5),
    V1_8(47),
    V1_9(107),
    V1_10(210),
    V1_11(315),
    V1_12(335),
    V1_13(393),
    V1_14(477),
    V1_15(573),
    V1_16(735),
    V1_17(755),
    V1_18(757),
    V1_19(759),
    V1_20(763);


    final int protocolVersion;
    ProtocolEnum(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public static Optional<ProtocolEnum> getProtocol(int protocolVersion){
        ProtocolEnum current = null;
        for (int i = 0; i < values().length; i++) {
            ProtocolEnum protocolEnum = values()[i];
            if(protocolVersion >= protocolEnum.protocolVersion){
                System.out.println("Checking protocol version: "+protocolEnum.name());
                if(current == null || current.protocolVersion < protocolEnum.protocolVersion){
                    System.out.println("Comparing protocol version: "+protocolEnum.name() + " with current: "+current);
                    current = protocolEnum;
                }
            }
        }
        return Optional.ofNullable(current);
    }
}
