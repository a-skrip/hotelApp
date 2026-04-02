package ru.skriplex.springhotelapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skriplex.springhotelapplication.model.HotelModel;
import ru.skriplex.springhotelapplication.service.HotelService;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelWebController {

    private final HotelService service;

    @GetMapping
    public String start(Model model) {
        model.addAttribute("hotels", service.getAll());
        return "hotels";
    }

    @GetMapping("/{id}")
    public String getHotelById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hotel", service.getById(id));
        return "hotel";
    }

    @PostMapping
    public String createHotel(@ModelAttribute HotelModel model) {
        service.create(model);
        return "redirect:/api/v1/hotels";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<HotelModel> updateHotel(
            @PathVariable("id") Long id,
            @RequestBody HotelModel model) {
        model.setId(id);
        return ResponseEntity.ok(service.update(model));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteHotelById(@PathVariable("id") Long id) {
        service.delete(id);
        return "OK";
    }
}

