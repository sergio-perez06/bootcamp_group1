package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
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
    @Mock private IDiscountTypeRepository discountTypeRepository;
    @Mock private IMakerRepository makerRepository;
    @Mock private IRecordRepository recordRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private IUserRepository userRepository;
    @Mock private IPartRepository partRepository;

    private IStockWarehouseService stockWarehouseService;

    public StockWarehouseServiceTest() {
        openMocks(this);
        stockWarehouseService = new StockWarehouseService(
                 countryDealerRepository,
                 subsidiaryRepository,
                 stockWarehouseRepository,
                 discountTypeRepository,
                 makerRepository,
                 recordRepository,
                 modelMapper,
                 userRepository,
                 partRepository);

    }

    @Test
    public void getPartsListNoParamsOK() throws Exception {
        List<StockWarehouse> stockWarehouse = generateStockWarehouseList();
        List<Record> records = generateRecordsList();
        List<PartDTO> expected = generatePartDTOList();

        when(stockWarehouseRepository.findAll()).thenReturn(stockWarehouse);
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

        CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "0001");

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

        CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "0001");

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
            CountryDealerStockResponseDTO response = stockWarehouseService.addStockToCountryDealer(cdDto, "00000005");
        }).isInstanceOf(ApiException.class).hasMessageContaining("No existe un repuesto con el partCode '00000005'");
    }

}
