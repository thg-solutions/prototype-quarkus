alter table person 
add constraint fk_address foreign key (address_id) references address(id);
