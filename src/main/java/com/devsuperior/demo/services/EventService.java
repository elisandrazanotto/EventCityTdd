package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = false)
    public EventDTO update(Long id, EventDTO eventDTO) {
        try {
            Event eventEntity = repository.getReferenceById(id);
            eventEntity.setName(eventDTO.getName());
            eventEntity.setDate(eventDTO.getDate());
            eventEntity.setUrl(eventDTO.getUrl());
            City cityEntity = cityRepository.getReferenceById(eventDTO.getCityId());
            eventEntity.setCity(cityEntity);
            return new EventDTO(repository.save(eventEntity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
}
