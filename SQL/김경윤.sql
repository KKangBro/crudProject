----------------------------------------------------------

create sequence sep_menu_id start with 1;
create sequence sep_order_id start with 2023001;

create table admin (
    admin_id     varchar2(20) primary key,
    admin_pw     varchar2(20) not null
);

create table customer (
    customer_id     varchar2(20) primary key,
    customer_pw     varchar2(20) not null,
    customer_name   varchar2(20) not null,
    birth           number not null,
    phone_number    varchar2(20)
);

create table menu (
    menu_id     number primary key,
    menu_name   varchar2(50) not null,
    price       number not null,
    stock       number default 20,
    sales       number default 0
);

create table orders (
    order_id    number primary key,
    customer_id varchar2(20),
    menu_id     number,
    quantity    number default 1,
    overall     number not null,
    
    constraint fk_customer_id foreign key(customer_id) references customer(customer_id) on delete set null,
    constraint fk_menu_id foreign key(menu_id) references menu(menu_id) on delete set null
);

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

commit;
----------------------------------------------------------


insert into admin values('admin', 'admin');

select * from admin;


insert into customer values('gpark', 'gpark123', '박명수', 700927, '010-2837-4576');
insert into customer values('yjs', 'yjs123', '유재석', 720814, '010-8463-9226');
insert into customer values('jjh', 'jjh123', '정준하', 710318, '010-1284-3287');
insert into customer values('jhd', 'jhd123', '정형돈', 780315, '010-2198-7354');
insert into customer values('nhc', 'nhc123', '노홍철', 790331, '010-4381-4287');
insert into customer values('hdh', 'hdh123', '하동훈', 790820, '010-9823-2172');

select * from customer;


insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '아메리카노', 5000);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '카페라떼', 5800);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '바닐라라떼', 6300);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '모카라떼', 6300);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '헤이즐넛라떼', 6800);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '캐러멜마키아또', 6800);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '콜드브루', 5200);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '콜드브루 라떼', 6000);
insert into menu values(sep_menu_id.nextval, '베이글', 3900, 0, 10);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '스콘', 3900);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '에그타르트', 3300);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '초코쿠키', 2000);
insert into menu(menu_id, menu_name, price) values(sep_menu_id.nextval, '모카쿠키', 2000);

select * from menu;


insert into orders values(sep_order_id.nextval, 'gpark', 2, 2, 5800*2);
insert into orders values(sep_order_id.nextval, 'yjs', 5, 1, 6800);
insert into orders values(sep_order_id.nextval, 'gpark', 11, 3, 3300*3);
insert into orders values(sep_order_id.nextval, 'nhc', 6, 2, 6800*2);
insert into orders values(sep_order_id.nextval, 'jjh', 1, 2, 5000*2);
insert into orders values(sep_order_id.nextval, 'jhd', 8, 1, 6000);
insert into orders values(sep_order_id.nextval, 'yjs', 2, 3, 5800*3);
insert into orders values(sep_order_id.nextval, 'jhd', 10, 1, 3900);
insert into orders values(sep_order_id.nextval, 'gpark', 3, 1, 6300);
insert into orders values(sep_order_id.nextval, 'nhc', 12, 2, 2000*2);
insert into orders values(sep_order_id.nextval, 'jjh', 11, 2, 3300*2);
insert into orders values(sep_order_id.nextval, 'gpark', 4, 4, 6300*2);

select * from orders;


commit;
----------------------------------------------------------