package org.b3log.xiaov.bots;

public abstract class AbstractBotModel {

    protected String BotName;
    protected Integer priority;

    AbstractBotModel() {

    }

    AbstractBotModel(String BotName) {
        this.BotName = BotName;
    }

    // 传入聊天内容看是否匹配到机器人规则
    public boolean hitTest(String content) {
        return false;
    }

    // 优先级
    public Integer priority() {
        return this.priority;
    }

    public void  setPriority(Integer priority) {
        this.priority = priority;
    }

    // 回答内容
    public String answer(String content, String username) {
        return null;
    }
}
