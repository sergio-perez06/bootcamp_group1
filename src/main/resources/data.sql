-- Maker
INSERT INTO MAKER VALUES (1, "CHEVROLET");
INSERT INTO MAKER VALUES (2, "FIAT");
INSERT INTO MAKER VALUES (3, "FORD");
INSERT INTO MAKER VALUES (4, "VOLKSWAGEN");
INSERT INTO MAKER VALUES (5, "NISSAN");

-- Parts
INSERT INTO PART VALUES (1, "Farol genérico de Chevrolet Spark", 30, 1000, "00000001", 50, 60, 1);
INSERT INTO PART VALUES (2, "Paragolpe trasero de Ford Fiesta", 130, 2500, "00000002", 50, 40, 3);
INSERT INTO PART VALUES (3, "Paragolpe frontal de Fiat Uno", 130, 2500, "00000003", 50, 40, 2);
INSERT INTO PART VALUES (4, "Volante original de Fiat 127", 60, 1500, "00000004", 50, 40, 2);
INSERT INTO PART VALUES (5, "Tablero de Nissan March", 130, 2500, "00000005", 50, 40, 5); -- No hay stock en dealer ni en subsidiarios

-- Discount type
INSERT INTO DISCOUNT_TYPE VALUES (1, 3, "Cliente VIP");
INSERT INTO DISCOUNT_TYPE VALUES (2, 5, "Compra al mayor");

-- Part record
INSERT INTO RECORD VALUES (1, "2021-01-20", 100, 120, 1, 1);
INSERT INTO RECORD VALUES (2, "2021-02-25", 110, 135, 1, 1);
INSERT INTO RECORD VALUES (3, "2021-01-05", 175, 200, 1, 2);
INSERT INTO RECORD VALUES (4, "2021-02-21", 160, 175, 1, 3);
INSERT INTO RECORD VALUES (5, "2020-09-12", 145, 165, 1, 4);
INSERT INTO RECORD VALUES (6, "2020-11-20", 145, 170, 1, 4);
INSERT INTO RECORD VALUES (7, "2020-12-28", 155, 175, 1, 4);
INSERT INTO RECORD VALUES (8, "2021-01-15", 140, 160, 1, 4);
INSERT INTO RECORD VALUES (9, "2020-05-25", 235, 265, 1, 5);

-- Country Dealers
INSERT INTO COUNTRY_DEALER VALUES (1, "Argentina", "0001", "Argentina Country Dealer");
INSERT INTO COUNTRY_DEALER VALUES (2, "Uruguay", "0002", "Uruguay Country Dealer");

-- Roles
INSERT INTO `role` VALUES (1, "Country Dealer");
INSERT INTO `role` VALUES (2, "Admin");

-- Stock Country Dealers
INSERT INTO STOCK_DEALER VALUES (150, 1, 1); -- Country Dealer Argentina
INSERT INTO STOCK_DEALER VALUES (50, 1, 2);
INSERT INTO STOCK_DEALER VALUES (55, 1, 3);
INSERT INTO STOCK_DEALER VALUES (13, 1, 4);

INSERT INTO STOCK_DEALER VALUES (75, 2, 1); -- Country Dealer Uruguay
INSERT INTO STOCK_DEALER VALUES (20, 2, 2);
INSERT INTO STOCK_DEALER VALUES (24, 2, 3);
INSERT INTO STOCK_DEALER VALUES (5, 2, 4);

-- Subisidiarias
INSERT INTO SUBSIDIARY VALUES (1, "Automotriz Alto Palermo BsAs", "0001", 1); -- Subsidiarias de Argentina
INSERT INTO SUBSIDIARY VALUES (2, "Automotriz La Boca BsAs", "0002", 1);

INSERT INTO SUBSIDIARY VALUES (3, "Automotriz Prado Mdeo", "0001", 2); -- Subisidiarias de Uruguay
INSERT INTO SUBSIDIARY VALUES (4, "Automotriz Centro Mdeo", "0002", 2);
INSERT INTO SUBSIDIARY VALUES (5, "Automotriz Pocitos Mdeo", "0003", 2);

-- Stock subsidiarias
INSERT INTO STOCK_SUBSIDIARY VALUES (10, 1, 1); -- Subsidiary Alto Palermo
INSERT INTO STOCK_SUBSIDIARY VALUES (5, 1, 2);
INSERT INTO STOCK_SUBSIDIARY VALUES (2, 1, 3);
INSERT INTO STOCK_SUBSIDIARY VALUES (1, 1, 4);

INSERT INTO STOCK_SUBSIDIARY VALUES (4, 2, 1); -- Subsidiary La Boca
INSERT INTO STOCK_SUBSIDIARY VALUES (2, 2, 2);
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 2, 3);
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 2, 4);

INSERT INTO STOCK_SUBSIDIARY VALUES (4, 3, 1); -- Subsidiary Prado
INSERT INTO STOCK_SUBSIDIARY VALUES (2, 3, 2);
INSERT INTO STOCK_SUBSIDIARY VALUES (2, 3, 3);
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 3, 4);

INSERT INTO STOCK_SUBSIDIARY VALUES (2, 4, 1); -- Subsidiary Centro
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 4, 2);
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 4, 3);
INSERT INTO STOCK_SUBSIDIARY VALUES (0, 4, 4);

INSERT INTO STOCK_SUBSIDIARY VALUES (6, 5, 1); -- Subsidiary Centro
INSERT INTO STOCK_SUBSIDIARY VALUES (2, 5, 2);
INSERT INTO STOCK_SUBSIDIARY VALUES (1, 5, 3);
INSERT INTO STOCK_SUBSIDIARY VALUES (1, 5, 4);

-- Stock warehouse
INSERT INTO STOCK_WAREHOUSE VALUES (2500, 1);
INSERT INTO STOCK_WAREHOUSE VALUES (1500, 2);
INSERT INTO STOCK_WAREHOUSE VALUES (1750, 3);
INSERT INTO STOCK_WAREHOUSE VALUES (125, 4);
INSERT INTO STOCK_WAREHOUSE VALUES (475, 5);

-- Bills de subsidiaria Automotriz Alto Palermo
INSERT INTO BILL VALUES (1, 0, "2021-03-12", "Procesando", "2021-03-10", "00000001", "0001-0001-00000001", 1);
INSERT INTO BILL VALUES (2, 10, "2021-03-20", "Demorado", "2021-03-10", "00000002", "0001-0001-00000002", 1);

-- Bills de subsidiaria Automotriz Prado Mdeo
INSERT INTO BILL VALUES (3, 8, "2021-01-18", "Demorado", "2021-02-10", "00000001", "0002-0001-00000001", 3);
INSERT INTO BILL VALUES (4, 0, "2021-02-24", "Procesado", "2021-02-22", "00000002", "0002-0001-00000002", 3);
INSERT INTO BILL VALUES (5, 7, "2021-03-02", "Demorado", "2021-02-25", "00000003", "0002-0001-00000003", 3);
INSERT INTO BILL VALUES (6, 10, "2021-03-10", "Demorado", "2021-02-28", "00000004", "0002-0001-00000004", 3);

-- Bill details de Bill 1 y 2 de Automotriz Alto Palermo
INSERT INTO BILL_DETAIL VALUES (1, "Repuesto", "Compra paragolpe frontal", "Normal", 1, "Sin motivo", 1, 3);
INSERT INTO BILL_DETAIL VALUES (2, "Garantia", "Compra volante", "Normal", 2, "Sin motivo", 1, 4);
INSERT INTO BILL_DETAIL VALUES (3, "Repuesto", "Compra paragolpe frontal", "Normal", 2, "Sin motivo", 2, 3);
INSERT INTO BILL_DETAIL VALUES (4, "Repuesto", "Compra faroles", "Demorado", 4, "Error del sistema", 2, 1);

-- Bill details de Bill 3 y 4 de Automotriz Prado Mdeo
INSERT INTO BILL_DETAIL VALUES (5, "Repuesto", "Compra paragolpe trasero", "Normal", 1, "Sin motivo", 3, 2);
INSERT INTO BILL_DETAIL VALUES (6, "Garantia", "Compra volante", "Normal", 1, "Sin motivo", 3, 4);
INSERT INTO BILL_DETAIL VALUES (7, "Repuesto", "Compra faroles", "Demorado", 3, "Error logistico", 4, 1);
INSERT INTO BILL_DETAIL VALUES (8, "Repuesto", "Compra paragolpe frontal", "Demorado", 1, "Error cambio repuesto defectuoso", 4, 3);
INSERT INTO BILL_DETAIL VALUES (9, "Repuesto", "Compra volante", "Demorado", 1, "Error logistico", 5, 4);
INSERT INTO BILL_DETAIL VALUES (10, "Repuesto", "Compra volante", "Demorado", 1, "Error logistico", 6, 4);