package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.Bill;
import com.mercadolibre.fernandez_federico.models.BillDetail;

import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IBillService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class BillService implements IBillService {
    private IBillRepository billRepository;
    private IBillDetailRepository billDetailRepository;
    private ModelMapper modelMapper;

    public BillService(IBillRepository billRepository, IBillDetailRepository billDetailRepository, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.modelMapper = modelMapper;
    }

    public BillDTO getBillDetails(String orderNumberCM) {
        BillDTO finalBill = new BillDTO();

        if (billRepository.findAll().isEmpty()) {
            throw new ApiException(NOT_FOUND.name(), "La orden no existe", NOT_FOUND.value());
        } else {
            Bill bills = billRepository.findByCmOrdernumberWarehouse(orderNumberCM);
            List<BillDetail> billsDetail = billDetailRepository.findAll();

            if (bills.getCmOrdernumberWarehouse().equals(orderNumberCM)) {
                String[] ord = bills.getCmOrdernumberWarehouse().split("-");
                finalBill.setOrderNumber(ord[1] + "-" + ord[2]);
                finalBill.setOrderDate(bills.getOrderDate());
                finalBill.setDeliveryStatus(bills.getDeliveryStatus());

                List<BillDetailDTO> billDetailDTOS = new ArrayList<>();

                billsDetail.forEach(billDetail -> {
                    if (bills.getId().equals(billDetail.getBill().getId())) {
                        BillDetailDTO billDetailDTO = modelMapper.map(billDetail, BillDetailDTO.class);
                        billDetailDTOS.add(billDetailDTO);
                    }
                });

                finalBill.setOrderDetails(billDetailDTOS);
            }
        }
        
        return finalBill;
    }
}
