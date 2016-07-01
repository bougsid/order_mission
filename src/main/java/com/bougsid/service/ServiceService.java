package com.bougsid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by ayoub on 6/30/2016.
 */
@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    private final static int pageSize = 4;

    @Override
    public Page<Service> findAll(int page) {
        return this.serviceRepository.findAll(new PageRequest(page, pageSize));
    }

    @Override
    public List<Service> getAllServices() {
        return this.serviceRepository.findAll();
    }

    @Override
    public Service save(Service service) {
        return this.serviceRepository.save(service);
    }
}
