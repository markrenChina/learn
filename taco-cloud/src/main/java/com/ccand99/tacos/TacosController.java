package com.ccand99.tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TacosController {
	@GetMapping("/")
	public String controllIndex() {
		return "index";
	}

}
