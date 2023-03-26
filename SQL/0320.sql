-- admin
create table admin (
    admin_id     varchar2(20) primary key,
    admin_pw     varchar2(20) not null
);

desc admin;
select * from admin;

insert into admin values('admin', 'admin');


-- customer
create table customer (
    customer_id     varchar2(20) primary key,
    customer_pw     varchar2(20) not null,
    customer_name   varchar2(20) not null,
    birth           number not null,
    phone_number    varchar2(20)
);

drop table customer;
delete from customer;

desc customer;
select * from customer;

insert into customer(customer_id, customer_pw, customer_name, birth, phone_number)
values('c1', '11', '김길동', 970101, '010-1111-1111');
insert into customer(customer_id, customer_pw, customer_name, birth, phone_number)
values('c2', '22', '이길동', 970202, '010-2222-2222');
insert into customer(customer_id, customer_pw, customer_name, birth, phone_number)
values('33', '33', '박길동', 970101, '010-1111-1111');


-- menu
create table menu (
    menu_id     number primary key,
    menu_name   varchar2(50) not null,
    price       number not null,
    stock       number default 20,
    sales       number default 0
);

create sequence sep_menu_id start with 1;
drop sequence sep_menu_id;

drop table menu;
delete from menu;

desc menu;
select * from menu;

insert into menu(menu_id, menu_name, price)
values(sep_menu_id.nextval, '에스프레소', 2000);
insert into menu(menu_id, menu_name, price)
values(sep_menu_id.nextval, '아메리카노', 2500);
insert into menu(menu_id, menu_name, price)
values(sep_menu_id.nextval, '카페라떼', 3000);


-- order
create table orders (
    order_id    number primary key,
    customer_id varchar2(20),
    menu_id     number,
    quantity    number default 1,
    overall     number not null,
    
    constraint fk_customer_id foreign key(customer_id) references customer(customer_id) on delete set null,
    constraint fk_menu_id foreign key(menu_id) references menu(menu_id) on delete set null
);

create sequence sep_order_id start with 2023001;
drop sequence sep_order_id;

-- 주문 트리거
create or replace trigger trg_order
    before insert on orders
    for each row
begin
    update menu
    set stock = stock - :new.quantity, sales = sales + :new.quantity
    where menu_id = :new.menu_id;
end;
/

drop table orders;
delete from orders;

desc orders;
select * from orders;

insert into orders values(sep_order_id.nextval, 'c1', 1, 1, 2000);
insert into orders values(sep_order_id.nextval, 'c2', 2, 1, 2500);
insert into orders values(sep_order_id.nextval, 'c1', 3, 1, 3000);
insert into orders values(sep_order_id.nextval, 'c1', 2, 1, 2500);





-----------------------------------------------
-- customer
--    stamp           number default 0,
--    coupon_number   number default 0

-- order
--    quantity    number not null,
--    overall     number not null,

-- coupon
--create table coupon (
--    coupon_id       number,
--    customer_id     varchar2(20),
--    issue_date      ,
--    expire_date     ,
--    discount_rate   ,
--    maximum         ,
--    use status      
--);
--
--select * from coupon;
--
--insert into coupon(customer_id, customer_pw, customer_name, birth, phone_number)
--values('cust01', 'cust01pw', '김길동', 970101, 010-1111-2222);

-----------------------------------------------


















