package com.kkultrip.threego.controller.notice;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Notice;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.service.notice.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String notice(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ) {
        Page page = noticeService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Notice> notice = noticeService.pageList(page);

        model.addAttribute("notice", notice);
        model.addAttribute("page", page);

        NavList.navNotice(model);
        return "notice/notice";
    }

    @GetMapping("/notice/info/{id}")
    public String noticePage(@PathVariable Long id, Model model) {
        Optional<Notice> notice = noticeService.findId(id);
        model.addAttribute("notice", notice.get());

        NavList.navNotice(model);
        return "notice/info";
    }

    @GetMapping("/notice/edit/{id}")
    public String noticeEditId(@PathVariable Long id, Model model) {
        Optional<Notice> notice = noticeService.findId(id);

        notice.get().setContent(notice.get().getContent().replace("<br/>", "\r\n"));
        model.addAttribute("notice", notice.get());

        NavList.navNotice(model);
        return "notice/edit";
    }

    @PostMapping("/notice/edit")
    public String noticeEdit(Notice _notice, Model model) {
        _notice.setContent(_notice.getContent().replace("\r\n", "<br/>"));

        int rs = noticeService.updateInfo(_notice);

        Optional<Notice> notice = noticeService.findId(_notice.getId());
        model.addAttribute("notice", notice.get());

        NavList.navNotice(model);
        return "notice/info";
    }

    @GetMapping("/notice/active/{id}")
    public String noticeActive(@PathVariable Long id, Model model) {
        NavList.navNotice(model);
        Optional<Notice> notice = noticeService.findId(id);

        int rs = noticeService.activeUpdate(id, !notice.get().isActive());
        if(rs == 1){
            notice.get().setActive(!notice.get().isActive());
            model.addAttribute("notice", notice.get());
            return "notice/info";
        }
        else
            return "notice/error";
    }

    @GetMapping("/notice/write")
    public String noticeWrite(Model model) {

        NavList.navNotice(model);
        model.addAttribute("notice", new Notice());
        model.addAttribute("mode", "write");
        return  "notice/edit";
    }

    @PostMapping("/notice/add")
    public String noticeAdd(@Valid Notice _notice, Model model) {
        _notice.setContent(_notice.getContent().replace("\r\n", "<br/>"));

        Long rs = noticeService.save(_notice);

        Optional<Notice> notice = noticeService.findId(_notice.getId());
        model.addAttribute("notice", notice.get());

        NavList.navNotice(model);
        return "notice/info";
    }
}
