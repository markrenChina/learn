package com.ccand99.tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.ccand99.tacos.model.Order;
import com.ccand99.tacos.model.Taco;
import com.ccand99.tacos.Ingredient;
import com.ccand99.tacos.Ingredient.Type;
import com.ccand99.tacos.h2db.IngredientRepository;
import com.ccand99.tacos.h2db.TacoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController{

    private TacoRepository designRepo;

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,TacoRepository designRepo){
        this.ingredientRepo = ingredientRepository;
        this.designRepo = designRepo;
    }

    // tag::modelAttributes[]
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,type));
            //根据type 创建不同的数组传入thymeleaf
        }

        //model.addAttribute("design", new Taco());
        
        //对应thymeleaf中th:object="${desgin}"
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    //public String processDesign(@Valid @ModelAttribute("design") Taco design,Errors errors,@ModelAttribute Order order){
    public String processDesign(@Valid Taco design,Errors errors,@ModelAttribute Order order){
        //save the taoc design...
        //We'll do this in chapter 3
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        log.info("Processing design: "+ design);
        return "redirect:/orders/current";
    }
}