package org.b3log.xiaov.bots;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.xiaov.service.BaiduQueryService;
import org.b3log.xiaov.service.ItpkQueryService;
import org.b3log.xiaov.service.TuringQueryService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FF14ItemBot extends AbstractBotModel {

    private static final Logger LOGGER = Logger.getLogger(FF14ItemBot.class);

    static String PREFIX_KEYWORD = "道具检索:";

    @Override
    public boolean hitTest(String content) {
        if (StringUtils.startsWith( content, PREFIX_KEYWORD )) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String answer(String content, String username) {
        final String itemNameParts = StringUtils.removeStart(content, PREFIX_KEYWORD);
        String searchQuery = null;
        try {
            searchQuery = URLEncoder.encode(itemNameParts, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.ERROR, "Search item encoding failed", e);
        }
        String msg = "简陋版道具检索嘟嘟\n http://ff14.huijiwiki.com/wiki/ItemSearch?name=".concat(searchQuery);
        return msg;
    }
}
