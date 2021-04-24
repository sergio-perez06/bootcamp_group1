package com.mercadolibre.fernandez_federico.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Comparator;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.DiscountType;
import com.mercadolibre.fernandez_federico.models.Maker;
import com.mercadolibre.fernandez_federico.models.Record;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import com.mercadolibre.fernandez_federico.repositories.IDiscountTypeRepository;
import com.mercadolibre.fernandez_federico.repositories.IMakerRepository;
import com.mercadolibre.fernandez_federico.repositories.IRecordRepository;
import com.mercadolibre.fernandez_federico.repositories.IStockWarehouseRepository;
import org.eclipse.jetty.websocket.jsr356.encoders.LongEncoder;
import org.modelmapper.ModelMapper;


import org.springframework.stereotype.Service;

@Service
public class StockWarehouseService implements IStockWarehouseService {


    private IStockWarehouseRepository stockWarehouseRepository;
    private IDiscountTypeRepository discountTypeRepository;
    private IMakerRepository makerRepository;
    private IRecordRepository recordRepository;
    private final ModelMapper modelMapper;

    public StockWarehouseService(IStockWarehouseRepository stockWarehouseRepository, ModelMapper modelMapper, IDiscountTypeRepository discountTypeRepository, IMakerRepository makerRepository, IRecordRepository recordRepository)
    {
        this.stockWarehouseRepository = stockWarehouseRepository;
        this.discountTypeRepository = discountTypeRepository;
        this.makerRepository = makerRepository;
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartDTO> getParts(HashMap<String, String> filters) throws Exception {
        if(stockWarehouseRepository.findAll().isEmpty())
            throw new ApiException("Not Found","La lista no existe",404 );
        else {
            //Se cargan los repositorios por separado trayendo lista entera
              List<PartDTO> partsDTO = new ArrayList<>();
              List<StockWarehouse> stockWarehouses = stockWarehouseRepository.findAll()
                      .stream()
                      .map(stock -> modelMapper.map(stock, StockWarehouse.class))
                      .collect(Collectors.toList());
            List<Maker> maker = makerRepository.findAll()
                    .stream()
                    .map(maker1 -> modelMapper.map(maker1, Maker.class))
                    .collect(Collectors.toList());
            List<DiscountType> discountTypes = discountTypeRepository.findAll()
                    .stream()
                    .map(discountType -> modelMapper.map(discountType, DiscountType.class))
                    .collect(Collectors.toList());
            List<Record> records = recordRepository.findAll()
                    .stream()
                    .map(record -> modelMapper.map(record, Record.class))
                    .collect(Collectors.toList());
            //toDo: La función de abajo podría reformularse para no volverla a hacer en todos los ifs y que en los filtros p y v elimine filas.
            if (filters.isEmpty() || (filters.get("queryType").equals("C"))) {
                for(int i=0; i<stockWarehouses.size(); i++){
                    PartDTO part = new PartDTO();
                    part.setPartCode(stockWarehouses.get(i).getPart().getPartCode());
                    part.setDescription(stockWarehouses.get(i).getPart().getDescription());
                    part.setNetWeight(stockWarehouses.get(i).getPart().getNetWeight());
                    part.setLongDimension(stockWarehouses.get(i).getPart().getLongDimension());
                    part.setWidthDimension(stockWarehouses.get(i).getPart().getWidthDimension());
                    part.setTallDimension(stockWarehouses.get(i).getPart().getTallDimension());
                    part.setQuantity(stockWarehouses.get(i).getQuantity());
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
                    for(int f=0; f<maker.size();f++){
                        if(stockWarehouses.get(i).getPart().getMaker().getId().equals(maker.get(f).getId())){
                            part.setMaker(maker.get(f).getName());
                        }
                    }
                    partsDTO.add(part);
                }
                 return partsDTO;

            }if (filters.containsKey("queryType") && (filters.get("queryType").equals("P") && filters.containsKey("date")))
            {
                for(int f=0; f<records.size(); f++){

                    for(int i=0; i<stockWarehouses.size(); i++){
                        PartDTO part = new PartDTO();
                        //Se filtra por fecha y luego se hace la carga
                        if(records.get(f).getLastModification().compareTo(LocalDate.parse(filters.get("date"),DateTimeFormatter.ofPattern("yyyy-MM-dd")))>=0){
                        if(stockWarehouses.get(i).getPart().getId().equals(records.get(f).getPart().getId())){
                                part.setNormalPrice(records.get(f).getNormalPrice());
                                part.setUrgentPrice(records.get(f).getUrgentPrice());
                                part.setLastModification(records.get(f).getLastModification().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                part.setPartCode(stockWarehouses.get(i).getPart().getPartCode());
                                part.setDescription(stockWarehouses.get(i).getPart().getDescription());
                                part.setNetWeight(stockWarehouses.get(i).getPart().getNetWeight());
                                part.setLongDimension(stockWarehouses.get(i).getPart().getLongDimension());
                                part.setWidthDimension(stockWarehouses.get(i).getPart().getWidthDimension());
                                part.setTallDimension(stockWarehouses.get(i).getPart().getTallDimension());
                                part.setQuantity(stockWarehouses.get(i).getQuantity());
                                for (int g = 0; g < discountTypes.size(); g++) {
                                    if (records.get(f).getDiscountType().getId().equals(discountTypes.get(g).getId())) {
                                        part.setDiscountType(discountTypes.get(g).getType());
                                    }
                                }
                                for (int g = 0; g < maker.size(); g++) {
                                    if (stockWarehouses.get(i).getPart().getMaker().getId().equals(maker.get(g).getId())) {
                                        part.setMaker(maker.get(g).getName());
                                    }
                                }
                            partsDTO.add(part);
                            }

                        }

                    }

                }

                return partsDTO;

                //toDO: aún no hicimos el tercer filtro, se hará en el transcurso del finde o principios de lunes.
            } if (filters.containsKey("queryType") && (filters.get("queryType").equals("V") && filters.containsKey("date"))){
                return partsDTO;
            } if(filters.containsKey("order")){
                if(filters.get("order").equals("1")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getDescription));}
                if(filters.get("order").equals("2")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getDescription).reversed());}
                if(filters.get("order").equals("3")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getLastModification));}
            }

        }
        return null;
    }

}
