package io.biker.management.biker.service;

import java.util.List;

import io.biker.management.biker.entity.Biker;

public interface BikerService {
    public Biker createBiker(Biker biker);

    public List<Biker> getAllBikers();

    public Biker getSingleBiker(int id);

    public String deleteBiker(int id);
}
