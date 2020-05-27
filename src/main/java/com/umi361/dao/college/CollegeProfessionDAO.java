package com.umi361.dao.college;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class CollegeProfessionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @return 键为学院的 id 与 名字，值为对应专业的 id 与 名字
     */
    public Map<Pair<Integer, String>, List<Pair<Integer, String>>> getProfessionCollegePairListMap() {
        List<Pair<Integer, String>> collegeList = new LinkedList<>(), professionList = new LinkedList<>();
        jdbcTemplate.query("SELECT college_list.college_id, college_name, profession_id, profession_name " +
                "FROM profession_list INNER JOIN college_list" +
                " ON profession_list.college_id = college_list.college_id ORDER BY profession_id", (rs) -> {
            collegeList.add(new Pair<>(rs.getInt(1), rs.getString(2)));
            professionList.add(new Pair<>(rs.getInt(3), rs.getString(4)));
        });
        Map<Pair<Integer, String>, List<Pair<Integer, String>>> ret = new LinkedHashMap<>();
        Pair<Integer, String> lastCollegePair = new Pair<>(-1, "");
        int i = 0;
        for (Pair<Integer, String> currCollegePair : collegeList) {
            if(!lastCollegePair.equals(currCollegePair)) {
                LinkedList<Pair<Integer, String>> valueList = new LinkedList<>();
                valueList.add(professionList.get(i));
                ret.put(currCollegePair, valueList);
                lastCollegePair = currCollegePair;
            } else {
                ret.get(currCollegePair).add(professionList.get(i));
            }
            i++;
        }
        return ret;
    }

}
