package com.gcl.crm.service;

import com.gcl.crm.entity.Position;

import java.util.List;

public interface PositionService {
    List<Position> findAllPositions();
    Position findPositionById(Long id);
}
