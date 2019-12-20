alter table community_line change community_line_id c_l_i bigint(20);
alter table community_line change api_key a_k binary(16);
alter table community_line change name n varchar(255);
alter table community_line change value v float;
alter table community_line change community_partner_id c_p_i bigint(20);
alter table community_line change context_source_id c_s_i bigint(20);
alter table community_line change cv_id c_i bigint(20); 
alter table community_line change param_id p_i bigint(20); 
alter table community_line change sample_type_id s_t_i bigint(20);
alter table community_line change trace_color_id t_c_i bigint(20); 
alter table community_line change alias a varchar(255);

alter table community_partner change community_partner_id c_p_i bigint(20);
alter table community_partner change email e varchar(255);
alter table community_partner change logo l varchar(255);
alter table community_partner change name n varchar(255);
alter table community_partner change web_page w_p varchar(255);

alter table community_line_node change community_line_node_id c_l_n_i bigint(20);
alter table community_line_node change active a bit(1);
alter table community_line_node change community_line_id c_l_i bigint(20);
alter table community_line_node change id i bigint(20);

rename table community_line to c_l
rename table community_partner to c_p
rename table community_line_node to c_l_n
rename table community_line_seq to c_l_s
rename table community_partner_seq to c_p_s
