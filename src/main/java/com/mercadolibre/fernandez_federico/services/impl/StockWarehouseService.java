package com.mercadolibre.fernandez_federico.services.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.request.PostBillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.*;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.util.Utils;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.RequiredArgsConstructor;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StockWarehouseService implements IStockWarehouseService {

    private final ICountryDealerRepository countryDealerRepository;
    private final ISubsidiaryRepository subsidiaryRepository;
    private final IStockWarehouseRepository stockWarehouseRepository;
    private final IDiscountTypeRepository discountTypeRepository;
    private final IMakerRepository makerRepository;
    private final IRecordRepository recordRepository;
    private final ModelMapper modelMapper;
    private final IUserRepository userRepository;
    private final IPartRepository partRepository;

    @Override
    public List<PartDTO> getParts(HashMap<String, String> filters) throws Exception {
        if (stockWarehouseRepository.findAll().isEmpty())
            throw new ApiException(NOT_FOUND.name(), "La lista no existe.", NOT_FOUND.value());

        String queryType = filters.getOrDefault("queryType", "");
        LocalDate date = filters.containsKey("date") ? LocalDate.parse(filters.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        Integer order = Integer.parseInt(filters.getOrDefault("order",  "0"));

        boolean p = queryType.equalsIgnoreCase("P") && date == null,
                q = queryType.equalsIgnoreCase("V") && date == null;

        if (p && !q
            || q && !p
            || order > 3 || order < 0
            || date != null && date.isAfter(LocalDate.now())) {
            throw new ApiException(BAD_REQUEST.name(), "No se puede continuar con los parámetros dados.", BAD_REQUEST.value());
        }

        //Se cargan los repositorios por separado trayendo lista entera
        List<PartDTO> partsDTO = new ArrayList<>();
        List<StockWarehouse> stockWarehouses = stockWarehouseRepository.findAll();
        for(int i=stockWarehouses.size()-1; i>=0; i--) {

            switch (queryType) {
                case "P":
                    if(stockWarehouses.get(i).getPart().getRecords().get(stockWarehouses.get(i).getPart().getRecords().size() - 1).getLastModification().isBefore(date))
                        stockWarehouses.remove(stockWarehouses.get(i));
                    break;
                case "V":
                   if(stockWarehouses.get(i).getPart().getRecords().size()<2
                           || stockWarehouses.get(i).getPart().getRecords().get(stockWarehouses.get(i).getPart().getRecords().size()-1).getLastModification().isBefore(date)
                           || stockWarehouses.get(i).getPart().getRecords().get(stockWarehouses.get(i).getPart().getRecords().size()-1).getNormalPrice()==stockWarehouses.get(i).getPart().getRecords().get(stockWarehouses.get(i).getPart().getRecords().size()-2).getNormalPrice()){
                            stockWarehouses.remove(stockWarehouses.get(i));

                    }
                    break;
                }

            }
        if (stockWarehouses.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value());
        stockWarehouses.forEach(stockWarehouse -> partsDTO.add(construct(stockWarehouse)));
        switch (order) {
            case 1:
                partsDTO.sort(Comparator.comparing(PartDTO::getDescription));
                break;
            case 2:
                partsDTO.sort(Comparator.comparing(PartDTO::getDescription).reversed());
                break;
            case 3:
                partsDTO.sort(Comparator.comparing(PartDTO::getLastModification));
                break;
        }

        if (partsDTO.isEmpty()) throw new ApiException(NOT_FOUND.name(), "La lista no existe.", NOT_FOUND.value());

        return partsDTO;
    }

    // Requirement 3
    public void getOrderStatus(String orderNumberCM){
        String[] splitted = orderNumberCM.split("-");

        Integer subsidiaryNumber = Integer.parseInt(splitted[0]);
        Integer CountryDealerNumber = Integer.parseInt(splitted[1]);
        Integer orderNumber = Integer.parseInt(splitted[2]);

        CountryDealer countryDelaer = countryDealerRepository.findByDealerNumber(CountryDealerNumber);
        System.out.println(countryDelaer+"\n");

        Subsidiary subsidiary  = subsidiaryRepository.findBySubsidiaryNumber(subsidiaryNumber);

        System.out.println(subsidiary+"\n");
    }

    // Utilitary
    public List<CountryDealer> getAllCountryDealers (){
        System.out.println(countryDealerRepository.count());
        return countryDealerRepository.findAll();
    }

    @Override
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(String subsidiaryNumber, String countryName, String deliveryStatus, String order) {
        SubsidiaryOrdersByDeliveryStatusDTO response = new SubsidiaryOrdersByDeliveryStatusDTO();
        CountryDealer countryDealer = countryDealerRepository.findByCountry(countryName);

        Optional<Subsidiary> subsidiaryOptional = countryDealer.getSubsidiaries().stream().filter(x -> x.getSubsidiaryNumber().equals(subsidiaryNumber)).findFirst();

        if (subsidiaryOptional.isPresent()) {
            Subsidiary subsidiary = subsidiaryOptional.get();
            List<Bill> bills = subsidiary.getBills();

            if (deliveryStatus != null) {
                bills = bills.stream().filter(x -> x.getDeliveryStatus().getValue().equals(deliveryStatus)).collect(Collectors.toList());
            }

            if (order != null) {
                switch (order) {
                    case "1": {
                        bills.sort(Comparator.comparing(Bill::getOrderDate));
                        break;
                    }

                    case "2": {
                        bills.sort(Comparator.comparing(Bill::getOrderDate).reversed());
                        break;
                    }

                    default:
                        break;
                }
            }

            List<BillDTO> billsResponse = bills.stream().map(x -> modelMapper.map(x, BillDTO.class)).collect(Collectors.toList());

            response.setSubsidiaryNumber(subsidiaryNumber);
            response.setOrders(billsResponse);

            return response;
        } else {
            throw new ApiException("Not found", String.format("No existe subsidiaria con numero %s", subsidiaryNumber), 404);
        }
    }

    // Requirement 4
    @Override
    public CountryDealerStockResponseDTO addStockToCountryDealer(CountryDealerStockDTO countryDealerStockDto, String countryName) {
        CountryDealerStockResponseDTO countryDealerStockResponse = new CountryDealerStockResponseDTO();

        CountryDealer countryDealer = countryDealerRepository.findByCountry(countryName);

        if (countryDealer == null)
            throw new ApiException(NOT_FOUND.name(), "No existe countryDealer asociado", NOT_FOUND.value());

        List<StockDealer> stockDealerList = countryDealer.getStockDealers();

        Optional<StockDealer> stockDealer = stockDealerList.stream()
                .filter(StockDealer -> StockDealer.getPart().getPartCode().equals(countryDealerStockDto.getPartCode()))
                .findFirst();

        // Stock with part already exists on dealer
        if (stockDealer.isPresent()){
            StockDealer result = stockDealer.get();
            result.setQuantity(result.getQuantity() + countryDealerStockDto.getQuantity());

            countryDealerRepository.save(countryDealer);

            PartDTO partDTO = modelMapper.map(result, PartDTO.class);
            countryDealerStockResponse.setPart(partDTO);
        }
        else // Stock no exists on dealer, creating new one
        {
            StockDealer newStock = new StockDealer();
            Part partFound = partRepository.findByPartCode(countryDealerStockDto.getPartCode());

            // Creating new Stock on dealer with existent part
            if (partFound != null)
            {
                newStock.setQuantity(countryDealerStockDto.getQuantity());
                newStock.setPart(partFound);
                newStock.setCountryDealer(countryDealer);

                stockDealerList.add(newStock);
                countryDealerRepository.save(countryDealer);

                PartDTO partDTO = modelMapper.map(newStock, PartDTO.class);
                countryDealerStockResponse.setPart(partDTO);
            }
            else
            {
                throw new ApiException(NOT_FOUND.name(), String.format("No existe un repuesto con el partCode '%s'", countryDealerStockDto.getPartCode()), NOT_FOUND.value());
            }
        }

        return countryDealerStockResponse;
    }

    @Override
    public BillDTO addBillToCountryDealer(BillRequestDTO billRequestDTO, String countryName) {
        if (LocalDate.now().isBefore(billRequestDTO.getDeliveryDate())) {
            CountryDealer countryDealer = countryDealerRepository.findByCountry(countryName);

            Optional<Subsidiary> subsidiary = countryDealer.getSubsidiaries().stream()
                    .filter(Subsidiary -> Subsidiary.getSubsidiaryNumber().equals(billRequestDTO.getSubsidiaryNumber()))
                    .findFirst();

            Set<String> setPartCode = billRequestDTO
                    .getBillDetails()
                    .stream()
                    .map(x -> x.getPartCode())
                    .collect(Collectors.toSet());

            List<Part> partList = setPartCode.stream()
                    .map(partCode-> partRepository.findByPartCode(partCode))
                    .collect(Collectors.toList());

            if (partList.contains(null)) {
                throw new ApiException(NOT_FOUND.name(), "Uno de los 'partCode' enviados no existe en el sistema", NOT_FOUND.value());
            }

            if (subsidiary.isPresent() && (partList.size() == setPartCode.size())){
                Subsidiary result = subsidiary.get();

                Bill bill = new Bill();

                bill.setDeliveryDate(billRequestDTO.getDeliveryDate());
                bill.setOrderDate(LocalDate.now());
                bill.setDeliveryStatus(OrderStatus.Procesando);
                bill.setOrderNumber(getLastOrderNumber(result));
                bill.setCmOrdernumberWarehouse(countryDealer.getDealerNumber() + "-"
                    + result.getSubsidiaryNumber() + "-"
                    + bill.getOrderNumber());

                bill.setSubsidiary(result);

                List<BillDetail> billDetails = billRequestDTO.getBillDetails()
                        .stream()
                        .map(billDetailDTO -> getMappingBillDetail(bill, billDetailDTO, partList))
                        .collect(Collectors.toList());

                bill.setBillDetails(billDetails);

                result.getBills().add(bill);
                subsidiaryRepository.save(result);

                BillDTO billDTO = modelMapper.map(bill, BillDTO.class);

                List<BillDetailDTO> billDetailDTOList = bill.getBillDetails()
                        .stream()
                        .map(billDetail -> modelMapper.map(billDetail,BillDetailDTO.class))
                        .collect(Collectors.toList());

                billDTO.setOrderDetails(billDetailDTOList);
                return billDTO;
            }
            else
            {
                throw new ApiException(NOT_FOUND.name(), "La subsidiaria no fue encontrada", NOT_FOUND.value());
            }
        }
        else
        {
            throw new ApiException(BAD_REQUEST.name(), "La fecha es incorrecta, el deliveryDate no puede ser anterior a la fecha actual", BAD_REQUEST.value());
        }
    }

    private BillDetail getMappingBillDetail(Bill bill, PostBillDetailDTO postBillDetailDTO, List<Part> partList) {
        BillDetail billDetail = modelMapper.map(postBillDetailDTO,BillDetail.class);

        billDetail.setPart(partList.stream()
                .filter(part -> part.getPartCode().equals(postBillDetailDTO.getPartCode()))
                .findFirst()
                .get());

        billDetail.setDescription(billDetail.getPart().getDescription());
        billDetail.setReason("");
        billDetail.setPartStatus(PartStatus.Normal);
        billDetail.setBill(bill);

        return billDetail;
    }

    private String getLastOrderNumber(Subsidiary result) {
        Optional<Bill> filteredBill = result.getBills().stream().max(Comparator.comparing(Bill::getOrderNumber));
        if (filteredBill.isPresent()){
            Integer lastOrder = Integer.parseInt(filteredBill.get().getOrderNumber()) + 1;
            return Utils.padLeftZeros(String.valueOf(lastOrder),8);
        }
        else{
            return "00000001";
        }
    }



    private PartDTO construct(StockWarehouse stockWarehouse) {
        PartDTO partDTO = modelMapper.map(stockWarehouse, PartDTO.class);
        Record record = stockWarehouse.getPart().getRecords().get(stockWarehouse.getPart().getRecords().size()-1);
        partDTO.setMaker(stockWarehouse.getPart().getMaker().getName());
        partDTO.setNormalPrice(record.getNormalPrice());
        partDTO.setUrgentPrice(record.getUrgentPrice());
        partDTO.setLastModification(record.getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        partDTO.setDiscountType(record.getDiscountType().getType());
        return partDTO;
    }
}