package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.request.PostBillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.services.impl.StockWarehouseService;
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
    @DisplayName("Test valido, se agrega Bill a CountryDealer")
    public void addBillToCountryDealerCorrect() {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setDeliveryDate(LocalDate.now().plusDays(1));
        billRequestDTO.setSubsidiaryNumber("0002");

        PostBillDetailDTO postA = new PostBillDetailDTO();
        postA.setPartCode("00000001");
        billRequestDTO.setBillDetails(new ArrayList<>(List.of(postA)));

        Part part = new Part();
        part.setPartCode("00000001");

        Bill bill = new Bill();
        BillDetail billDetail = new BillDetail();
        billDetail.setPart(part);
        bill.setBillDetails(new ArrayList<>(List.of(billDetail)));

        Subsidiary sub = new Subsidiary();
        sub.setSubsidiaryNumber("0001");
        sub.setBills(new ArrayList<>(List.of(bill)));
        CountryDealer cd = new CountryDealer();
        cd.setSubsidiaries(new ArrayList<>(List.of(sub)));

        BillDTO billDTO = new BillDTO();
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setPartCode("00000001");
        billDTO.setOrderDetails(new ArrayList<>(List.of(billDetailDTO)));

        when(partRepository.findByPartCode(anyString())).thenReturn(part);
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cd);
        when(modelMapper.map(any(), any()))
                .thenReturn(billDetail)
                .thenReturn(billDTO)
                .thenReturn(billDetailDTO);
        when(countryDealerRepository.save(cd)).then(any());

        BillDTO response = stockWarehouseService.addBillToCountryDealer(billRequestDTO, "Uruguay");

        assertThat(response.getOrderDetails().size()).isEqualTo(1);
    }

}
