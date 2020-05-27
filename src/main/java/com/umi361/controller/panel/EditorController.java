package com.umi361.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditorController {

    @RequestMapping("/editor.jsp")
    public String editorController() {
        return "editor";
    }

}
