package com.tallgeorge.pi.project1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppHtmlController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final Model model) {
        return "index";
    }
}
