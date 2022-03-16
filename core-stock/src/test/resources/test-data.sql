INSERT INTO SHOP(id, name, max_stock)
VALUES (1, 'MAIN_SHOP', 30);

INSERT INTO SHOE(id, name, color, size)
VALUES (1, 'Air Jordans XXX', 'PINK', 40);

INSERT INTO SHOE(id, name, color, size)
VALUES (2, 'Air Jordans XXX', 'PINK', 41);

INSERT INTO SHOE(id, name, color, size)
VALUES (3, 'Air Jordans YYY', 'BLUE', 40);

INSERT INTO INVENTORY(shoe_id, shop_id, quantity)
VALUES (1, 1, 10);

INSERT INTO INVENTORY(shoe_id, shop_id, quantity)
VALUES (2, 1, 0);

INSERT INTO INVENTORY(shoe_id, shop_id, quantity)
VALUES (3, 1, 10);