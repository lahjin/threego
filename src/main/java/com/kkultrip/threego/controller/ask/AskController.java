package com.kkultrip.threego.controller.ask;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Ask;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.service.ask.AskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AskController {

    private final AskService askService;

    public AskController(AskService askService) {
        this.askService = askService;
    }

    @GetMapping("/ask")
    public String ask(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ){
        Page page = askService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Ask> ask = askService.pageList(page);

        model.addAttribute("ask", ask);
        model.addAttribute("page", page);

        NavList.navAsk(model);

        return "ask/ask";
    }

    @GetMapping("/ask/info/{id}")
    public String askPage(@PathVariable Long id, Model model) {
        Optional<Ask> ask = askService.findId(id);
        model.addAttribute("ask", ask.get());

        NavList.navAsk(model);
        return "ask/info";
    }

    @GetMapping("/ask/answer/{id}")
    public String askAnswerId(@PathVariable Long id, Model model) {
        Optional<Ask> ask = askService.findId(id);

        ask.get().setContent(ask.get().getContent().replace("<br/>", "\r\n"));
        ask.get().setAnswer(ask.get().getAnswer().replace("<br/>", "\r\n"));

        model.addAttribute("ask", ask.get());

        NavList.navAsk(model);
        return "ask/answer";
    }

    @PostMapping("/ask/answer")
    public String askAnswer(Ask _ask, Model model) {
        _ask.setAnswer(_ask.getAnswer().replace("\r\n", "<br/>"));

        int rs = askService.updateInfo(_ask);

        Optional<Ask> ask = askService.findId(_ask.getId());
        model.addAttribute("ask", ask.get());

        NavList.navAsk(model);
        return "ask/info";
    }
}
