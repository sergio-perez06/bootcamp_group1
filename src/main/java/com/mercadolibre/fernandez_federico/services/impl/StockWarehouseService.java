package com.mercadolibre.fernandez_federico.services.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Comparator;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.*;
import com.mercadolibre.fernandez_federico.repositories.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StockWarehouseService implements IStockWarehouseService {

    private ICountryDealerRepository countryDealerRepository;
    private ISubsidiaryRepository subsidiaryRepository;
    private IStockWarehouseRepository stockWarehouseRepository;
    private IDiscountTypeRepository discountTypeRepository;
    private IMakerRepository makerRepository;
    private IRecordRepository recordRepository;
    private ModelMapper modelMapper;
    private IUserRepository userRepository;

    public StockWarehouseService(IStockWarehouseRepository stockWarehouseRepository, ModelMapper modelMapper, IDiscountTypeRepository discountTypeRepository,
                                 ICountryDealerRepository countryDealerRepository, ISubsidiaryRepository subsidiaryRepository,IMakerRepository makerRepository,
                                 IRecordRepository recordRepository)
    {
        this.stockWarehouseRepository = stockWarehouseRepository;
        this.discountTypeRepository = discountTypeRepository;
        this.makerRepository = makerRepository;
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartDTO> getParts(HashMap<String, String> filters) throws Exception {
        if (stockWarehouseRepository.findAll().isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value());
        else {
            //Se cargan los repositorios por separado trayendo lista entera
            List<PartDTO> partsDTO = new ArrayList<>();
            List<StockWarehouse> stockWarehouses = stockWarehouseRepository.findAll();
            List<Maker> makers = makerRepository.findAll();
            List<DiscountType> discountTypes = discountTypeRepository.findAll();
            List<Record> records = recordRepository.findAll();
            if (filters.isEmpty() || (filters.get("queryType").equals("C"))) {
                for (int i = 0; i < stockWarehouses.size(); i++) {
                    PartDTO part = modelMapper.map(stockWarehouses.get(i), PartDTO.class);
                    for (int f = 0; f < records.size(); f++) {
                        if (stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())) {
                            part.setNormalPrice(records.get(f).getNormalPrice());
                            part.setUrgentPrice(records.get(f).getUrgentPrice());
                            part.setLastModification(records.get(f).getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            for (int g = 0; g < discountTypes.size(); g++) {
                                if (records.get(f).getDiscountType().getId().equals(discountTypes.get(g).getId())) {
                                    part.setDiscountType(discountTypes.get(g).getType());
                                }
                            }
                        }
                    }
                    for (int f = 0; f < makers.size(); f++) {
                        if (stockWarehouses.get(i).getPart().getMaker().getId().equals(makers.get(f).getId())) {
                            part.setMaker(makers.get(f).getName());
                        }
                    }
                    partsDTO.add(part);
                }

            } else if (filters.containsKey("queryType") && filters.containsKey("date")) {
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                if(LocalDate.parse(filters.get("date")).compareTo(LocalDate.now())>0)
                    throw new ApiException("internal_error", "La facha ingresada debe ser anterior a la fecha actual", HttpStatus.INTERNAL_SERVER_ERROR.value());
                else if(filters.get("queryType")==null || filters.get("date")==null){
                    throw new ApiException("internal_error", "Los parámetros no pueden estar vacíos", HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
                else if ((filters.get("queryType").equals("P"))) {
                    for (int f = 0; f < records.size(); f++) {
                        for (int i = 0; i < stockWarehouses.size(); i++) {
                            PartDTO part = new PartDTO();
                            //Se filtra por fecha y luego se hace la carga
                            if (records.get(f).getLastModification().compareTo(LocalDate.parse(filters.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))) >= 0) {
                                if (stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())) {
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
                } else if (filters.get("queryType").equals("V")) {
                    HashMap<Long, Double> partHashMap = new HashMap<>();
                    for (int f = 0; f < records.size(); f++) {
                        for (int i = 0; i < stockWarehouses.size(); i++) {
                            //Se filtra por fecha y luego se hace la carga en un hashmap de los primeros codigos de cada pieza en stockwarehouse
                            if (records.get(f).getLastModification().compareTo(LocalDate.parse(filters.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))) >= 0) {
                                if (stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())) {
                                    if (!partHashMap.containsKey(stockWarehouses.get(i).getPart().getId())) {
                                        partHashMap.put(stockWarehouses.get(i).getPart().getId(), records.get(f).getNormalPrice());
                                    }

                                }

                            }

                        }

                    }
                    for (int f = 0; f < records.size(); f++) {
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

                } else{
                    throw new ApiException("internal_error", "El tipo de consulta ingresado no es válido", HttpStatus.INTERNAL_SERVER_ERROR.value());
                }

                if (filters.containsKey("order")) {
                    if (filters.get("order").equals("1")) {
                        partsDTO.sort(Comparator.comparing(PartDTO::getDescription));
                    }
                    else if (filters.get("order").equals("2")) {
                        partsDTO.sort(Comparator.comparing(PartDTO::getDescription).reversed());
                    }
                    else if (filters.get("order").equals("3")) {
                        partsDTO.sort(Comparator.comparing(PartDTO::getLastModification));
                    }else{
                        throw new ApiException("internal_error", "El tipo de ordenamiento ingresado no es válido", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    }
                }

            }
            if (partsDTO.isEmpty())
                throw new ApiException(HttpStatus.NOT_FOUND.name(), "La lista no existe.", HttpStatus.NOT_FOUND.value());

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
    public CountryDealerStockResponseDTO addStockToCountryDealer(CountryDealerStockDTO countryDealerStock, String country) {
        return null;
    }

    @Override
    public BillDTO addBillToCountryDealer(BillRequestDTO billRequestDTO, String countryName) {
        return null;
    }

    //Requirement 2
    @Override
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(String subsidiaryNumber, String countryName, String deliveryStatus, String order) {
        SubsidiaryOrdersByDeliveryStatusDTO response = new SubsidiaryOrdersByDeliveryStatusDTO();

        Subsidiary s = subsidiaryRepository.findBySubsidiaryNumber(Integer.parseInt(subsidiaryNumber));

        if(s!= null){

        }
        return null;
    }
}
