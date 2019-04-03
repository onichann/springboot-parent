package com.wt.springboot.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SpringSessionController {

    private static Log log= LogFactory.getLog(SpringSessionController.class);

    @RequestMapping("/session")
    public @ResponseBody  String putSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info(session.getClass());
        log.info(session.getId());
        String name="cora";
        session.setAttribute("user",name);
        return "hey "+name;
    }
}
