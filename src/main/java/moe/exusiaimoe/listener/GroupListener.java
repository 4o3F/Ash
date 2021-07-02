package moe.exusiaimoe.listener;

import moe.exusiaimoe.Data;
import moe.exusiaimoe.network.Mojang;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;

public class GroupListener extends SimpleListenerHost {
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event) {
        Long memberid = event.getSender().getId();
        String message = event.getMessage().contentToString();
        String verifystatus = Data.verifyData.get(memberid);
        if (verifystatus != null) {
            if (verifystatus.equals("2")) {
                if (message.equals("是")) {
                    Data.verifyData.put(memberid, "3");
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(memberid))
                            .append(new PlainText("邀请人为？(请回答QQ号)"))
                            .build();
                    event.getGroup().sendMessage(messageChain);
                } else if (message.equals("否")) {
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(memberid))
                            .append(new PlainText("了解本服的渠道为？"))
                            .build();
                    event.getGroup().sendMessage(messageChain);
                    Data.verifyData.put(memberid, "3");
                } else {
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(memberid))
                            .append(new PlainText("回答错误，请用“是”或“否”来回答"))
                            .build();
                    event.getGroup().sendMessage(messageChain);
                }
            } else if (verifystatus.equals("3")) {
                Random ran = new Random();
                HashSet hs = new HashSet();
                for (; ; ) {
                    int tmp = ran.nextInt(6);
                    hs.add(tmp);
                    if (hs.size() == 3) break;
                }
                StringBuilder randomedquestionbuilder = new StringBuilder("3");
                Iterator iterator = hs.iterator();
                while (iterator.hasNext()) {
                    randomedquestionbuilder.append("-").append(iterator.next());
                }
                randomedquestionbuilder.append("-2");
                //最终格式应该是3-1-2-3-2
                //第一位是总问题为3
                //第2到4为随机的题号
                //最后一位为下一个应该提问的题
                String randomedquestion = randomedquestionbuilder.toString();
                MessageChain messageChain = new MessageChainBuilder()
                        .append(new At(memberid))
                        .append(new PlainText(Data.question[Integer.parseInt(randomedquestion.split("-")[1])]))
                        .build();
                event.getGroup().sendMessage(messageChain);
                Data.verifyData.put(memberid, randomedquestion);
            } else if (!verifystatus.equals("3") && verifystatus.contains("3") && verifystatus.endsWith("2")) {
                String[] randomquestion = verifystatus.split("-");
                MessageChain messageChain = new MessageChainBuilder()
                        .append(new At(memberid))
                        .append(new PlainText(Data.question[Integer.parseInt(randomquestion[2])]))
                        .build();
                event.getGroup().sendMessage(messageChain);
                Data.verifyData.put(memberid, verifystatus.substring(0, verifystatus.length() - 1) + "3");
            } else if (!verifystatus.equals("3") && verifystatus.contains("3") && verifystatus.endsWith("3")) {
                String[] randomquestion = verifystatus.split("-");
                MessageChain messageChain = new MessageChainBuilder()
                        .append(new At(memberid))
                        .append(new PlainText(Data.question[Integer.parseInt(randomquestion[3])]))
                        .build();
                event.getGroup().sendMessage(messageChain);
                Data.verifyData.put(memberid, "4");
            } else if (verifystatus.equals("4")) {
                MessageChain messageChain = new MessageChainBuilder()
                        .append(new At(memberid))
                        .append(new PlainText("请阅读服务器规则: \n https://docs.qq.com/doc/DT2dUcUxncGJGa0dS \n \n"))
                        .append(new PlainText("审核通过，请加入主群https://jq.qq.com/?_wv=1027&k=WcDlyhFA"))
                        .build();
                event.getGroup().sendMessage(messageChain);
                Data.verifyData.put(memberid, "done");
            }
        }

        //判断疑问语气
        //TODO add more words
        if (message.contains("?") || message.contains("？") || message.contains("什么")
                || message.contains("啥") || message.contains("吗") || message.contains("么") ||
                message.contains("呢")) {
            if (message.contains("ip") || message.contains("IP") || message.contains("地址")) {
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("game.spawnmc.net:3300"))
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            } else if (message.contains("规则") || message.contains("规定")) {
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("规则:\n"))
                        .append(new PlainText("https://docs.qq.com/doc/DT2dUcUxncGJGa0dS"))
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            } else if (message.contains("小账本") || message.contains("财务") || message.contains("流水")) {
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("服务器小账本:\n"))
                        .append(new PlainText("https://docs.qq.com/sheet/DVnlxbXpFam1CblVX"))
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            } else if (message.contains("百科") || message.contains("帖子") || message.contains("mcmod")) {
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("mc百科:\n"))
                        .append(new PlainText("https://play.mcmod.cn/sv20184949.html"))
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            }
        } else {
            if (message.contains("QQ支付")) {
                Image image = event.getGroup().uploadImage(ExternalResource.create(new File("./img/qq.jpg")));
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("QQ支付:\n"))
                        .append(image)
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            } else if (message.contains("微信支付")) {
                Image image = event.getGroup().uploadImage(ExternalResource.create(new File("./img/wechat.jpg")));
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("微信支付:\n"))
                        .append(image)
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            } else if (message.contains("支付宝")) {
                Image image = event.getGroup().uploadImage(ExternalResource.create(new File("./img/alipay.jpg")));
                MessageChain returnmessageChain = new MessageChainBuilder()
                        .append(new PlainText("支付宝:\n"))
                        .append(image)
                        .build();
                event.getGroup().sendMessage(returnmessageChain);
            }
        }
    }


    @EventHandler
    public void onMemberJoinRequestEvent(MemberJoinRequestEvent event) {
        Long groupid = event.getGroupId();
        Long requesterid = event.getFromId();
        String answer = event.getMessage().split("：")[2];
        if (groupid.equals(Data.verifygroupid)) {
            Boolean usernameprime = Mojang.MojangUserNameExist(answer);
            if (usernameprime) {
                Data.primeusername.put(requesterid, answer);
                event.accept();
            } else {
                event.reject(false, "非正版ID");
            }
        } else if (groupid.equals(Data.maingroupid)) {
            if (Data.verifyData.get(requesterid).equals("done")) {
                event.accept();
                event.getBot().getGroup(Data.verifygroupid).get(requesterid).kick("成功加入主群");
            } else {
                event.reject(false, "请先加入审核群完成审核");
            }
        }
    }

    @EventHandler
    public void onMemberJoinEvent(MemberJoinEvent event) {
        Long memberid = event.getMember().getId();
        Long groupid = event.getGroup().getId();
        String primeusername = Data.primeusername.get(memberid);
        if (groupid.equals(Data.verifygroupid)) {

            event.getMember().setNameCard(primeusername);
            MessageChain messageChain = new MessageChainBuilder()
                    .append(new At(memberid))
                    .append(new PlainText("欢迎! " + primeusername + "\n 即将开始审核环节"))
                    .build();
            event.getGroup().sendMessage(messageChain);
            Data.verifyData.put(memberid, "2");
            messageChain = new MessageChainBuilder()
                    .append(new At(memberid))
                    .append(new PlainText("是否为朋友邀请"))
                    .build();
            event.getGroup().sendMessage(messageChain);

        } else {
            event.getMember().setNameCard(primeusername);
            MessageChain messageChain = new MessageChainBuilder()
                    .append(new At(memberid))
                    .append(new PlainText("欢迎! \n 请将自己的群名片更改为正版ID \n 服务器地址为 \n game.spawnmc.net:3300"))
                    .build();
            event.getGroup().sendMessage(messageChain);
        }
    }
}
