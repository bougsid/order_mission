package com.bougsid.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 6/30/2016.
 */
public interface IServiceService {
    Page<Service> findAll(int page);

    List<Service> getAllServices();

    Service save(Service service);
}
