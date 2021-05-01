-- Maker
INSERT INTO maker VALUES (1, 'CHEVROLET');
INSERT INTO maker VALUES (2, 'FIAT');
INSERT INTO maker VALUES (3, 'FORD');
INSERT INTO maker VALUES (4, 'VOLKSWAGEN');
INSERT INTO maker VALUES (5, 'NISSAN');

-- Parts
INSERT INTO part VALUES (1, 'Farol genérico de Chevrolet Spark', 30, 1000, '00000001', 50, 60, 1);
INSERT INTO part VALUES (2, 'Paragolpe trasero de Ford Fiesta', 130, 2500, '00000002', 50, 40, 3);
INSERT INTO part VALUES (3, 'Paragolpe frontal de Fiat Uno', 130, 2500, '00000003', 50, 40, 2);
INSERT INTO part VALUES (4, 'Volante original de Fiat 127', 60, 1500, '00000004', 50, 40, 2);
INSERT INTO part VALUES (5, 'Tablero de Nissan March', 130, 2500, '00000005', 50, 40, 5); -- No hay stock en dealer ni en subsidiarios

-- Discount type
INSERT INTO discount_type VALUES (1, 3, 'Cliente VIP');
INSERT INTO discount_type VALUES (2, 5, 'Compra al mayor');

-- Part record
INSERT INTO record VALUES (1, '2021-01-20', 100, 120, 1, 1);
INSERT INTO record VALUES (2, '2021-02-25', 110, 135, 1, 1);
INSERT INTO record VALUES (3, '2021-01-05', 175, 200, 1, 2);
INSERT INTO record VALUES (4, '2021-02-21', 160, 175, 1, 3);
INSERT INTO record VALUES (5, '2020-09-12', 145, 165, 1, 4);
INSERT INTO record VALUES (6, '2020-11-20', 145, 170, 1, 4);
INSERT INTO record VALUES (7, '2020-12-28', 155, 175, 1, 4);
INSERT INTO record VALUES (8, '2021-01-15', 140, 160, 1, 4);
INSERT INTO record VALUES (9, '2020-05-25', 235, 265, 1, 5);

-- Country Dealers
INSERT INTO country_dealer VALUES (1, 'Argentina', '0001', 'Argentina Country Dealer');
INSERT INTO country_dealer VALUES (2, 'Uruguay', '0002', 'Uruguay Country Dealer');

-- Roles
INSERT INTO `role` VALUES (1, 'Country Dealer');
INSERT INTO `role` VALUES (2, 'Admin');

-- Stock Country Dealers
INSERT INTO stock_dealer VALUES (150, 1, 1); -- Country Dealer Argentina
INSERT INTO stock_dealer VALUES (50, 1, 2);
INSERT INTO stock_dealer VALUES (55, 1, 3);
INSERT INTO stock_dealer VALUES (13, 1, 4);

INSERT INTO stock_dealer VALUES (75, 2, 1); -- Country Dealer Uruguay
INSERT INTO stock_dealer VALUES (20, 2, 2);
INSERT INTO stock_dealer VALUES (24, 2, 3);
INSERT INTO stock_dealer VALUES (5, 2, 4);

-- Subisidiarias
INSERT INTO subsidiary VALUES (1, 'Automotriz Alto Palermo BsAs', '0001', 1); -- Subsidiarias de Argentina
INSERT INTO subsidiary VALUES (2, 'Automotriz La Boca BsAs', '0002', 1);

INSERT INTO subsidiary VALUES (3, 'Automotriz Prado Mdeo', '0001', 2); -- Subisidiarias de Uruguay
INSERT INTO subsidiary VALUES (4, 'Automotriz Centro Mdeo', '0002', 2);
INSERT INTO subsidiary VALUES (5, 'Automotriz Pocitos Mdeo', '0003', 2);

-- Stock subsidiarias
INSERT INTO stock_subsidiary VALUES (10, 1, 1); -- Subsidiary Alto Palermo
INSERT INTO stock_subsidiary VALUES (5, 1, 2);
INSERT INTO stock_subsidiary VALUES (2, 1, 3);
INSERT INTO stock_subsidiary VALUES (1, 1, 4);

INSERT INTO stock_subsidiary VALUES (4, 2, 1); -- Subsidiary La Boca
INSERT INTO stock_subsidiary VALUES (2, 2, 2);
INSERT INTO stock_subsidiary VALUES (0, 2, 3);
INSERT INTO stock_subsidiary VALUES (0, 2, 4);

INSERT INTO stock_subsidiary VALUES (4, 3, 1); -- Subsidiary Prado
INSERT INTO stock_subsidiary VALUES (2, 3, 2);
INSERT INTO stock_subsidiary VALUES (2, 3, 3);
INSERT INTO stock_subsidiary VALUES (0, 3, 4);

INSERT INTO stock_subsidiary VALUES (2, 4, 1); -- Subsidiary Centro
INSERT INTO stock_subsidiary VALUES (0, 4, 2);
INSERT INTO stock_subsidiary VALUES (0, 4, 3);
INSERT INTO stock_subsidiary VALUES (0, 4, 4);

INSERT INTO stock_subsidiary VALUES (6, 5, 1); -- Subsidiary Centro
INSERT INTO stock_subsidiary VALUES (2, 5, 2);
INSERT INTO stock_subsidiary VALUES (1, 5, 3);
INSERT INTO stock_subsidiary VALUES (1, 5, 4);

-- Stock warehouse
INSERT INTO stock_warehouse VALUES (2500, 1);
INSERT INTO stock_warehouse VALUES (1500, 2);
INSERT INTO stock_warehouse VALUES (1750, 3);
INSERT INTO stock_warehouse VALUES (125, 4);
INSERT INTO stock_warehouse VALUES (475, 5);

-- Bills de subsidiaria Automotriz Alto Palermo

INSERT INTO bill VALUES (1, '0001-0001-00000001', 0, '2021-03-12', 'Procesando', '2021-03-10', '00000001', 1);
INSERT INTO bill VALUES (2, '0001-0001-00000002', 5, '2021-03-20', 'Demorado', '2021-03-10', '00000002', 1);

-- Bills de subsidiaria Automotriz Prado Mdeo
INSERT INTO bill VALUES (3, '0002-0001-00000001', 8, '2021-02-18', 'Demorado', '2021-02-10', '00000001', 3);
INSERT INTO bill VALUES (4, '0002-0001-00000002', 0, '2021-02-24', 'Procesando', '2021-02-20', '00000002', 3);
INSERT INTO bill VALUES (5, '0002-0001-00000003', 8, '2021-03-02', 'Demorado', '2021-02-22', '00000003', 3);
INSERT INTO bill VALUES (6, '0002-0001-00000004', 14, '2021-03-10', 'Demorado', '2021-02-24', '00000004', 3);

-- Bill details de Bill 1 y 2 de Automotriz Alto Palermo
INSERT INTO bill_detail VALUES (1, 'Repuesto', 'Compra paragolpe frontal', 'Normal', 1, 'Sin motivo', 1, 3);
INSERT INTO bill_detail VALUES (2, 'Garantia', 'Compra volante', 'Normal', 2, 'Sin motivo', 1, 4);
INSERT INTO bill_detail VALUES (3, 'Repuesto', 'Compra paragolpe frontal', 'Normal', 2, 'Sin motivo', 2, 3);
INSERT INTO bill_detail VALUES (4, 'Repuesto', 'Compra faroles', 'Demorado', 4, 'Error del sistema', 2, 1);

-- Bill details de Bill 3 y 4 de Automotriz Prado Mdeo
INSERT INTO bill_detail VALUES (5, 'Repuesto', 'Compra paragolpe trasero', 'Normal', 1, 'Sin motivo', 3, 2);
INSERT INTO bill_detail VALUES (6, 'Garantia', 'Compra volante', 'Normal', 1, 'Sin motivo', 3, 4);
INSERT INTO bill_detail VALUES (7, 'Repuesto', 'Compra faroles', 'Demorado', 3, 'Error logistico', 4, 1);
INSERT INTO bill_detail VALUES (8, 'Repuesto', 'Compra paragolpe frontal', 'Demorado', 1, 'Error cambio repuesto defectuoso', 4, 3);
INSERT INTO bill_detail VALUES (9, 'Repuesto', 'Compra volante', 'Demorado', 1, 'Error logistico', 5, 4);
INSERT INTO bill_detail VALUES (10, 'Repuesto', 'Compra volante', 'Demorado', 1, 'Error logistico', 6, 4);