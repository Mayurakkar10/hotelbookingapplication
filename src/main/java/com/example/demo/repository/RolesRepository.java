package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RolesModel;


@Repository
public class RolesRepository {
	
	List<RolesModel> list;
	@Autowired
	private JdbcTemplate template;
	public List<RolesModel> getAllRoles() {
		list = template.query("select * from roles",new RowMapper<RolesModel>() {

			@Override
			public RolesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				RolesModel model = new RolesModel();
				model.setRole_id(rs.getInt(1));
				model.setRole_name(rs.getString(2));
				
				return  model;
			}
    		
    	});
    	return list;
	}

}
