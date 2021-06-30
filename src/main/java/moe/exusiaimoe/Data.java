package moe.exusiaimoe;

import net.mamoe.mirai.Bot;

import java.io.*;
import java.util.*;

public class Data {
    public static Bot bot;

    public static Long qqid = 3029675570L;
    public static String qqpassword = "botbot2020";

    public static Long verifygroupid = 450204188L;
    public static Long maingroupid = 1149249976L;

    public static Map<Long, String> verifyData = new HashMap<Long, String>();
    private static File verifyDataFile = new File("verifydata.list");
    //public static Map<Long, String> usernameData = new HashMap<>();

    public static String[] question = new String[]{
            "是否玩过相似的纯净生存服务器？",
            "在游戏中死亡后如果物品全部意外丢失，是否还有动力重新开始？",
            "愿意与服务器内其他玩家一起游玩或成为朋友吗？",
            "是否愿意与群内的玩家和谐讨论任何问题",
            "擅长生存中的哪些领域？（红石，建筑等）",
            "如果觉得本服很赞是否愿意推荐给朋友或在mc百科中写个评分？"
    };

    public static void LoadConfig() {
        loadConfig();
        loadVerify();
    }

    private static void loadConfig() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(new File("./config.properties")));

            class task extends TimerTask {
                public Properties properties;

                public task(Properties properties) {
                    this.properties = properties;
                }

                @Override
                public void run() {
                    try {
                        properties.store(new FileOutputStream(new File("./config.properties")), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Timer timer = new Timer();
            timer.schedule(new task(props), 0, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadVerify() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(verifyDataFile));
            oos.writeObject(verifyData);
            oos.close();

            class task extends TimerTask {
                public Properties properties;

                public task(Properties properties) {
                    this.properties = properties;
                }

                @Override
                public void run() {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(verifyDataFile));
                        Object readMap = ois.readObject();
                        if(readMap != null && readMap instanceof HashMap) {
                            verifyData.putAll((HashMap) readMap);
                        }
                        ois.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
