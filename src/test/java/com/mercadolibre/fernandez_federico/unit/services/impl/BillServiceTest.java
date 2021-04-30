package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.models.Bill;
import com.mercadolibre.fernandez_federico.models.BillDetail;
import com.mercadolibre.fernandez_federico.repositories.IBillDetailRepository;
import com.mercadolibre.fernandez_federico.repositories.IBillRepository;
import com.mercadolibre.fernandez_federico.services.IBillService;
import com.mercadolibre.fernandez_federico.services.impl.BillService;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BillServiceTest {
    @Mock
    private IBillRepository billRepository;
    @Mock
    private IBillDetailRepository billDetailRepository;
    @Mock
    private ModelMapper modelMapper;

    private IBillService billService;

    public BillServiceTest(){
        openMocks(this);
        billService = new BillService(
                billRepository,
                billDetailRepository,
                modelMapper
        );
    }

    @Test
    public void getBillExpectedOk() throws Exception {
        List<BillDetail> billDetails = generateBillDetailList();
        Bill bills = generateBill();
        BillDTO expected = generateBillDTO();
        List<Bill> newBill = new ArrayList<>(List.of(new Bill()));
        when(billRepository.findAll()).thenReturn(newBill);
        when(billRepository.findByCmOrdernumberWarehouse("0001-0001-00000001")).thenReturn(bills);
        when(billDetailRepository.findAll()).thenReturn(billDetails);
        when(modelMapper.map(any(), any())).thenReturn(expected.getOrderDetails().get(0));
        BillDTO response = billService.getBillDetails("0001-0001-00000001");
        assertEquals(expected,response);
    }

    private Bill generateBill(){
        Bill bills = new Bill(1L,"00000001", "0001-0001-00000001", LocalDate.of(2021, 02, 03), LocalDate.of(2021, 03, 01), OrderStatus.Procesando);
        return bills;
    }

    private List<BillDetail> generateBillDetailList(){
        Bill bills = generateBill();
        List<BillDetail> billDetails = new ArrayList<>();
        billDetails.add(new BillDetail(1L, "Puerta de de un auto", 2, AccountType.Garantia, "Sin motivo", PartStatus.Demorado, bills));
        return billDetails;
    }

    private BillDTO generateBillDTO(){
        List<BillDetailDTO> billDetails = generateBillDetailDTOList();
        BillDTO billDTO = new BillDTO("0001-00000001", LocalDate.of(2021, 02, 03), OrderStatus.Procesando, billDetails);
        return billDTO;
    }
    private List<BillDetailDTO> generateBillDetailDTOList(){
        List<BillDetailDTO> billDetails = new ArrayList<>();
        billDetails.add(new BillDetailDTO("00000003", "Puerta de de un auto", 2, AccountType.Garantia, "Sin motivo", PartStatus.Demorado));
        return billDetails;
    }
}
