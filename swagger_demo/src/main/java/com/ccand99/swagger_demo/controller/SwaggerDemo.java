package com.ccand99.swagger_demo.controller;

import com.ccand99.swagger_demo.pojo.ResultV0;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SwaggerDemo {

    @GetMapping(value = "hello",produces = "application/json;charset=UTF-8")
    public ResultV0<String> hello(){
        return ResultV0.OK("Hello World!");
    }

    @PostMapping("Post")
    public ResultV0<String> postDemo(@RequestBody ResultV0<String> resultV0){
        return resultV0;
    }

    @GetMapping("{address}")
    public ResultV0<String> path(@PathVariable String address){
        return ResultV0.OK(address);
    }

    @GetMapping("query")
    public ResultV0<String> query(@RequestParam String name){
        return ResultV0.OK(name);
    }
}