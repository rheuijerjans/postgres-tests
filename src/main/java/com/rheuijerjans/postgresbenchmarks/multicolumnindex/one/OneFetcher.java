package com.rheuijerjans.postgresbenchmarks.multicolumnindex.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

@Component
public class OneFetcher {

    private static final Logger log = LoggerFactory.getLogger(OneFetcher.class);

    private final JdbcTemplate jdbcTemplate;

    public OneFetcher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fetch() {

        final List<OneColumn> oneColumns = jdbcTemplate.query(
                "select one from one_column where perc >= 98;",
                (rs, rowNum) -> new OneColumn(rs.getString("one"), 0));
        fetch(oneColumns);
    }

    private void fetch(List<OneColumn> value) {
        final StopWatch stopWatch = new StopWatch("Fetch test");

        for (int i = 0; i < 5; i++) {
            stopWatch.start("run-" + i);
            for (OneColumn one : value) {

                jdbcTemplate.query("select * from one_column where one = ?",
                        ps -> ps.setString(1, one.getOne()),
                        (RowMapper<String>) (rs, rowNum) -> null);
            }
            stopWatch.stop();
        }

        log.info(stopWatch.prettyPrint());
    }

}