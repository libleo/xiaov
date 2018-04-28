package org.b3log.xiaov.bots;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.xiaov.service.BaiduQueryService;
import org.b3log.xiaov.service.ItpkQueryService;
import org.b3log.xiaov.service.TuringQueryService;
import org.b3log.xiaov.util.XiaoVs;

public class QueryServiceBot extends AbstractBotModel {

    private Integer QQ_BOT_TYPE = 1;

    /**
     * Turing query service.
     */
    @Inject
    private TuringQueryService turingQueryService;
    /**
     * Baidu bot query service.
     */
    @Inject
    private BaiduQueryService baiduQueryService;
    /**
     * ITPK query service.
     */
    @Inject
    private ItpkQueryService itpkQueryService;

    @Override
    public boolean hitTest(String content) {
        if (StringUtils.contains(content, this.BotName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String answer(String content, String username) {
        String ret = "";
        String msg = replaceBotName(content);

        if (1 == QQ_BOT_TYPE && StringUtils.isNotBlank(username)) {
            ret = turingQueryService.chat(username, msg);
            ret = StringUtils.replace(ret, "图灵机器人", XiaoVs.QQ_BOT_NAME + "机器人");
            ret = StringUtils.replace(ret, "默认机器人", XiaoVs.QQ_BOT_NAME + "机器人");

            ret = StringUtils.replace(ret, "<br>", "\n");
        } else if (2 == QQ_BOT_TYPE) {
            ret = baiduQueryService.chat(msg);
        } else if (3 == QQ_BOT_TYPE) {
            ret = itpkQueryService.chat(msg);
            // 如果是茉莉机器人，则将灵签结果格式化输出
            JSONObject parseMsg;
            if (ret.indexOf("\\u8d22\\u795e\\u7237\\u7075\\u7b7e") > 0) {
                // 财神爷灵签
                parseMsg = JSONObject.parseObject(ret);

                ret = "";
                ret += "第" + parseMsg.getString("number2") + "签\r\n\r\n";
                ret += "签语: " + parseMsg.getString("qianyu") + "\r\n";
                ret += "注释: " + parseMsg.getString("zhushi") + "\r\n";
                ret += "解签: " + parseMsg.getString("jieqian") + "\r\n";
                ret += "解说: " + parseMsg.getString("jieshuo") + "\r\n\r\n";
                ret += "婚姻: " + parseMsg.getString("hunyin") + "\r\n";
                ret += "事业: " + parseMsg.getString("shiye") + "\r\n";
                ret += "运途: " + parseMsg.getString("yuntu");
                ret = ret.replace("null", "无");
            } else if (ret.indexOf("\\u6708\\u8001\\u7075\\u7b7e") > 0) {
                // 观音灵签
                parseMsg = JSONObject.parseObject(ret);

                ret = "";
                ret += "第" + parseMsg.getString("number2") + "签\r\n\r\n";
                ret += "签位: " + parseMsg.getString("haohua") + "\r\n";
                ret += "签语: " + parseMsg.getString("qianyu") + "\r\n";
                ret += "诗意: " + parseMsg.getString("shiyi") + "\r\n";
                ret += "解签: " + parseMsg.getString("jieqian");
                ret = ret.replace("null", "无");
            } else if (ret.indexOf("\\u89c2\\u97f3\\u7075\\u7b7e") > 0) {
                // 月老灵签
                parseMsg = JSONObject.parseObject(ret);

                ret = "";
                ret += "第" + parseMsg.getString("number2") + "签\r\n\r\n";
                ret += "签位: " + parseMsg.getString("haohua") + "\r\n";
                ret += "签语: " + parseMsg.getString("qianyu") + "\r\n";
                ret += "注释: " + parseMsg.getString("zhushi") + "\r\n";
                ret += "解签: " + parseMsg.getString("jieqian") + "\r\n";
                ret += "白话释义: " + parseMsg.getString("baihua");
                ret = ret.replace("null", "无");
            }
        }
        return ret;
    }

    private String replaceBotName(String msg) {
        if (StringUtils.isBlank(msg)) {
            return null;
        }

        if (msg.startsWith(XiaoVs.QQ_BOT_NAME + " ")) {
            msg = msg.replace(XiaoVs.QQ_BOT_NAME + " ", "");
        }
        if (msg.startsWith(XiaoVs.QQ_BOT_NAME + "，")) {
            msg = msg.replace(XiaoVs.QQ_BOT_NAME + "，", "");
        }
        if (msg.startsWith(XiaoVs.QQ_BOT_NAME + ",")) {
            msg = msg.replace(XiaoVs.QQ_BOT_NAME + ",", "");
        }
        if (msg.startsWith(XiaoVs.QQ_BOT_NAME)) {
            msg = msg.replace(XiaoVs.QQ_BOT_NAME, "");
        }
        return msg;
    }
}
