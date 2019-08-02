package eu.qcloud.redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginRedirect(HttpServletRequest request) {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/application/**", method = RequestMethod.GET)
    public String applicationRedirect(HttpServletRequest request) {

        return "forward:/index.html";
    }

}
