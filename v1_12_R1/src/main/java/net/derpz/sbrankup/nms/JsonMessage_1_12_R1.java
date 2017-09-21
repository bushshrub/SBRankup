package net.derpz.sbrankup.nms;


import net.derpz.sbrankup.nms.JsonMessage;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import org.bukkit.entity.Player;

/**
 * Created by xiurobert on 21-Sep-17.
 */
public class JsonMessage_1_12_R1 implements JsonMessage {

    @Override
    public void sendJsonMessage(Player p, String s){
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(ChatSerializer.a(s));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
