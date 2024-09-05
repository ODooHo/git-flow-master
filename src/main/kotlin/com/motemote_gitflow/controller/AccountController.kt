package com.motemote_gitflow.controller


import com.motemote_gitflow.dto.AccountDto
import com.motemote_gitflow.dto.SignUpDto
import com.motemote_gitflow.viewmodel.AccountViewModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/api")
class AccountController(private val accountViewModel: AccountViewModel) {
    private val logger = LoggerFactory.getLogger(javaClass)

//    @RequestMapping("/success")
//    fun success(request: HttpServletRequest) : String {
//        return ""
//    }


    @GetMapping("/signUp")
    fun showSignUpForm(model: Model): String {
        model.addAttribute("signUpDto", SignUpDto("", "")) // 빈 DTO 객체 추가
        return "signup/signup" // login.html 뷰 반환
    }


    @PostMapping("/signUp")
    fun signUp(@ModelAttribute signUpDto: SignUpDto, model: Model): String{
        logger.info("email: ${signUpDto.email}")
        val result = accountViewModel.saveAccount(signUpDto)
        model.addAttribute("email",result.email)
        return "signup/success"
    }



    @PostMapping("/login")
    fun login(@RequestBody accountDto: AccountDto): String{
        logger.info("email: ${accountDto.email}")
        val email= accountDto.email ?: throw IllegalArgumentException("can't be null!")
        accountViewModel.loadUserByUsername(email)
        return "index"
    }
}