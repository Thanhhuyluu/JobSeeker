package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Industry;

import java.util.List;

public interface IndustryService {
    List<Industry> findAll();
    Industry findById(int id);
    Industry save(Industry industry);
    void deleteById(int id);
}
