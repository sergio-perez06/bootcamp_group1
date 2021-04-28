package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.Bill;
import com.mercadolibre.fernandez_federico.models.BillDetail;

import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IBillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    private IBillRepository billRepository;
    private IBillDetailRepository billDetailRepository;
    private IStockWarehouseRepository stockWarehouseRepository;
    private ModelMapper modelMapper;

    public BillService(IBillRepository billRepository, IBillDetailRepository billDetailRepository, IStockWarehouseRepository stockWarehouseRepository, ModelMapper modelMapper)
    {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.stockWarehouseRepository = stockWarehouseRepository;
        this.modelMapper = modelMapper;

    }

    public BillDTO getBillDetails(String orderNumberCM){
        BillDTO finalBill = new BillDTO();
        if(billRepository.findAll().isEmpty())
            throw new ApiException("Not Found","La orden no existe",404 );
        else{
            Bill bills = billRepository.findByCmOrdernumberWarehouse(orderNumberCM);
            List<BillDetail> billsDetail = billDetailRepository.findAll();
                if(bills.getCmOrdernumberWarehouse().equals(orderNumberCM)){
                    String[] ord =bills.getCmOrdernumberWarehouse().split("-");
                    finalBill.setOrderNumber(ord[1]+"-"+ord[2]);
                    finalBill.setOrderDate(bills.getOrderDate());
                    finalBill.setDeliveryStatus(bills.getDeliveryStatus());
                    List<BillDetailDTO> billDetailDTOS = new ArrayList<>();
                    BillDetailDTO billDetailDTO = new BillDetailDTO();
                    for(int i=0; i<billsDetail.size(); i++){
                        if(bills.getId().equals(billsDetail.get(i).getBill().getId())){
                            billDetailDTO = modelMapper.map(billsDetail.get(i), BillDetailDTO.class);
                            billDetailDTOS.add(billDetailDTO);
                        }
                    }
                    finalBill.setOrderDetails(billDetailDTOS);
                }
            }

        return finalBill;
    }
}
