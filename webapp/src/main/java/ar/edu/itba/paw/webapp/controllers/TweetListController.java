package ar.edu.itba.paw.webapp.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.edu.itba.paw.services.TweetService;

@Controller
public abstract class TweetListController extends RaptorController {

    private static final String HAS_NEXT = "hasNext";
	private static final String NEXT = "next";
	private static final String PAGE = "page";
	private static final String PREVIOUS = "previous";
	private static final String TITLE_CODE = "titleCode";
	private static final String LINK = "link";
	
	@Autowired
    protected TweetService tweetService;

    protected Map<String, Object> buildPageInfo(Integer page, Integer resultsPerPage, Integer resultsInPage, String pageBase) {

        Map<String, Object> pageInfo = new HashMap<String, Object>();

        Boolean hasPrevious;
        Boolean hasNext;

        if (page > 1) {
            hasPrevious = Boolean.TRUE;
            Map<String, Object> previous = new HashMap<String, Object>();
            if (page == 1) {

            }
            previous.put(LINK, TweetListController.addParameter(pageBase, PAGE, Integer.toString(page-1)));
            previous.put(TITLE_CODE, "tweetList.previousTitle");
            pageInfo.put(PREVIOUS, previous);
        } else {
            hasPrevious = Boolean.FALSE;
        }

        pageInfo.put("hasPrevious", hasPrevious);

        if (resultsInPage == resultsPerPage) {
            hasNext = Boolean.TRUE;
            Map<String, Object> next = new HashMap<String, Object>();
            next.put(LINK, TweetListController.addParameter(pageBase, PAGE, Integer.toString(page+1)));
            next.put(TITLE_CODE, "tweetList.nextTitle");
            pageInfo.put(NEXT, next);
        } else {
            hasNext = Boolean.FALSE;
        }

        pageInfo.put(HAS_NEXT, hasNext);

        return pageInfo;
    }

    protected static String addParameter(String URL, String name, String value)
    {
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + name + '=' + value;
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }


}
