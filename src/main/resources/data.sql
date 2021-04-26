
INSERT INTO MAKER VALUES (1, "CHEVROLET");
INSERT INTO MAKER VALUES (2, "FIAT");
INSERT INTO MAKER VALUES (3, "FORD");
INSERT INTO MAKER VALUES (4, "VOLKSWAGEN");
INSERT INTO MAKER VALUES (5, "NISSAN");

INSERT INTO PART VALUES (1, "Farol Chevrolet Spark", 30, 1000, "00000001", 50, 60, 1);
INSERT INTO PART VALUES (2, "Paragolpe trasero Ford Fiesta", 130, 2500, "00000002", 50, 40, 3);
INSERT INTO PART VALUES (3, "Paragolpe frontal Fiat Uno", 130, 2500, "00000003", 50, 40, 3);
INSERT INTO PART VALUES (4, "Volante original de Fiat 127", 60, 1500, "00000003", 50, 40, 3);

INSERT INTO COUNTRY_DEALER VALUES (1, "Argentina", 1, "Argentina Country Dealer");
INSERT INTO COUNTRY_DEALER VALUES (2, "Uruguay", 2, "Uruguay Country Dealer");

-- Subisidiarias de Argentina
INSERT INTO SUBSIDIARY VALUES (1, "Automotriz Alto Palermo BsAs", 1, 1);
INSERT INTO SUBSIDIARY VALUES (2, "Automotriz La Boca BsAs", 2, 1);

-- Subisidiarias de Uruguay
INSERT INTO SUBSIDIARY VALUES (3, "Automotriz Prado Mdeo", 1, 2);
INSERT INTO SUBSIDIARY VALUES (4, "Automotriz Centro Mdeo", 2, 2);

-- Bills de subsidiaria Automotriz Alto Palermo
INSERT INTO BILL VALUES (1, 0, 0, "2021-03-12", "Procesando", "2021-03-10", 1, 1);
INSERT INTO BILL VALUES (2, 0, 5, "2021-03-20", "Demorado", "2021-03-10", 2, 1);

-- Bills de subsidiaria Automotriz Prado Mdeo
INSERT INTO BILL VALUES (3, 0, 2, "2021-02-10", "Demorado", "2021-02-18", 1, 3);
INSERT INTO BILL VALUES (4, 0, 0, "2021-02-22", "Demorado", "2021-02-24", 2, 3);

-- Bill details de Bill 1 y 2 de Automotriz Alto Palermo
INSERT INTO BILL_DETAIL VALUES (1, "Repuesto", "Compra paragolpe frontal", "Normal", 1, "", 1, 3);
INSERT INTO BILL_DETAIL VALUES (4, "Garantia", "Compra volante", "Normal", 2, "", 1, 4);
INSERT INTO BILL_DETAIL VALUES (2, "Repuesto", "Compra paragolpe frontal", "Normal", 2, "", 2, 3);
INSERT INTO BILL_DETAIL VALUES (3, "Repuesto", "Compra faroles", "Demorado", 4, "Error del sistema", 2, 1);



--- create user

INSERT INTO COUNTRY_DEALER VALUES (1, "Argentina",1, "Argentina Country Dealer");
INSERT INTO role VALUES (1, "admin");