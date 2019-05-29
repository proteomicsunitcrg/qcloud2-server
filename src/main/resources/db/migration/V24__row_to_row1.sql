ALTER TABLE view_display CHANGE `row` `row1` int(11);
ALTER TABLE message CHANGE `message` `message` varchar(50000);
update param set is_zero_no_data = 1 where id = 2;