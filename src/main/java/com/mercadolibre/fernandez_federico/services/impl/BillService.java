package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.Bill;
import com.mercadolibre.fernandez_federico.models.BillDetail;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IBillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public List<BillDTO> getBillDetails(String oNum){
        if(billRepository.findBillByCMOrderNumber(oNum)==null)
            throw new ApiException("Not Found","La orden no existe",404 );
        else{
            BillDTO finalBill = new BillDTO();
            List<StockWarehouse> stockWarehouses = stockWarehouseRepository.findAll()
                    .stream()
                    .map(stock -> modelMapper.map(stock, StockWarehouse.class))
                    .collect(Collectors.toList());
            Bill bills = billRepository.findBillByCMOrderNumber(oNum);
            List<BillDetail> billsDetail = billDetailRepository.findAll()
                    .stream()
                    .map(billDetail -> modelMapper.map(billDetail, BillDetail.class))
                    .collect(Collectors.toList());
                if(bills.getCMOrderNumber().equals(oNum)){
                    //toDo: Cortar el string del order number
                    finalBill.setOrderNumber(bills.getCMOrderNumber());
                    finalBill.setOrderDate(bills.getOrderDate());
                    finalBill.setDeliveryStatus(bills.getDeliveryStatus());
                    //toDo: For para iterar por los billsDetails para cargarlos y agregarlos a la lista. Luego ver de conseguir el código de parte desde las partes.
                }
            }
        return null;
        }

    }

