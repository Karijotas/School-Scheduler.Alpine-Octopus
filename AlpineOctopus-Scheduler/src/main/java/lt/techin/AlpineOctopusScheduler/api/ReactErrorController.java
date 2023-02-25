package lt.techin.AlpineOctopusScheduler.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ReactErrorController implements ErrorController {


    public String index() {

        return "index.html";

    }
}

