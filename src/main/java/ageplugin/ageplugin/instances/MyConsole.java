package ageplugin.ageplugin.instances;

import ageplugin.ageplugin.AgePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;

public class MyConsole implements RemoteConsoleCommandSender {

    private PermissibleBase permissibleBase;
    private static String lastOutput;

    public MyConsole(AgePlugin agePlugin){
        this.permissibleBase=new PermissibleBase(this);
        this.lastOutput="";
    }
    public static String getLastOutput(){
        return lastOutput;
    }



    @Override
    public void sendMessage(String message) {
        System.out.println("[Console]: "+message);
        lastOutput=message;
    }

    @Override
    public void sendMessage(String... messages) {
        StringBuilder stringBuilder=new StringBuilder();
        for(String message:messages){
            stringBuilder.append(message);
        }
        lastOutput=stringBuilder.toString();
        System.out.println("[Console]: "+lastOutput);
    }

    @Override
    public void sendMessage(UUID sender, String message) {
        sendMessage(message);
    }

    @Override
    public void sendMessage(UUID sender, String... messages) {
        sendMessage(messages);
    }

    @Override
    public Server getServer() {
        System.out.println(lastOutput);
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        System.out.println(lastOutput);
        return "MyConsole";
    }

    @Override
    public Spigot spigot() {
        System.out.println(lastOutput);
        return null;
    }

    @Override
    public boolean isPermissionSet(String name) {
        System.out.println(lastOutput);
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        System.out.println(lastOutput);
        return true;
    }

    @Override
    public boolean hasPermission(String name) {
        System.out.println(lastOutput);
        return true;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        System.out.println(lastOutput);
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        System.out.println(lastOutput);
        return permissibleBase.addAttachment(plugin,name,value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        System.out.println(lastOutput);
        return permissibleBase.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        System.out.println(lastOutput);
        return permissibleBase.addAttachment(plugin,name,value,ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        System.out.println(lastOutput);
        return permissibleBase.addAttachment(plugin,ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        System.out.println(lastOutput);
        permissibleBase.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        System.out.println(lastOutput);
        permissibleBase.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        System.out.println(lastOutput);
        return permissibleBase.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        System.out.println(lastOutput);
        return true;
    }

    @Override
    public void setOp(boolean value) {
        System.out.println(lastOutput);
        System.out.println("not op");
        return;
    }
}
