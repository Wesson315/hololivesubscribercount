package de.wesson.hololivesubscribercount.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {


    @GetMapping("/talents")
    public RedirectView redirectTalents(
            RedirectAttributes attributes) {
        return new RedirectView("/");
    }
    @RequestMapping("/error")
    public String handleError() {
        return "error.html";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
