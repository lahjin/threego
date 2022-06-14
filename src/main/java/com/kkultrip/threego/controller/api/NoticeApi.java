package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.dto.PageDto;
import com.kkultrip.threego.model.Notice;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.service.api.NoticeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoticeApi {

    private final NoticeApiService service;

    public NoticeApi(NoticeApiService service) {
        this.service = service;
    }

    @PostMapping("/notice/list")
    public ResponseEntity<?> notices(@RequestBody PageDto pageDto){
        Map<String, Object> result = new HashMap<>();

        Page page = service.paging(pageDto);

        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        List<Notice> lists = service.findAllPage(startOffset, page.getIndexSize());
        result.put("page", page);
        result.put("lists", lists);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/notice/list/{id}")
    public ResponseEntity<?> notice(@PathVariable("id") Long id){
        Optional<Notice> notice = service.findById(id);

        if(notice.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(notice.get());
    }
}
