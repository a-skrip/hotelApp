package ru.skriplex.springhotelapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skriplex.springhotelapplication.model.HotelModel;
import ru.skriplex.springhotelapplication.service.HotelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/hotels")
public class HotelRestController {

    private final HotelService service;

    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        List<HotelModel> list = service.getAll().stream()
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable("id") Long id) {
        HotelModel byId = service.getById(id);
        return ResponseEntity.ok().body(byId);
    }

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody HotelModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable("id") Long id, @RequestBody HotelModel model) {
        model.setId(id);
        return ResponseEntity.ok(service.update(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotelById(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
