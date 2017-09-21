package net.derpz.sbrankup.nms;

import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by xiurobert on 21-Sep-17.
 */
public class JsonMessage_1_9_R1 implements JsonMessage {
    public void sendJsonMessage(Player p, String s){
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(ChatSerializer.a(s));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
