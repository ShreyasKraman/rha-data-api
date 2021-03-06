package com.rha.dataapi.services;

import com.google.common.base.Preconditions;
import com.rha.dataapi.hibernate.Robin;
import com.rha.dataapi.repositories.RobinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RobinService implements ICrudService<Robin, Integer> {

    @Autowired
    private RobinRepository robinRepository;

    @Override
    public List<Robin> getAll() {
        return robinRepository.findAll();
    }

    @Override
    public Robin get(Integer robinId) {
        Optional<Robin> optionalRobin = robinRepository.findById(robinId);
        if (optionalRobin.isPresent()) {
            return optionalRobin.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Robin create(Robin robinToBeCreated) {
        Preconditions.checkNotNull(robinToBeCreated.getName());
        return robinRepository.save(robinToBeCreated);
    }

    @Override
    public Robin update(Integer robinId, Robin robinToBeUpdated) {
        Preconditions.checkNotNull(robinId, "Cannot update a null robinId");
        Optional<Robin> optionalRobin = robinRepository.findById(robinId);
        if (optionalRobin.isPresent()) {
            Robin existingRobin = optionalRobin.get();
            existingRobin.copyAttributes(robinToBeUpdated);
            return robinRepository.save(existingRobin);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public void delete(Integer robinId) {
        Preconditions.checkNotNull(robinId, "Cannot update a null robinId");
        Optional<Robin> optionalRobin = robinRepository.findById(robinId);
        if (optionalRobin.isPresent()) {
            Robin existingRobin = optionalRobin.get();
            existingRobin.setActive(false);
            robinRepository.save(existingRobin);
            return;
        }
        throw new EntityNotFoundException();
    }
}
