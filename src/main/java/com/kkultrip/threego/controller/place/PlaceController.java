package com.kkultrip.threego.controller.place;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.Place;
import com.kkultrip.threego.service.place.PlaceService;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/place")
    public String place(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ) {
        Page page = placeService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<Place> place = placeService.pageList(page);

        model.addAttribute("place", place);
        model.addAttribute("page", page);

        NavList.navPlace(model);
        return "place/place";
    }

    @GetMapping("/place/info/{id}")
    public String placePage(@PathVariable Long id, Model model) {
        Optional<Place> place = placeService.findId(id);
        model.addAttribute("place", place.get());

        NavList.navPlace(model);
        return "place/info";
    }

    @GetMapping("/place/edit/{id}")
    public String placeEditId(@PathVariable Long id, Model model) {
        Optional<Place> place = placeService.findId(id);
        if(place.get().getDescription() != null)
         place.get().setDescription(place.get().getDescription().replace("<br/>", "\r\n"));
        if(place.get().getGuide() != null)
         place.get().setGuide(place.get().getGuide().replace("<br/>", "\r\n"));
        model.addAttribute("place", place.get());

        NavList.navPlace(model);
        return "place/edit";
    }

    @PostMapping("/place/edit")
    public String placeEdit(Place _place, Model model) {
        _place.setDescription(_place.getDescription().replace("\r\n", "<br/>"));
        _place.setGuide(_place.getGuide().replace("\r\n", "<br/>"));
        int rs = placeService.updateInfo(_place);

        Optional<Place> place = placeService.findId(_place.getId());
        model.addAttribute("place", place.get());

        NavList.navPlace(model);
        return "place/info";
    }

    @GetMapping("/place/active/{id}")
    public String placeActive(@PathVariable Long id, Model model) {
        NavList.navPlace(model);
        Optional<Place> place = placeService.findId(id);

        int rs = placeService.activeUpdate(id, !place.get().isActive());
        if (rs == 1) {
            place.get().setActive(!place.get().isActive());
            model.addAttribute("place", place.get());
            return "place/info";
        }
        else{
            return null;
        }
    }

    @GetMapping("/place/write")
    public String placeWrite(Model model) {

        NavList.navPlace(model);
        model.addAttribute("place", new Place());
        model.addAttribute("mode", "write");
        return  "place/edit";
    }

    @PostMapping("/place/add")
    public String placeAdd(@Valid Place _place, Model model) {
        NavList.navPlace(model);
        _place.setDescription(_place.getDescription().replace("\r\n", "<br/>"));
        _place.setGuide(_place.getGuide().replace("\r\n", "<br/>"));

        Long rs = placeService.save(_place);

        Optional<Place> place = placeService.findId(_place.getId());
        model.addAttribute("place", place.get());


        return "place/info";
    }

}
