package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.request.PostBillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.services.impl.StockWarehouseService;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class StockWarehouseServiceTest {

    @Mock private ICountryDealerRepository countryDealerRepository;
    @Mock private ISubsidiaryRepository subsidiaryRepository;
    @Mock private IStockWarehouseRepository stockWarehouseRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private IPartRepository partRepository;

    private IStockWarehouseService stockWarehouseService;

    public StockWarehouseServiceTest() {
        openMocks(this);
        stockWarehouseService = new StockWarehouseService(
                 countryDealerRepository,
                 subsidiaryRepository,
                 stockWarehouseRepository,
                 modelMapper,
                 partRepository);
    }

    @Test
    public void getPartsListNoParamsOK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<Record> auxList = new ArrayList<>();
        auxList.add(records.get(0));
        auxList.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList);
        auxList.clear();
        auxList.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList);
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));
        HashMap<String, String> params = new HashMap<>();
        List<PartDTO> actual = stockWarehouseService.getParts(params);

        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(2)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListWith2ParamsOK1() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<DiscountType> discountTypes = generateDiscountTypeList();

        // New record needed
        Maker auxMaker = new Maker(3L, "NISSAN");
        Part auxPart = new Part(3L, "00000003", "Tablero de Nissan March", 2500, 130, 40, 50, auxMaker);
        Record auxRecord = new Record(3L, 235.0, 265.0, LocalDate.of(2020, 05, 25), auxPart, discountTypes.get(0));
        StockWarehouse auxStock = new StockWarehouse(auxPart, 3);
        stockWarehouse.add(auxStock);

        List<Record> records = generateRecordsList();
        List<Record> auxList1 = new ArrayList<>();
        auxList1.add(records.get(0));
        auxList1.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList1);

        List<Record> auxList2 = new ArrayList<>();
        auxList2.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList2);

        List<Record> auxList3 = new ArrayList<>();
        auxList3.add(auxRecord);
        stockWarehouse.get(2).getPart().setRecords(auxList3);

        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));
        HashMap<String, String> params = new HashMap<>();
        params.put("date", "2021-01-01");
        params.put("queryType", "P");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(2)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListWith2ParamsOK2() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<Record> auxList1 = new ArrayList<>();
        auxList1.add(records.get(0));
        auxList1.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList1);
        List<Record> auxList2 = new ArrayList<>();
        auxList2.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList2);
        List<PartDTO> expected = generatePartDTOList();
        expected.remove(expected.get(1));

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0));
        HashMap<String, String> params = new HashMap<>();
        params.put("date", "2021-01-01");
        params.put("queryType", "V");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(1)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListParamOrder1OK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<Record> auxList1 = new ArrayList<>();
        auxList1.add(records.get(0));
        auxList1.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList1);
        List<Record> auxList2 = new ArrayList<>();
        auxList2.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList2);
        List<PartDTO> expected = generatePartDTOList();
        Collections.reverse(expected);

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "1");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(2)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListParamOrder2OK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<Record> auxList1 = new ArrayList<>();
        auxList1.add(records.get(0));
        auxList1.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList1);
        List<Record> auxList2 = new ArrayList<>();
        auxList2.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList2);
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0), expected.get(1));
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "2");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(2)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListParamOrder3OK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<Record> auxList1 = new ArrayList<>();
        auxList1.add(records.get(0));
        auxList1.add(records.get(2));
        stockWarehouse.get(0).getPart().setRecords(auxList1);
        List<Record> auxList2 = new ArrayList<>();
        auxList2.add(records.get(1));
        stockWarehouse.get(1).getPart().setRecords(auxList2);
        List<PartDTO> expected = generatePartDTOList();
        Collections.reverse(expected);

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(1), expected.get(0));
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "3");
        List<PartDTO> actual = stockWarehouseService.getParts(params);
        verify(stockWarehouseRepository, atMostOnce()).findAll();
        verify(modelMapper, atMost(2)).map(any(), any());
        assertEquals(expected, actual);
    }

    @Test
    public void getPartsListParamOrder4BadRequest() {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "4");
        ApiException expected = new ApiException(HttpStatus.BAD_REQUEST.name(), "No se puede continuar con los parámetros dados.", HttpStatus.BAD_REQUEST.value()),
                    actual = assertThrows(ApiException.class, () -> {
                        this.stockWarehouseService.getParts(params);
                    });
        assertEquals(expected.getMessage(), actual.getMessage());
        verify(stockWarehouseRepository, atMost(1)).findAll();
    }

    @Test
    public void getPartsListNoParamsNotFound() {
        HashMap<String, String> params = new HashMap<>();
        ApiException expected = new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value()),
                actual = assertThrows(ApiException.class, () -> {
                    this.stockWarehouseService.getParts(params);
                });
        assertEquals(expected.getMessage(), actual.getMessage());
        verify(stockWarehouseRepository, atMost(1)).findAll();
    }

    @Test
    public void getSubsidiaryOrdersSubsidiaryNotFound(){
        String subsidiaryNumber = "0348";
        String countryName = "Argentina";
        CountryDealer countryDealer = generateCountryDealer();

        when(countryDealerRepository.findByCountry(countryName)).thenReturn(countryDealer);
        ApiException apiException = new ApiException("Not found",String.format("No existe subsidiaria con numero %s",subsidiaryNumber), 404),
                        actual = assertThrows(ApiException.class, () -> {
                            this.stockWarehouseService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber,countryName,null,null);
                        });
        assertEquals(apiException.getMessage(), actual.getMessage());

        verify(countryDealerRepository, atMost(1)).findByCountry(countryName);
    }

    @Test
    public void getSubsidiaryOrders(){
        String subsidiaryNumber = "0001";
        String countryName = "Argentina";

        CountryDealer countryDealer = generateCountryDealer();
        List<BillDTO> expected = generateBillDTOList();

        when(countryDealerRepository.findByCountry(countryName)).thenReturn(countryDealer);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0),
                expected.get(1),
                expected.get(2));
        SubsidiaryOrdersByDeliveryStatusDTO response = stockWarehouseService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber
                ,countryName,null,null);
        verify(countryDealerRepository, atMost(1)).findByCountry(countryName);
        assertEquals(3, response.getOrders().size());
    }

    @Test
    public void getSubsidiaryOrdersInAscendantOrder(){
        String subsidiaryNumber = "0001";
        String countryName = "Argentina";
        String order = "1";

        List<BillDTO> expected = generateBillDTOList();
        CountryDealer countryDealer = generateCountryDealer();

        when(countryDealerRepository.findByCountry(countryName)).thenReturn(countryDealer);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0),
                expected.get(1),
                expected.get(2));

        SubsidiaryOrdersByDeliveryStatusDTO response = stockWarehouseService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber
                ,countryName,null,order);
        verify(countryDealerRepository, atMost(1)).findByCountry(countryName);
        assertTrue(response.getOrders().get(1).getOrderDate().isAfter(response.getOrders().get(0).getOrderDate()));

        assertEquals(3, response.getOrders().size());
    }

    @Test
    public void getSubsidiaryOrdersFilteredByStatusInDescendantOrder(){
        String subsidiaryNumber = "0001";
        String countryName = "Argentina";
        String deliveryStatus = "Demorado";
        String order = "2";

        List<BillDTO> expected = generateBillDTOList();
        CountryDealer countryDealer = generateCountryDealer();

        when(countryDealerRepository.findByCountry(countryName)).thenReturn(countryDealer);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0),
                expected.get(1),
                expected.get(2));

        SubsidiaryOrdersByDeliveryStatusDTO response = stockWarehouseService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber
                ,countryName,deliveryStatus,order);
        verify(countryDealerRepository, atMost(1)).findByCountry(countryName);
        assertTrue(response.getOrders().get(0).getOrderDate().isAfter(response.getOrders().get(1).getOrderDate()));
        assertEquals(2, response.getOrders().size());
    }

    @Test
    public void getSubsidiaryOrdersFilteredByStatus(){
        String subsidiaryNumber = "0001";
        String countryName = "Argentina";
        String deliveryStatus = "Demorado";

        List<BillDTO> expected = generateBillDTOList();
        CountryDealer countryDealer = generateCountryDealer();

        when(countryDealerRepository.findByCountry(countryName)).thenReturn(countryDealer);
        when(modelMapper.map(any(), any())).thenReturn(expected.get(0),
                expected.get(1),
                expected.get(2));

        SubsidiaryOrdersByDeliveryStatusDTO response = stockWarehouseService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber
                ,countryName,deliveryStatus,null);
        verify(countryDealerRepository, atMost(1)).findByCountry(countryName);
        assertEquals(2, response.getOrders().size());
    }

    private CountryDealer generateCountryDealer() {
        CountryDealer response = new CountryDealer();
        response.setId(1L);
        response.setDealerNumber("0001");
        response.setName("Automotriz Bs As");
        response.setCountry("Argentina");
        response.setSubsidiaries(generateSubsidiaryList());
        return response;
    }

    private List<Subsidiary> generateSubsidiaryList() {
        List<Subsidiary> subsidiaries = new ArrayList<>();
        Subsidiary sub1 = new Subsidiary();
        Subsidiary sub2 = new Subsidiary();
        Subsidiary sub3 = new Subsidiary();
        sub1.setId(1L);
        sub1.setSubsidiaryNumber("0001");
        sub1.setName("Subsidiaria 1");
        sub1.setBills(generateBillList());

        sub2.setId(2L);
        sub2.setSubsidiaryNumber("0002");
        sub2.setName("Subsidiaria 2");

        sub3.setId(3L);
        sub3.setSubsidiaryNumber("0003");
        sub3.setName("Subsidiaria 3");

        subsidiaries.add(sub1);
        subsidiaries.add(sub2);
        subsidiaries.add(sub3);

        return subsidiaries;
    }

    private List<Bill> generateBillList() {
        List<Bill> response = new ArrayList<>();

        Bill bill1 = new Bill();
        bill1.setId(1L);
        bill1.setOrderNumber("00000001");
        bill1.setCmOrdernumberWarehouse("0000000100000001");
        bill1.setOrderDate(LocalDate.of(2021,01,15));
        bill1.setDeliveryStatus(OrderStatus.Procesando);
        bill1.setDeliveryDate(LocalDate.of(2021,02,15));

        Bill bill2 = new Bill();
        bill2.setId(2L);
        bill2.setOrderNumber("00000002");
        bill2.setCmOrdernumberWarehouse("0000000100000002");
        bill2.setOrderDate(LocalDate.of(2020,01,15));
        bill2.setDeliveryStatus(OrderStatus.Demorado);
        bill2.setDeliveryDate(LocalDate.of(2020,02,15));

        Bill bill3 = new Bill();
        bill3.setId(3L);
        bill3.setOrderNumber("00000003");
        bill3.setCmOrdernumberWarehouse("0000000100000003");
        bill3.setOrderDate(LocalDate.of(2019,01,15));
        bill3.setDeliveryStatus(OrderStatus.Demorado);
        bill3.setDeliveryDate(LocalDate.of(2019,02,15));

        response.add(bill1);
        response.add(bill2);
        response.add(bill3);

        return response;
    }

    private List<BillDTO> generateBillDTOList() {
        List<BillDTO> response = new ArrayList<>();

        BillDTO bill1 = new BillDTO();

        bill1.setOrderNumber("00000001");
        bill1.setOrderDate(LocalDate.of(2021,01,15));
        bill1.setDeliveryStatus(OrderStatus.Procesando);
        bill1.setDeliveryDate(LocalDate.of(2021,02,15));

        BillDTO bill2 = new BillDTO();
        bill2.setOrderNumber("00000002");
        bill2.setOrderDate(LocalDate.of(2020,01,15));
        bill2.setDeliveryStatus(OrderStatus.Demorado);
        bill2.setDeliveryDate(LocalDate.of(2020,02,15));

        BillDTO bill3 = new BillDTO();
        bill3.setOrderNumber("00000003");
        bill3.setOrderDate(LocalDate.of(2019,01,15));
        bill3.setDeliveryStatus(OrderStatus.Demorado);
        bill3.setDeliveryDate(LocalDate.of(2019,02,15));

        response.add(bill1);
        response.add(bill2);
        response.add(bill3);

        return response;
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
        parts.add(new Part(1L, "00000001", "Farol Chevrolet Spark", 1000, 30, 60, 50, makers.get(0)));
        parts.add(new Part(2L, "00000002", "Paragolpe trasero Ford Fiesta", 2500, 130, 460, 50, makers.get(1)));
        return parts;
    }

    private List<Maker> generateMakersList() {
        List<Maker> makers = new ArrayList<>();
        makers.add(new Maker(1L, "CHEVROLET"));
        makers.add(new Maker(2L, "FORD"));
        return makers;
    }

    private List<DiscountType> generateDiscountTypeList() {
        List<DiscountType> discountTypes = new ArrayList<>();
        discountTypes.add(new DiscountType(1L, "CLIENTE VIP", 3));
        discountTypes.add(new DiscountType(2L, "Compra al mayor", 5));
        return discountTypes;
    }

    private List<Record> generateRecordsList() {
        List<Part> parts = generatePartsList();
        List<DiscountType> discountTypes = generateDiscountTypeList();
        List<Record> records = new ArrayList<>();
        records.add(new Record(1L, 110.0, 135.0, LocalDate.of(2021, 02, 25), parts.get(0), discountTypes.get(0)));
        records.add(new Record(2L, 175.0, 200.0, LocalDate.of(2021, 01, 25), parts.get(1), discountTypes.get(1)));
        records.add(new Record(3L, 100.0, 120.0, LocalDate.of(2021, 01, 20), parts.get(0), discountTypes.get(0)));
        return records;
    }

    private List<PartDTO> generatePartDTOList() {
        List<PartDTO> partDTOS = new ArrayList<>();
        partDTOS.add(new PartDTO("00000002", "Paragolpe trasero de Ford Fiesta", "FORD", 1500, "Cliente VIP", 175.0, 200.0, 2500, 130, 40, 50, "2021-01-05"));
        partDTOS.add(new PartDTO("00000001", "Farol genérico de Chevrolet Spark", "CHEVROLET", 2500, "Cliente VIP", 110.0, 135.0, 1000, 30, 60, 50, "2021-02-25"));
        return partDTOS;
    }

    @Test
    @DisplayName("Test invalido countryDealer no existente")
    public void addStockToCountryDealerInvalidNotExistentCountry() throws Exception {
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(new CountryDealerStockDTO(), "pepe");
        }).isInstanceOf(ApiException.class).hasMessageContaining("No existe countryDealer");
    }

    @Test
    @DisplayName("Test válido countryDealer con stock ya existente")
    public void addStockToCountryDealerValidExistentStock() throws Exception {
        CountryDealerStockDTO cdDto = new CountryDealerStockDTO();
        cdDto.setPartCode("00000001");
        cdDto.setQuantity(10);

        CountryDealer cd = new CountryDealer();
        StockDealer sd = new StockDealer();
        Part part = new Part();
        part.setPartCode("00000001");
        sd.setPart(part);
        sd.setQuantity(5);

        cd.setStockDealers(new ArrayList<>(Arrays.asList(sd)));

        PartDTO partDTO = new PartDTO();
        partDTO.setPartCode("00000001");
        partDTO.setQuantity(15);

        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(countryDealerRepository.save(cd)).then(returnsFirstArg());
        when(modelMapper.map(any(), any())).thenReturn(partDTO);

        CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "Uruguay");

        assertThat(response.getPart().getPartCode()).isEqualTo("00000001");
        assertThat(response.getPart().getQuantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("Test válido countryDealer creando nuevo stock")
    public void addStockToCountryDealerValidNewStock() throws Exception {
        CountryDealerStockDTO cdDto = new CountryDealerStockDTO();
        cdDto.setPartCode("00000001");
        cdDto.setQuantity(10);

        CountryDealer cd = new CountryDealer();
        cd.setStockDealers(new ArrayList<>());

        Part part = new Part();
        part.setPartCode("00000001");

        PartDTO partDTO = new PartDTO();
        partDTO.setPartCode("00000001");
        partDTO.setQuantity(10);

        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(partRepository.findByPartCode(anyString())).thenReturn(part);
        when(countryDealerRepository.save(cd)).then(returnsFirstArg());
        when(modelMapper.map(any(), any())).thenReturn(partDTO);

        CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "Uruguay");

        assertThat(response.getPart().getPartCode()).isEqualTo("00000001");
        assertThat(response.getPart().getQuantity()).isEqualTo(10);
        assertThat(cd.getStockDealers().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test invalido countryDealer sin stock y parte no existente")
    public void addStockToCountryDealerInvalidInexistentPart() {
        CountryDealerStockDTO cdDto = new CountryDealerStockDTO();
        cdDto.setPartCode("00000005");
        cdDto.setQuantity(10);

        CountryDealer cd = new CountryDealer();
        cd.setStockDealers(new ArrayList<>());

        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(partRepository.findByPartCode(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "Uruguay");
        }).isInstanceOf(ApiException.class).hasMessageContaining("No existe un repuesto con el partCode '00000005'");
    }

    @Test
    @DisplayName("Test invalido BillRequestDTO con deliveryDate anterior a la fecha actual")
    public void addBillToCountryDealerInvalidDate() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.parse("2019-01-01"));

        assertThatThrownBy(() -> {
            BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");
        }).isInstanceOf(ApiException.class).hasMessageContaining("La fecha es incorrecta, el deliveryDate no puede ser anterior a la fecha actual");
    }

    @Test
    @DisplayName("Test invalido partList inexistente")
    public void addBillToCountryDealerInvalidPartCodeSent() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.now().plusDays(1));

        PostBillDetailDTO postA = new PostBillDetailDTO();
        postA.setPartCode("00000001");
        PostBillDetailDTO postB = new PostBillDetailDTO();
        postB.setPartCode("00000002");

        billRequestDTO.setBillDetails(new ArrayList<>(List.of(postA, postB)));

        Part part = new Part();
        part.setPartCode("00000002");

        when(partRepository.findByPartCode(anyString()))
                .thenReturn(null)
                .thenReturn(part);

        assertThatThrownBy(() -> {
            BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");
        }).isInstanceOf(ApiException.class).hasMessageContaining("Uno de los 'partCode' enviados no existe en el sistema");
    }

    @Test
    @DisplayName("Test invalido subsidiaria inexistente")
    public void addBillToCountryDealerInvalidSubsidiaryNotFound() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.now().plusDays(1));
        billRequestDTO.setSubsidiaryNumber("0002");

        PostBillDetailDTO postA = new PostBillDetailDTO();
        postA.setPartCode("00000001");
        billRequestDTO.setBillDetails(new ArrayList<>(List.of(postA)));

        CountryDealer cd = new CountryDealer();
        cd.setSubsidiaries(new ArrayList<>());

        Part part = new Part();
        part.setPartCode("00000001");

        when(partRepository.findByPartCode(anyString())).thenReturn(part);
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);

        assertThatThrownBy(() -> {
            BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");
        }).isInstanceOf(ApiException.class).hasMessageContaining("La subsidiaria no fue encontrada");
    }

    @Test
    @DisplayName("Test valido, se agrega Bill a CountryDealer. Subsidiaria con bills existentes")
    public void addBillToCountryDealerCorrectWithBills() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.now().plusDays(1));
        billRequestDTO.setSubsidiaryNumber("0001");

        PostBillDetailDTO postA = new PostBillDetailDTO();
        postA.setPartCode("00000001");
        billRequestDTO.setBillDetails(new ArrayList<>(List.of(postA)));

        Part part = new Part();
        part.setPartCode("00000001");
        part.setDescription("Descripcion");

        Bill bill = new Bill();
        BillDetail billDetail = new BillDetail();
        billDetail.setPart(part);
        bill.setBillDetails(new ArrayList<>(List.of(billDetail)));
        bill.setOrderNumber("00000001");

        Subsidiary sub = new Subsidiary();
        sub.setSubsidiaryNumber("0001");
        sub.setBills(new ArrayList<>(List.of(bill)));
        CountryDealer cd = new CountryDealer();
        cd.setSubsidiaries(new ArrayList<>(List.of(sub)));

        BillDTO billDTO = new BillDTO();
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setPartCode("00000001");
        billDTO.setOrderDetails(new ArrayList<>(List.of(billDetailDTO)));
        billDTO.setOrderNumber("00000002");

        when(partRepository.findByPartCode(anyString())).thenReturn(part);
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(modelMapper.map(any(), any()))
                .thenReturn(billDetail)
                .thenReturn(billDTO)
                .thenReturn(billDetailDTO);
        when(countryDealerRepository.save(cd)).thenReturn(any());

        BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");

        assertThat(response.getOrderDetails().size()).isEqualTo(1);
        assertThat(response.getOrderNumber()).isEqualTo("00000002");
    }

    @Test
    @DisplayName("Test valido, se agrega Bill a CountryDealer. Subsidiaria sin bills existentes")
    public void addBillToCountryDealerCorrectSubsidiaryWithoutBills() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.now().plusDays(1));
        billRequestDTO.setSubsidiaryNumber("0001");

        PostBillDetailDTO postA = new PostBillDetailDTO();
        postA.setPartCode("00000001");
        billRequestDTO.setBillDetails(new ArrayList<>(List.of(postA)));

        Part part = new Part();
        part.setPartCode("00000001");
        part.setDescription("Descripcion");

        BillDetail billDetail = new BillDetail();
        billDetail.setPart(part);

        Subsidiary sub = new Subsidiary();
        sub.setSubsidiaryNumber("0001");
        sub.setBills(new ArrayList<>());
        CountryDealer cd = new CountryDealer();
        cd.setSubsidiaries(new ArrayList<>(List.of(sub)));

        BillDTO billDTO = new BillDTO();
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setPartCode("00000001");
        billDTO.setOrderDetails(new ArrayList<>(List.of(billDetailDTO)));
        billDTO.setOrderNumber("00000001");

        when(partRepository.findByPartCode(anyString())).thenReturn(part);
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(modelMapper.map(any(), any()))
                .thenReturn(billDetail)
                .thenReturn(billDTO)
                .thenReturn(billDetailDTO);
        when(countryDealerRepository.save(cd)).thenReturn(any());

        BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");

        assertThat(response.getOrderDetails().size()).isEqualTo(1);
        assertThat(response.getOrderNumber()).isEqualTo("00000001");
    }

}
