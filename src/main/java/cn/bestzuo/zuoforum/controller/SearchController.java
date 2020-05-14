package cn.bestzuo.zuoforum.controller;

import cn.bestzuo.zuoforum.pojo.Question;
import cn.bestzuo.zuoforum.pojo.vo.UserIndexQuestionVO;
import cn.bestzuo.zuoforum.service.SearchService;
import cn.bestzuo.zuoforum.util.CommonUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单搜索Controller
 *
 * @author zuoxiang
 * @date 2019/11/20
 */
@SuppressWarnings("Duplicates")
@Controller
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 简单搜索Controller
     *
     * @param keywords
     * @return
     */
    @RequestMapping("/search")
    public String searchByKeywords(@RequestParam("keywords") String keywords, Model model) {
        List<Question> questions = searchService.searchByTitleAndContent(keywords);
        List<UserIndexQuestionVO> res = new ArrayList<>();

        if (questions.size() != 0) {
            for (Question q : questions) {
                res.add(convertQuestionToVO(q));
            }
            model.addAttribute("questions", res);
        } else {
            PageInfo<Question> pageInfo = new PageInfo<>(questions);
            model.addAttribute("questions", pageInfo.getList());
        }

        model.addAttribute("keywords", keywords);
        return "post/search";
    }

    /**
     * 将问题转换成前端VO
     *
     * @param question
     * @return
     */
    private UserIndexQuestionVO convertQuestionToVO(Question question) {
        UserIndexQuestionVO vo = new UserIndexQuestionVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setCommentCount(question.getCommentCount());
        vo.setLikeCount(question.getLikeCount());
        vo.setGmtCreate(question.getGmtCreate());
        vo.setViewCount(question.getViewCount());
        vo.setPublisher(question.getPublisher());

        String text = CommonUtils.Html2Text(question.getDescription());
        if (text.length() > 50) {
            vo.setDescription(text.substring(0, 50) + "......");
        } else
            vo.setDescription(text);
        return vo;
    }

}
