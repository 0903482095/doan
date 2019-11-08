package com.hoangnt.service.impl;

import com.hoangnt.entity.Shift;
import com.hoangnt.entity.StatusShift;
import com.hoangnt.entity.User;
import com.hoangnt.model.ShiftDTO;
import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.service.StatusShiftService;
import org.springframework.stereotype.Service;

@Service
public class StatusShiftServiceImpl implements StatusShiftService {
    @Override
    public void addStatusShift(StatusShiftDTO statusShiftDTO) {
        StatusShift statusShift=new StatusShift();
        statusShift.setShift(new Shift(statusShiftDTO.getShiftDTO()));
        statusShift.setUser(new User(statusShiftDTO.getUser()));
        statusShift.setDate(statusShiftDTO.getDate());
        statusShift.setStatus(1);
    }
}
