package com.hoangnt.controller;

import com.hoangnt.model.StatusShiftDTO;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.StatusShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class StatusShiftController {
    @Autowired
    StatusShiftService statusShiftService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/statusshift/create")
    public ResponseEntity<Response<Void>> updateShift(@RequestBody StatusShiftDTO statusShiftDTO) {

        Response<Void> response = new Response<>();
        ResponseData<Void> responseData = new ResponseData<>();
        response.setTimestamp(new Timestamp(System.currentTimeMillis()));

        statusShiftService.addStatusShift(statusShiftDTO);
        response.setStatus("OK");
        response.setData(responseData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
