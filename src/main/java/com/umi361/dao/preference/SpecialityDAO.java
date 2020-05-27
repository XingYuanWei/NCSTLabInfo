package com.umi361.dao.preference;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class SpecialityDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Pair<Integer, String>> getSpecialityPairsList() {
        LinkedList<Pair<Integer, String>> ret = new LinkedList<>();
        jdbcTemplate.query("SELECT speciality_id, speciality_name FROM speciality_list ORDER BY speciality_id", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                ret.add(new Pair<>(resultSet.getInt(1), resultSet.getString(2)));
            }
        });
        return ret;
    }

}
