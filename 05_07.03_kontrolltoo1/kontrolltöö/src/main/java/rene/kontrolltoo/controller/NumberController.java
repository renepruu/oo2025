package rene.kontrolltoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rene.kontrolltoo.entity.NumberEntity;
import rene.kontrolltoo.repository.NumberRepository;

import java.util.List;

//Teisendamine

@RestController
@RequestMapping("/numbers")
public class NumberController {
    @Autowired
    private NumberRepository numberRepository;
    @PostMapping
    public ResponseEntity<String> addNumber(@RequestBody NumberEntity numberEntity) {
        //2 kontrolli + veateade
        if (numberEntity.getNumber() < 0) {
            return ResponseEntity.badRequest().body("Number must be positive");
        }
        if (numberEntity.getNumber() > 1000) {
            return ResponseEntity.badRequest().body("Number must be less than 1000");
        }

        NumberEntity entity = new NumberEntity();
        entity.setNumber(numberEntity.getNumber());
        numberRepository.save(entity);
        return ResponseEntity.ok("Number added successfully");
    }
    @GetMapping
    public ResponseEntity<List<NumberEntity>> getAllNumbers() {
        return ResponseEntity.ok(numberRepository.findAll());
    }
}
