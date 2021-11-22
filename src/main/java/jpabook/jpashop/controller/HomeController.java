package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    // Logger 선언을 lombok을 사용 할경우 @Slf4j로 대체 가능하다.
    // Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String home(){
        log.info("home Controller");
        return "home";
    }
}
