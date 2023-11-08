package com.average.bookReturn.logging;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class LoggingController {
    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    @RequestMapping("/")
    public String index() {
//        logger.trace("A TRACE Message");
//        logger.debug("A DEBUG Message");
//        logger.info("An INFO Message");
//        logger.warn("A WARN Message");
//        logger.error("An ERROR Message");


        return "Check out the Logs to see the output...";
    }
}
