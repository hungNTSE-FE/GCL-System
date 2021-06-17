package com.gcl.crm.service;

import com.gcl.crm.entity.Position;
import com.gcl.crm.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    PositionRepository positionRepository;

    @Override
    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Position findPositionById(Long id) {
        if (id == null){
            return null;
        }
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()){
            return position.get();
        }
        return null;
    }
}
