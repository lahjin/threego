package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.config.security.jwt.JwtUtils;
import com.kkultrip.threego.dto.MessageDto;
import com.kkultrip.threego.model.Ask;
import com.kkultrip.threego.service.api.AskApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AskApi {

    private final AskApiService service;

    private final JwtUtils jwtUtils;

    public AskApi(AskApiService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/ask/save")
    public ResponseEntity<?> save(HttpServletRequest req, @RequestBody Ask ask){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        ask.setUser_id(user_id);
        Long id = service.save(ask);
        if(id != 0)
            return ResponseEntity.ok(id);
        return ResponseEntity.internalServerError().body(new MessageDto("저장에 실패했습니다."));
    }

    @PostMapping("/ask/update")
    public ResponseEntity<?> update(HttpServletRequest req, @RequestBody Ask ask){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        // 로그인된 유저와 게시글 작성 유저가 같은지 체크
        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        boolean isUser = Objects.equals(ask.getUser_id(), user_id);

        if(!isUser)
            return ResponseEntity.status(403).body(new MessageDto("권한이 없습니다."));
        // 유저가 같다면 업데이트
        int rs = service.update(ask);

        // 업데이트에 성공했으면
        if(rs == 1)
            return ResponseEntity.ok(ask.getId());
        // 실패했으면
        return ResponseEntity.internalServerError().body(new MessageDto("수정에 실패했습니다."));
    }

    @PostMapping("/ask/delete")
    public ResponseEntity<?> delete(HttpServletRequest req, @RequestBody Ask ask){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        // 로그인된 유저와 게시글 작성 유저가 같은지 체크
        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        boolean isUser = Objects.equals(ask.getUser_id(), user_id);

        if(!isUser)
            return ResponseEntity.status(403).body(new MessageDto("권한이 없습니다."));
        int rs = service.delete(ask.getId());

        // 삭제에 성공했으면
        if(rs == 1)
            return ResponseEntity.ok(new MessageDto("삭제에 성공하였습니다."));
        // 실패했으면
        return ResponseEntity.internalServerError().body(new MessageDto("삭제에 실패했습니다."));
    }

    @GetMapping("/ask/list")
    public ResponseEntity<?> asks(HttpServletRequest req){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        List<Ask> askList = service.findByUserId(user_id);
        return ResponseEntity.ok(askList);
    }

    @GetMapping("/ask/list/{id}")
    public ResponseEntity<?> ask(@PathVariable("id") Long id, HttpServletRequest req){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        // 검색한 id 값에 존재하는지 유무 체크
        Optional<Ask> ask = service.findById(id);
        // 없으면 not found
        if (ask.isEmpty())
            return ResponseEntity.notFound().build();

        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        boolean isUser = Objects.equals(ask.get().getUser_id(), user_id);

        if(isUser)
            return ResponseEntity.ok(ask.get());
        return ResponseEntity.status(403).body(new MessageDto("권한이 없습니다."));
    }

    @GetMapping("/ask/user/count/{user_id}")
    public ResponseEntity<?> countUserAsk(@PathVariable("user_id") Long user_id){
        int count = service.countUserAsk(user_id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("ask/user/list/{user_id}")
    public ResponseEntity<?> userAskList(@PathVariable("user_id") Long user_id){
        List<Ask> asks = service.findByUserId(user_id);
        return ResponseEntity.ok(asks);
    }
}
