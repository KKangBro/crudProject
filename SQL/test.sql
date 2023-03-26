select customer_pw
from customer
where customer_id = 'cust01';

select * from menu
order by menu_name;

select *
from orders
where customer_id = 'cust01';



show user;

commit;

select * from customer;

insert into customer values('11', '11', '¹Ú±æµ¿', 970303, 010-3333-3333);

rollback;


select o.order_id, m.menu_name, o.quantity, o.overall
from orders o join menu m using(menu_id)
where customer_id = 'c1'
order by 1 desc;


select * from orders;


insert into orders values(sep_order_id.nextval, 'c2', 3);

delete from customer
where customer_id = 'c2';


select admin_pw
from admin
where admin_id = 'admin';


insert into menu(menu_id, menu_name, price)
values(sep_menu_id.nextval, 'Ä«¶ó¸á ¸¶³¢¾ß¶Ç', 4000);

select * from menu;

delete from menu
where menu_id = 4;

select price
from menu
where menu_id = 2;


select * from orders;

select overall
from orders;


select o.order_id, o.customer_id, m.menu_name, o.quantity, o.overall
from orders o join menu m using(menu_id)
order by 1 desc;


select customer_id from customer;


select o.order_id, nvl(o.customer_id, 'Å»ÅðÇÑ È¸¿ø') customer_id, m.menu_name, o.quantity, o.overall
from orders o join menu m using(menu_id)
order by 1 desc;







































