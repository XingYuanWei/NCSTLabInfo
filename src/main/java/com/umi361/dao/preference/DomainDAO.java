package com.umi361.dao.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javafx.util.Pair;
import java.util.*;

@Repository
public class DomainDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<Pair<Integer, String>, List<Pair<Integer, String>>> getDomainInterestPairListMap() {
        List<Pair<Integer, String>> domainList = new LinkedList<>(), interestList = new LinkedList<>();
        jdbcTemplate.query("SELECT club_domain_list.club_domain_id, club_domain_name, interest_id, interest_name " +
                "FROM club_domain_list INNER JOIN interest_list " +
                "ON club_domain_list.club_domain_id = interest_list.club_domain_id ORDER BY club_domain_list.club_domain_id;", (rs) -> {
            domainList.add(new Pair<>(rs.getInt(1), rs.getString(2)));
            interestList.add(new Pair<>(rs.getInt(3), rs.getString(4)));
        });
        Map<Pair<Integer, String>, List<Pair<Integer, String>>> ret = new LinkedHashMap<>();
        Pair<Integer, String> lastDomainPair = new Pair<>(-1, "");
        int i = 0;
        for (Pair<Integer, String> currDomainPair : domainList) {
            if(!lastDomainPair.equals(currDomainPair)) {
                LinkedList<Pair<Integer, String>> valueList = new LinkedList<>();
                valueList.add(interestList.get(i));
                ret.put(currDomainPair, valueList);
                lastDomainPair = currDomainPair;
            } else {
                ret.get(currDomainPair).add(interestList.get(i));
            }
            i++;
        }
        return ret;
    }

}
