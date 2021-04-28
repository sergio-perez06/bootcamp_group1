package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.services.impl.StockWarehouseService;
import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class StockWarehouseServiceTest {

    @Mock private IStockWarehouseRepository stockWarehouseRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private IDiscountTypeRepository discountTypeRepository;
    @Mock private ICountryDealerRepository countryDealerRepository;
    @Mock private ISubsidiaryRepository subsidiaryRepository;
    @Mock private IMakerRepository makerRepository;
    @Mock private IRecordRepository recordRepository;

    private IStockWarehouseService stockWarehouseService;

    public StockWarehouseServiceTest() {
        openMocks(this);
        this.stockWarehouseService = new StockWarehouseService(stockWarehouseRepository, modelMapper, discountTypeRepository,
                                                                countryDealerRepository, subsidiaryRepository, makerRepository, recordRepository);
    }

    @Test
    public void getPartsListNoParamsOK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Maker> makers = generateMakersList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = generateRecordsList();
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(makerRepository.findAll()).thenReturn(makers);
        when(discountTypeRepository.findAll()).thenReturn(discountTypes);
        when(recordRepository.findAll()).thenReturn(records);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));

        HashMap<String, String> params = new HashMap<>();
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListWith2ParamsOK1() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Maker> makers = generateMakersList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = generateRecordsList();
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(makerRepository.findAll()).thenReturn(makers);
        when(discountTypeRepository.findAll()).thenReturn(discountTypes);
        when(recordRepository.findAll()).thenReturn(records);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));

        HashMap<String, String> params = new HashMap<>();
        params.put("date", "2021-01-22");
        params.put("queryType", "P");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListWith2ParamsOK2() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Maker> makers = generateMakersList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = generateRecordsList();
        List<PartDTO> expected = generatePartDTOList();
        expected.remove(1);

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(makerRepository.findAll()).thenReturn(makers);
        when(discountTypeRepository.findAll()).thenReturn(discountTypes);
        when(recordRepository.findAll()).thenReturn(records);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0));

        HashMap<String, String> params = new HashMap<>();
        params.put("date", "2021-01-22");
        params.put("queryType", "V");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListWith3ParamsOK1() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Maker> makers = generateMakersList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = generateRecordsList();
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(makerRepository.findAll()).thenReturn(makers);
        when(discountTypeRepository.findAll()).thenReturn(discountTypes);
        when(recordRepository.findAll()).thenReturn(records);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));

        HashMap<String, String> params = new HashMap<>();
        params.put("order", "1");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListNoParamsNotFound() {
        HashMap<String, String> asd = new HashMap<>();
        ApiException expected = new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value()),
                        actual = assertThrows(ApiException.class, () -> {
                            this.stockWarehouseService.getParts(asd);
                        });
        assertEquals(expected.getMessage(), actual.getMessage());
        verify(stockWarehouseRepository, atMost(1)).findAll();
    }

    private List<StockWarehouse> generateStockWarehouseList() {
        List<StockWarehouse> stockWarehouse = new ArrayList<>();
        List<Part> parts = generatePartsList();
        stockWarehouse.add(new StockWarehouse(parts.get(0), 10));
        stockWarehouse.add(new StockWarehouse(parts.get(1), 10));
        return stockWarehouse;
    }

    private List<Part> generatePartsList() {
        List<Part> parts = new ArrayList<>();
        List<Maker> makers = generateMakersList();
        parts.add(new Part((long) 1, "00000001", "Farol Chevrolet Spark", 1000, 30, 60, 50, makers.get(0)));
        parts.add(new Part((long) 2, "00000002", "Paragolpe trasero Ford Fiesta", 2500, 130, 460, 50, makers.get(1)));
        return parts;
    }

    private List<Maker> generateMakersList() {
        List<Maker> makers = new ArrayList<>();
        makers.add(new Maker((long) 1, "CHEVROLET"));
        makers.add(new Maker((long) 2, "FORD"));
        return makers;
    }

    private List<DiscountType> generateDiscountTypeList() {
        List<DiscountType> discountTypes = new ArrayList<>();
        discountTypes.add(new DiscountType((long) 1, "CLIENTE VIP", 3));
        discountTypes.add(new DiscountType((long)  2, "Compra al mayor", 5));
        return discountTypes;
    }

    private List<Record> generateRecordsList() {
        List<Part> parts = generatePartsList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = new ArrayList<>();
        records.add(new Record((long) 1, 110.0, 135.0, LocalDate.of(2021, 02, 25), parts.get(0), discountTypes.get(0)));
        records.add(new Record((long) 2, 175.0, 200.0, LocalDate.of(2021, 01, 25), parts.get(1), discountTypes.get(1)));
        records.add(new Record((long) 3, 100.0, 120.0, LocalDate.of(2021, 01, 20), parts.get(0), discountTypes.get(0)));
        return records;
    }

    private List<PartDTO> generatePartDTOList() {
        List<PartDTO> partDTOS = new ArrayList<>();
        partDTOS.add(new PartDTO("00000001", "Farol genérico de Chevrolet Spark", "CHEVROLET", 2500, "Cliente VIP", 110.0, 135.0, 1000, 30, 60, 50, "2021-02-25"));
        partDTOS.add(new PartDTO("00000002", "Paragolpe trasero de Ford Fiesta", "FORD", 1500, "Cliente VIP", 175.0, 200.0, 2500, 130, 40, 50, "2021-01-05"));
        return partDTOS;
    }
}
