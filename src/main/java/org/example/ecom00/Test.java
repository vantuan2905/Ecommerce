package org.example.ecom00;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RequestMapping("/")
public class Test {
    @GetMapping("/")
    public String test(){
        return "test";
    }

}
