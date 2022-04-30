package com.example.redistest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
  @RequestMapping("")
  @ResponseBody
  public String welcome(){
    return "hello";
  }





}
