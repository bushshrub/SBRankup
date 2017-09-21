package net.derpz.sbrankup.nms;

import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by xiurobert on 21-Sep-17.
 */
public class JsonMessage_1_9_R2 implements JsonMessage{
    public void sendJsonMessage(Player p, String s){
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(ChatSerializer.a(s));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
