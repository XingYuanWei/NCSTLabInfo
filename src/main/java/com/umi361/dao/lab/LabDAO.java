package com.umi361.dao.lab;

import com.umi361.domain.lab.Lab;
import org.icesea.genericDAO.DataSourceProvider.DataSourceInitializer;
import org.icesea.genericDAO.ExtensibleGenericPooledDAO;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class LabDAO extends ExtensibleGenericPooledDAO<Lab> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LabDAO(DataSourceInitializer initializer) { super(initializer); }

    public Map<Pair<Integer, String>, List<Pair<Integer, String>>> getDomainLabPairListMap() {
        Map<Pair<Integer, String>, List<Pair<Integer, String>>> ret = new LinkedHashMap<>();
        List<Pair<Integer, String>> domains = new LinkedList<>(), labs = new LinkedList<>();
        jdbcTemplate.query("SELECT lab_id, lab_name, lab_domain_list.lab_domain_id, lab_domain_name FROM lab_list INNER JOIN lab_domain_list " +
                "ON lab_list.lab_domain_id = lab_domain_list.lab_domain_id WHERE lab_domain_list.lab_domain_id > 0 ORDER BY lab_domain_list.lab_domain_id, lab_id", rs -> {
            labs.add(new Pair<>(rs.getInt(1), rs.getString(2)));
            domains.add(new Pair<>(rs.getInt(3), rs.getString(4)));
        });
        Pair<Integer, String> last_domain_pair = new Pair<>(-1, "");
        for (int i = 0; i < domains.size(); i++) {
            int lab_domain_currId = domains.get(i).getValue0();
            Pair<Integer, String> lab_domain_currPair = new Pair<>(lab_domain_currId, domains.get(i).getValue1());
            if (last_domain_pair.getValue0() != lab_domain_currId) {
                last_domain_pair = lab_domain_currPair;
            }
            if (ret.containsKey(last_domain_pair)) {
                ret.get(last_domain_pair).add(labs.get(i));
            } else {
                List<Pair<Integer, String >> list = new LinkedList<>();
                list.add(labs.get(i));
                ret.put(last_domain_pair, list);
            }
        }
        return ret;
    }

}
