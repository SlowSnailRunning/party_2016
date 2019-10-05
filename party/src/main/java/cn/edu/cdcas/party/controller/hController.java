package cn.edu.cdcas.party.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("t")
public class hController {
    @GetMapping("t")
    public String t(){
        return "hello springboot!";
    }
    @GetMapping("t1")
    public String my(){

        return "hello springboot";
    }
}
