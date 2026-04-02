package ru.skriplex.springhotelapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skriplex.springhotelapplication.entity.Hotel;
import ru.skriplex.springhotelapplication.exception.HotelNotFoundException;
import ru.skriplex.springhotelapplication.mapper.HotelMapper;
import ru.skriplex.springhotelapplication.model.HotelModel;
import ru.skriplex.springhotelapplication.repository.HotelRepository;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HotelService implements CRUDServices<HotelModel> {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    @Override
    public List<HotelModel> getAll() {
        List<Hotel> all = repository.findAll();
        return all.stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public HotelModel getById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new HotelNotFoundException(String.format("Отель с id: %d не найден", id)));
    }

    @Override
    public HotelModel create(HotelModel model) {
        Hotel entity = mapper.toEntity(model);
        entity.setCreationDate(Instant.now());
        repository.save(entity);
        return mapper.toModel(entity);
    }

    @Override
    public HotelModel update(HotelModel model) {
        Long hotelId = model.getId();
        Hotel entity = repository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(String.format("Отель с id: %d не найден", hotelId)));
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setStars(model.getStars());
        entity.setUpdatedDate(Instant.now());

        return mapper.toModel(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new HotelNotFoundException(String.format("Отель с id: {} не найден" + id));
        }
        repository.deleteById(id);
    }
}