package com.beyond.basic.b1_hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Component 어노테이션을 통해 별도의 객체을 생성할 필요가 없는, 싱글톤 객체 생성
// Controller 어노테이션을 통해 쉽게 사용자의 http req를 분석하고, http res를 생성
@Controller
// 클래스 차원에 url매핑시에는 RequestMapping을 사용
@RequestMapping("/hello")
public class HelloController {

//    get 요청의 case들
//    case1. 서버가 사용자에게 단순 String 데이터 return - @ResponseBody 있을 때
    @GetMapping("") // 아래 메서드에 대한 서버의 엔드포인트를 설정
//    ResponseBody가 없고, return 타입이 String인 경우 서버는 templates폴더 밑에 helloworld.html을 찾아서 리턴
//    @ResponseBody
    public String helloWorld() {
        return "helloworld";
    }

//    case2. 서버가 사용자에게 String(json형식)의 데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
        Hello h1 = new Hello("hong", "hong@naver.com");
//        직접 json으로 직렬화 할 필요 없이, return타입에 객체가 있으면 자동으로 직렬화
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        return objectMapper.writeValueAsString(h1);
        return h1;
    }

//    case3. parameter방식을 통해 사용자로부터 값을 수신
//    parameter의 형식 : /member?name=honggildong
    @GetMapping("/param")
    @ResponseBody
    public Hello param(@RequestParam(value = "name")String inputName) {
        System.out.println(inputName);
//        {name:사용자가 넣어온이름, email:아무거나}
        Hello h1 = new Hello(inputName, inputName+"@naver.com");
        return h1;
    }

//    case4. pathvariable방식을 통해 사용자로부터 값을 수신
//    pathvariable의 형식 : /member/1
//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현할 때 사용(좀더 restful함)
    @GetMapping("/path/{inputId}")
    @ResponseBody
    public String path(@PathVariable Long inputId) {
//        long id = Long.parseLong(inputId);
        System.out.println(inputId);
        return "OK";
    }

//    case5. paramenter 2개 이상 형식
//    /hello/param2?name=hongildong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name")String inputName,
                         @RequestParam(value = "email")String inputEmail) {
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "OK";
    }
}
