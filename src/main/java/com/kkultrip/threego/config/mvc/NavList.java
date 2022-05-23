package com.kkultrip.threego.config.mvc;

import com.kkultrip.threego.dto.NavListDto;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public final class NavList {

    public static void navDashboard(Model model){
        model.addAttribute("header", "대시보드");
    }

    public static void navPlace(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/place"));
        list.add(new NavListDto("글쓰기", "/place/write"));
        model.addAttribute("header", "장소관리");
        model.addAttribute("list",list);
    }

    public static void navTour(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/tour"));
        list.add(new NavListDto("글쓰기", "/tour/write"));
        list.add(new NavListDto("카테고리 목록", "/category"));
        list.add(new NavListDto("카테고리 추가", "/category/write"));
        model.addAttribute("header", "관광관리");
        model.addAttribute("list",list);
    }

    public static void navReview(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/review"));
        list.add(new NavListDto("댓글관리", "/comment"));
        model.addAttribute("header", "리뷰관리");
        model.addAttribute("list",list);
    }

    public static void navNotice(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/notice"));
        list.add(new NavListDto("글쓰기", "/notice/write"));
        model.addAttribute("header", "공지사항");
        model.addAttribute("list",list);
    }

    public static void navAsk(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/ask"));
        model.addAttribute("header", "문의사항");
        model.addAttribute("list",list);
    }

    public static void navUser(Model model){
        List<NavListDto> list = new ArrayList<>();
        list.add(new NavListDto("목록", "/user"));
        model.addAttribute("header", "회원관리");
        model.addAttribute("list",list);
    }

}
