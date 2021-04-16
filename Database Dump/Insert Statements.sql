-- Insert Statements Products
INSERT INTO products(product_id, name, msrp, price) VALUES (628352190, "AMD Ryzen 7 5800X", 329.53, 399.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (192637485, "CableMod PCIe Power Cable", 20.11, 24.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (153852934, "CableMod ATX Power Cable", 45.82, 64.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (643826261, "AMD Ryzen 5 5600X", 245.34, 299.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (632742987, "NVIDIA RTX 3080 Founders Edition", 682.25, 799.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (729834278, "NVIDIA RTX 3070 Founders Edition", 486.62, 649.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (512357126, "Noctua 4-pin Fan Connection Kit", 9.34, 19.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (127369128, "USB-A to USB-A Cable", 2.86, 3.50);
INSERT INTO products(product_id, name, msrp, price) VALUES (127835219, "Xbox Series X Controller", 54.26, 79.99);
INSERT INTO products(product_id, name, msrp, price) VALUES (536271623, "Razer Basilisk Optical Mouse", 86.90, 99.99);


-- Insert Statments Users
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (1, "someUser", "somePassword", "John", "Doe", "Customer");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (2, "scasi", "imNotSharing", "Shane", "Casiano", "IT");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (3, "jdodd", "another", "Joseph", "Dodd", "Manager");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (4, "jspah", "password", "Jade", "Spahr", "Manager");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (5, "dhedd", "entry", "Duncan", "Hedden", "Admin");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (6, "dholc", "this", "Dakarai", "Holcomb", "Account Manager");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (7, "jane_doe", "should", "Jane", "Doe", "User");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (8, "serpent", "be", "Sarah", "Smith", "Customer");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (9, "jvdover", "md5", "Joep", "Van Den Overecht", "Customer");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (10, "bakednorker", "encrypted", "Bickolas", "Norker", "Customer");
INSERT INTO users(user_id, username, password, fname, lname, role) VALUES (11, "jmill", "nopassword", "Josh", "Miller", "Account Worker");


-- Insert Statements Employees
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (000000000, 2, "Shane", "Casiano", 23.45, "IT", '2020-12-01', NULL);
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (111111111, 3, "Joseph", "Dodd", 25.67, "Manager", '2020-12-01', NULL);
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (222222222, 4, "Jade", "Spahr", 25.42, "Manager", '2020-12-01', NULL);
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (333333333, 5, "Duncan", "Hedden", 31.32, "Admin", '2020-12-01', '2020-12-01');
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (444444444, 6, "Dakarai", "Holcomb", 21.76, "Account Manager", '2020-12-01', NULL); 
INSERT INTO employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) VALUES (555555555, 11, "Josh", "Miller", 21.76, "Account Worker", '2020-12-01', '2020-12-05'); 


-- Insert Statements Orders
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (101010101, "Jane", "Doe", "123, Somplace Rd. SomeTown, USA", '2020-12-10', 111111111);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (202020202, "John", "Doe", "001, Somplace Ln. SomeTown, USA", '2020-12-15', 111111111);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (303030303, "Sarah", "Smith", "234, Somplace Blvd. SomeTown, USA", '2020-12-20', 222222222);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (404040404, "Shane", "Casiano", "654, Somplace Pkwy. SomeTown, USA", '2020-12-25', 111111111);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (505050505, "Dustin", "Farringdon", "342, Somplace Ct. SomeTown, USA", '2020-12-30', 222222222);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (606060606, "Joep", "Van Den Overecht", "345, Somplace St. SomeTown, USA", '2021-01-10', 111111111);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (707070707, "James", "Kim", "743, Somplace Tr. SomeTown, USA", '2021-01-15', 222222222);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (808080808, "Boris", "Conner", "654, Somplace Ave. SomeTown, USA", '2021-01-20', 222222222);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (909090909, "Jacob", "Ravensburger", "645, Somplace Way. SomeTown, USA", '2021-01-25', 222222222);
INSERT INTO orders(order_id, customer_fn, customer_ln, customer_add, date_placed, employee_no) VALUES (100010001, "Jessica", "Remmington", "238, Somplace Cir. SomeTown, USA", '2021-01-30', 111111111);


-- Insert Statements current_stock
-- THIS IS AUTO POPULATED BASED ON INCOMING/OUTGOING


-- Insert Statements Incoming_Goods
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (628352190, '2020-12-01', "AS3JSA378EWASH", 100, 111111111);
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (192637485, '2020-12-05', "1Z6789SADALKSDJH", 100, 222222222);
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (153852934, '2020-12-09', "900344465729992834702", 100, 111111111);
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (643826261, '2020-12-12', "1ZHJKWSLDKJASY33", 100, 222222222);
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (632742987, '2020-12-15', "DHAKSJD78231Q2", 100, 111111111);
INSERT INTO incoming_goods(product_id, date_in, track_no, quantity, employee_no) VALUES (729834278, '2020-05-10', "1Z0X987U3QPOWIQERU", 100, 222222222);


-- Insert Statements Outgoing_Goods
INSERT INTO outgoing_goods(product_id, date_go, quantity, employee_no) VALUES (628352190, '2020-02-01', 7, 111111111);
INSERT INTO outgoing_goods(product_id, date_go, quantity, employee_no) VALUES (192637485, '2020-02-01', 5, 222222222);
INSERT INTO outgoing_goods(product_id, date_go, quantity, employee_no) VALUES (153852934, '2020-02-01', 2, 111111111);
INSERT INTO outgoing_goods(product_id, date_go, quantity, employee_no) VALUES (643826261, '2020-02-01', 1, 222222222);
INSERT INTO outgoing_goods(product_id, date_go, quantity, employee_no) VALUES (632742987, '2020-02-01', 5, 111111111);


-- Insert Statements Order_Items
INSERT INTO order_items(order_id, product_id) VALUES (101010101, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (101010101, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (101010101, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (202020202, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (202020202, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (303030303, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (404040404, 628352190);
INSERT INTO order_items(order_id, product_id) VALUES (404040404, 192637485);
INSERT INTO order_items(order_id, product_id) VALUES (505050505, 192637485);
INSERT INTO order_items(order_id, product_id) VALUES (505050505, 192637485);
INSERT INTO order_items(order_id, product_id) VALUES (606060606, 192637485);
INSERT INTO order_items(order_id, product_id) VALUES (707070707, 192637485);
INSERT INTO order_items(order_id, product_id) VALUES (707070707, 153852934);
INSERT INTO order_items(order_id, product_id) VALUES (707070707, 153852934);
INSERT INTO order_items(order_id, product_id) VALUES (808080808, 643826261);
INSERT INTO order_items(order_id, product_id) VALUES (808080808, 632742987);
INSERT INTO order_items(order_id, product_id) VALUES (808080808, 632742987);
INSERT INTO order_items(order_id, product_id) VALUES (909090909, 632742987);
INSERT INTO order_items(order_id, product_id) VALUES (909090909, 632742987);
INSERT INTO order_items(order_id, product_id) VALUES (100010001, 632742987);


-- Insert Statements Tracking
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (101010101, "Delivered", "JAYS7Q6W894G3JH", "UPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (202020202, "Delivered", "JSKLHADH783242", "UPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (303030303, "Delivered", "ASDJHFGWEE467298", "UPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (404040404, "Shipped", "ASLKKDJG84756R534E", "USPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (505050505, "Shipped", "SDAKFHGC8ZX&DS6R", "USPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (606060606, "Shipped", "32487EDOASJHDAJLKSDF", "USPS");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (707070707, "Not Shipped", "1Z7983024EWDHAKSJDHL", "FedEx");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (808080808, "Not Shipped", "900221736726357821364", "FedEx");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (909090909, "Not Shipped", "JAYS7Q6W894G3JH", "FedEx");
INSERT INTO tracking(order_id, shipping_status, tracking_id, carrier) VALUES (100010001, "Not Shipped", "JHSAGD76123TP4EH", "FedEx");

-- Insert Statements Active_Invoice
INSERT INTO active_invoice(order_id, date_processed, total_charge, outstanding_balance) VALUES (707070707, '2021-01-15', 349.99, 349.99);
INSERT INTO active_invoice(order_id, date_processed, total_charge, outstanding_balance) VALUES (808080808, '2021-01-20', 499.99, 349.99);
INSERT INTO active_invoice(order_id, date_processed, total_charge, outstanding_balance) VALUES (909090909, '2021-01-25', 499.99, 449.99);
INSERT INTO active_invoice(order_id, date_processed, total_charge, outstanding_balance) VALUES (100010001, '2021-01-30', 549.99, 549.99);


-- Insert Statements Invoice_History
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (101010101, '2020-12-10', 12.99);
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (202020202, '2021-12-15', 599.99);
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (303030303, '2021-12-20', 399.99);
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (404040404, '2021-12-25', 149.99);
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (505050505, '2020-12-30', 249.99);
INSERT INTO invoice_history(order_id, date_processed, total_charge) VALUES (606060606, '2021-01-10', 199.99);


-- Insert Statements Budget
INSERT INTO budget(date_start, date_end, outgoing, income, employee_no) VALUES ('2020-12-01', '2020-12-31', 1234.23, 6221.34, 444444444);
INSERT INTO budget(date_start, date_end, outgoing, income, employee_no) VALUES ('2020-01-01', '2020-01-31', 3212.56, 2332.75, 444444444);
INSERT INTO budget(date_start, date_end, outgoing, income, employee_no) VALUES ('2020-02-01', '2020-02-28', 2311.34, 3523.83, 444444444);