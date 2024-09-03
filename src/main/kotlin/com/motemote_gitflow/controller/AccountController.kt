package com.motemote_gitflow.controller


import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
@RequestMapping("/view")
class AccountController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @RequestMapping("/success")
    fun success(request: HttpServletRequest) : String {
        logger.info("success")
        return ""
    }
}