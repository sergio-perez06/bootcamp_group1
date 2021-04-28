package com.mercadolibre.fernandez_federico.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.*;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import lombok.RequiredArgsConstructor;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value());
        else
        {
            //Se cargan los repositorios por separado trayendo lista entera
            List<PartDTO> partsDTO = new ArrayList<>();
            List<StockWarehouse> stockWarehouses = stockWarehouseRepository.findAll()
                    .stream()
                    .map(stock -> modelMapper.map(stock, StockWarehouse.class))
                    .collect(Collectors.toList());
            List<Maker> makers = makerRepository.findAll()
                    .stream()
                    .map(maker -> modelMapper.map(maker, Maker.class))
                    .collect(Collectors.toList());
            List<DiscountType> discountTypes = discountTypeRepository.findAll()
                    .stream()
                    .map(discountType -> modelMapper.map(discountType, DiscountType.class))
                    .collect(Collectors.toList());
            List<Record> records = recordRepository.findAll()
                    .stream()
                    .map(record -> modelMapper.map(record, Record.class))
                    .collect(Collectors.toList());
            //toDo: La función de abajo podría reformularse para no volverla a hacer en todos los ifs y que en los filtros
            // p y v elimine filas.
            if (filters.isEmpty() || (filters.get("queryType").equals("C"))) {
                for(int i=0; i<stockWarehouses.size(); i++){
                    PartDTO part = modelMapper.map(stockWarehouses.get(i), PartDTO.class);
                    for(int f=0; f<records.size(); f++){
                        if(stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())){
                            part.setNormalPrice(records.get(f).getNormalPrice());
                            part.setUrgentPrice(records.get(f).getUrgentPrice());
                            part.setLastModification(records.get(f).getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            for(int g=0; g<discountTypes.size(); g++){
                                if(records.get(f).getDiscountType().getId().equals(discountTypes.get(g).getId())){
                                    part.setDiscountType(discountTypes.get(g).getType());
                                }
                            }
                        }
                    }
                    for(int f=0; f<makers.size();f++){
                        if(stockWarehouses.get(i).getPart().getMaker().getId().equals(makers.get(f).getId())){
                            part.setMaker(makers.get(f).getName());
                        }
                    }
                    partsDTO.add(part);
                }
                // return partsDTO;

            }if (filters.containsKey("queryType") && (filters.get("queryType").equals("P") && filters.containsKey("date")))
        {
            for(int f=0; f<records.size(); f++){
                for(int i=0; i<stockWarehouses.size(); i++){
                    PartDTO part = new PartDTO();
                    //Se filtra por fecha y luego se hace la carga
                    if(records.get(f).getLastModification().compareTo(LocalDate.parse(filters.get("date"),DateTimeFormatter.ofPattern("yyyy-MM-dd")))>=0){
                        if(stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())){
                            part = modelMapper.map(stockWarehouses.get(i), PartDTO.class);
                            part.setNormalPrice(records.get(f).getNormalPrice());
                            part.setUrgentPrice(records.get(f).getUrgentPrice());
                            part.setLastModification(records.get(f).getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            for (int g = 0; g < discountTypes.size(); g++) {
                                if (records.get(f).getDiscountType().getId().equals(discountTypes.get(g).getId())) {
                                    part.setDiscountType(discountTypes.get(g).getType());
                                }
                            }
                            for (int g = 0; g < makers.size(); g++) {
                                if (stockWarehouses.get(i).getPart().getMaker().getId().equals(makers.get(g).getId())) {
                                    part.setMaker(makers.get(g).getName());
                                }
                            }
                            partsDTO.add(part);
                        }

                    }

                }

            }

            //return partsDTO;

            //toDO: aún no hicimos el tercer filtro, se hará en el transcurso del finde o principios de lunes.
        } if (filters.containsKey("queryType") && (filters.get("queryType").equals("V") && filters.containsKey("date"))){
            HashMap<Long, Double> partHashMap = new HashMap<>();
            for(int f=0; f<records.size(); f++){
                for(int i=0; i<stockWarehouses.size(); i++){

                    //Se filtra por fecha y luego se hace la carga en un hashmap de los primeros codigos de cada pieza en stockwarehouse
                    if(records.get(f).getLastModification().compareTo(LocalDate.parse(filters.get("date"),DateTimeFormatter.ofPattern("yyyy-MM-dd")))>=0){
                        if(stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())){
                            if(!partHashMap.containsKey(stockWarehouses.get(i).getPart().getId())){
                                partHashMap.put(stockWarehouses.get(i).getPart().getId(),records.get(f).getNormalPrice());
                            }

                        }

                    }

                }

            }
            for(int f=0; f<records.size(); f++) {
                PartDTO part = new PartDTO();
                for (int i = 0; i < stockWarehouses.size(); i++) {
                    if (stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())
                            && partHashMap.containsKey(stockWarehouses.get(i).getPart().getId())
                            && !partHashMap.get(stockWarehouses.get(i).getPart().getId()).equals(records.get(f).getNormalPrice())) {
                        part.setDescription(stockWarehouses.get(i).getPart().getDescription());
                        part.setNormalPrice(records.get(f).getNormalPrice());
                        part.setUrgentPrice(records.get(f).getUrgentPrice());
                        part.setLastModification(records.get(f).getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        partsDTO.add(part);
                    }
                }
            }

        }if(filters.containsKey("order")){
            if(filters.get("order").equals("1")){
                partsDTO.sort(Comparator.comparing(PartDTO::getDescription));}
            if(filters.get("order").equals("2")){
                partsDTO.sort(Comparator.comparing(PartDTO::getDescription).reversed());}
            if(filters.get("order").equals("3")){
                partsDTO.sort(Comparator.comparing(PartDTO::getLastModification));}
        }
            return partsDTO;
        }
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

    @Override
    public CountryDealerStockResponseDTO addStockToCountryDealer(CountryDealerStockDTO countryDealerStock, String countryName) {
        CountryDealerStockResponseDTO countryDealerStockResponse = new CountryDealerStockResponseDTO();

        CountryDealer countryDealer = countryDealerRepository.findByCountry(countryName);
        StockDealer result = new StockDealer();

        List<StockDealer> stockDealerList = countryDealer.getStockDealers();

        if (stockDealerList.isEmpty()) {
            throw new ApiException("Not Found", "La lista no existe", 404);
        }
        else
        {
            Optional<StockDealer> stockDealer = stockDealerList.stream()
                    .filter(StockDealer -> StockDealer.getPart().getPartCode().equals(countryDealerStock.getPartCode()))
                    .findFirst();

            if (stockDealer.isPresent()){
                result = stockDealer.get();
                result.setQuantity(result.getQuantity() + countryDealerStock.getQuantity());
                countryDealerRepository.save(countryDealer); //seteo el repo de paises
                PartDTO partDTO = modelMapper.map(result,PartDTO.class);

                countryDealerStockResponse.setPart(partDTO);
            }
            else
            {
                StockDealer newStock = new StockDealer();
                Part partFound = partRepository.findByPartCode(countryDealerStock.getPartCode());

                if (partFound != null )
                {
                    newStock.setQuantity(countryDealerStock.getQuantity());
                    newStock.setPart(partFound);
                    newStock.setCountryDealer(countryDealer);

                    stockDealerList.add(newStock);
                    countryDealerRepository.save(countryDealer);

                    PartDTO partDTO = modelMapper.map(newStock,PartDTO.class);
                    countryDealerStockResponse.setPart(partDTO);
                }
                else
                {
                    throw new ApiException("Not Found","La parte no existe",404 );
                }
            }
        }

        return countryDealerStockResponse;
    }

    @Override
    public BillDTO addBillToCountryDealer(BillRequestDTO billRequestDTO, String countryName) {
        BillDTO billDTO = new BillDTO();

        if (validateBillRequest(billRequestDTO)){
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


            if (subsidiary.isPresent() && (partList.size() == setPartCode.size())){
                Subsidiary result = subsidiary.get();

                Bill bill = modelMapper.map(billRequestDTO, Bill.class);
                List<BillDetail> billDetails = billRequestDTO.getBillDetails()
                        .stream()
                        .map(x -> getMappingBillDetail(x,partList))
                        .collect(Collectors.toList());

                bill.setBillDetails(billDetails);
                bill.setOrderDate(LocalDate.now());
                bill.setDeliveryStatus(OrderStatus.Procesando);
                bill.setOrderNumber(getLastOrderNumber(result));
                bill.setCmOrdernumberWarehouse(countryDealer.getDealerNumber() + "-"
                        + result.getSubsidiaryNumber() + "-"
                        + bill.getOrderNumber());

                result.getBills().add(bill);
                subsidiaryRepository.save(result);

                billDTO = modelMapper.map(bill, BillDTO.class);

                List<BillDetailDTO> billDetailDTOList = bill.getBillDetails()
                        .stream()
                        .map(billDetail -> modelMapper.map(billDetail,BillDetailDTO.class))
                        .collect(Collectors.toList());

                billDTO.setOrderDetails(billDetailDTOList);
                return billDTO;
            }
            else{
                throw new ApiException("Not Found","La subsidiaria no fue encontrada",404 );
            }
        }
        else{
            throw new ApiException("Bad Request","La fecha es incorrecta",400 );
        }
    }

    private BillDetail getMappingBillDetail(BillDetailDTO x ,List<Part> partList) {
        BillDetail billDetail = modelMapper.map(x,BillDetail.class);
        billDetail.setPart(partList.stream()
                .filter(part -> part.getPartCode().equals(x.getPartCode()))
                .findFirst().get());

        return billDetail;
    }

    private String getLastOrderNumber(Subsidiary result) {
        Optional<Bill> filteredBill = result.getBills().stream().max(Comparator.comparing(Bill::getOrderNumber));
        if (filteredBill.isPresent()){
            return filteredBill.get().getOrderNumber();

        }
        else{
            return "00000001";
        }
    }

    private boolean validateBillRequest(BillRequestDTO billRequestDTO) {
        LocalDate today = LocalDate.now();
        Boolean status = true;
        if (today.isAfter(billRequestDTO.getDeliveryDate())){
            status = false;
        }
        return status;
    }
}