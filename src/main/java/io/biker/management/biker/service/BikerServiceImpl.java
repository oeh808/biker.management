package io.biker.management.biker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.exception.BikerException;
import io.biker.management.biker.exception.BikerExceptionMessages;
import io.biker.management.biker.repo.BikerRepo;

@Service
public class BikerServiceImpl implements BikerService {
    private BikerRepo bikerRepo;

    public BikerServiceImpl(BikerRepo bikerRepo) {
        this.bikerRepo = bikerRepo;
    }

    @Override
    public Biker createBiker(Biker biker) {
        return bikerRepo.save(biker);
    }

    @Override
    public List<Biker> getAllBikers() {
        return bikerRepo.findAll();
    }

    @Override
    public Biker getSingleBiker(int id) {
        Optional<Biker> opBiker = bikerRepo.findById(id);
        if (opBiker.isPresent()) {
            return opBiker.get();
        } else {
            throw new BikerException(BikerExceptionMessages.BIKER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBiker(int id) {
        getSingleBiker(id);
        bikerRepo.deleteById(id);
    }

}
