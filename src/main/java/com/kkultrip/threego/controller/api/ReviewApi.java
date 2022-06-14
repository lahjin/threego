package com.kkultrip.threego.controller.api;

import com.kkultrip.threego.config.security.jwt.JwtUtils;
import com.kkultrip.threego.dto.MessageDto;
import com.kkultrip.threego.dto.PageDto;
import com.kkultrip.threego.dto.ReviewDto;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Review;
import com.kkultrip.threego.model.Tour;
import com.kkultrip.threego.model.User;
import com.kkultrip.threego.repository.UserRepository;
import com.kkultrip.threego.service.api.ReviewApiService;
import com.kkultrip.threego.service.api.TourApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ReviewApi {

    private final ReviewApiService service;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepo;

    private final TourApiService tourService;

    public ReviewApi(ReviewApiService service, JwtUtils jwtUtils, UserRepository userRepo, TourApiService tourService) {
        this.service = service;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
        this.tourService = tourService;
    }

    @PostMapping("/review/save")
    public ResponseEntity<?> save(HttpServletRequest req, @RequestBody Review review){

        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        review.setUser_id(user_id);
        Long id = service.save(review);
        if(id != 0)
            return ResponseEntity.ok(id);
        return ResponseEntity.internalServerError().body(new MessageDto("저장에 실패했습니다."));
    }

    @PostMapping("/review/update")
    public ResponseEntity<?> update(HttpServletRequest req, @RequestBody Review review){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");

        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        // 로그인된 유저와 게시글 작성 유저가 같은지 체크
        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        boolean isUser = Objects.equals(review.getUser_id(), user_id);

        if(!isUser)
            return ResponseEntity.status(403).body(new MessageDto("권한이 없습니다."));
        // 유저가 같다면 업데이트
        int rs = service.update(review);

        // 업데이트에 성공했으면
        if(rs == 1)
            return ResponseEntity.ok(review.getId());
        // 실패했으면
        return ResponseEntity.internalServerError().body(new MessageDto("수정에 실패했습니다."));
    }

    @PostMapping("/review/delete")
    public ResponseEntity<?> delete(HttpServletRequest req, @RequestBody Review review){
        // header 에서 토큰 정보를 가져옴
        String token = req.getHeader("Authorization");
        System.out.println(token);
        // 토큰 유효성 체크
        boolean isToken = jwtUtils.validateToken(token);

        // 토큰이 유효성 체크를 통과하지 못했다면
        if (!isToken)
            return ResponseEntity.status(401).body(new MessageDto("잘못된 토큰입니다."));

        // 로그인된 유저와 게시글 작성 유저가 같은지 체크
        Long user_id = jwtUtils.getUserIdFromJwtToken(token);
        boolean isUser = Objects.equals(review.getUser_id(), user_id);

        if(!isUser)
            return ResponseEntity.status(403).body(new MessageDto("권한이 없습니다."));
        int rs = service.delete(review.getId());

        // 삭제에 성공했으면
        if(rs == 1)
            return ResponseEntity.ok(new MessageDto("삭제에 성공하였습니다."));
        // 실패했으면
        return ResponseEntity.internalServerError().body(new MessageDto("삭제에 실패했습니다."));
    }

    @PostMapping("/review/list")
    public ResponseEntity<?> reviews(@RequestBody PageDto pageDto){
        Map<String, Object> result = new HashMap<>();

        Page page = service.paging(pageDto);

        int startOffset = (page.getPageIndex() - 1) * page.getIndexSize();
        List<Review> lists = service.findAllPage(startOffset, page.getIndexSize());
        List<ReviewDto> ls = new ArrayList<>();
        for(int i=0; i< lists.size(); i++){
            Optional<User> user = userRepo.findById(lists.get(i).getUser_id());
            Optional<Tour> tour = tourService.findById(lists.get(i).getTour_id());
            ReviewDto dto = ReviewDto.builder()
                    .id(lists.get(i).getId())
                    .user_id(lists.get(i).getUser_id())
                    .nickname(user.get().getNickname())
                    .tour_id(lists.get(i).getTour_id())
                    .tour_name(tour.get().getName())
                    .title(lists.get(i).getTitle())
                    .content(lists.get(i).getContent())
                    .date(lists.get(i).getDate())
                    .count(lists.get(i).getCount())
                    .point(lists.get(i).getPoint())
                    .active(lists.get(i).isActive())
                    .good(lists.get(i).getGood())
                    .bad(lists.get(i).getBad())
                    .image_name1("")
                    .image_name2("")
                    .image_name3("")
                    .build();
            ls.add(dto);
        }
        result.put("page", page);
        result.put("lists", ls);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/review/list/{id}")
    public ResponseEntity<?> review(@PathVariable("id") Long id){
        Optional<Review> review = service.findById(id);

        if(review.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(review.get());
    }

    @GetMapping("/review/good/{id}")
    public ResponseEntity<?> good(@PathVariable("id") Long id){
        int rs = service.updateGood(id);
        if(rs == 1) {
            Optional<Review> review = service.findById(id);
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.internalServerError().body(new MessageDto("Update fail"));
    }

    @GetMapping("/review/bad/{id}")
    public ResponseEntity<?> bad(@PathVariable("id") Long id){
        int rs = service.updateBad(id);
        if(rs == 1) {
            Optional<Review> review = service.findById(id);
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.internalServerError().body(new MessageDto("Update fail"));
    }

    @GetMapping("/review/user/count/{user_id}")
    public ResponseEntity<?> countUserReview(@PathVariable("user_id") Long user_id){
        int count = service.countUserReview(user_id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("review/user/list/{user_id}")
    public ResponseEntity<?> userReviewList(@PathVariable("user_id") Long user_id){
        List<Review> reviews = service.findByUserId(user_id);
        return ResponseEntity.ok(reviews);
    }
}
