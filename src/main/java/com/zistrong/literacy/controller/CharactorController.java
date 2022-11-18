package com.zistrong.literacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zistrong.literacy.entity.Charactor;
import com.zistrong.literacy.service.CharactorService;
import com.zistrong.literacy.service.DicService;

@Controller
public class CharactorController {

    @Autowired
    private DicService dicService;

    @Autowired
    private CharactorService charactorService;

    @GetMapping("/view")
    public String toView(Model model) {
        model.addAttribute("name", "张");

        return "v";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:static/index.html";
    }

    @ResponseBody
    @GetMapping("get")
    public Charactor get() {
        return this.dicService.getCurrentCharactor();
    }

    @ResponseBody
    @GetMapping("favorite/{id}")
    public void favorite(@PathVariable("id") Long id) {
        charactorService.favorite(id);
    }

    @ResponseBody
    @GetMapping("switchs")
    public void switchs() {
        this.dicService.switchs();
    }
}
