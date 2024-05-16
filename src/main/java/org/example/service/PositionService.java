package org.example.service;

import org.example.entity.PositionEntity;
import org.example.exp.AppBadException;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;


    public PositionEntity get(Integer id) {
        return positionRepository.getPositionById(id).orElseThrow(() -> {
            throw new AppBadException("Position not found");
        });
    }

}
