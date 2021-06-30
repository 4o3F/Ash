package moe.exusiaimoe;

import moe.exusiaimoe.listener.GroupListener;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

public class Ash {
    public static void main(String args[]) {
        Ash();
    }

    public static void Ash() {

        Data.LoadConfig();

        Data.bot = BotFactory.INSTANCE.newBot(Data.qqid,Data.qqpassword, new BotConfiguration() {
            {
                fileBasedDeviceInfo();
                setProtocol(MiraiProtocol.ANDROID_PAD);
                enableContactCache();
            }
        });
        Data.bot.getEventChannel().registerListenerHost(new GroupListener());
        Data.bot.login();
        Data.bot.join();
    }
}
