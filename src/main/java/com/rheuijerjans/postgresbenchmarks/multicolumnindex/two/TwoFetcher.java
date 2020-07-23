package com.rheuijerjans.postgresbenchmarks.multicolumnindex.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

@Component
public class TwoFetcher {

    private static final Logger log = LoggerFactory.getLogger(TwoFetcher.class);

    private final JdbcTemplate jdbcTemplate;

    public TwoFetcher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fetch() {

        final List<TwoColumn> twoColumns = jdbcTemplate.query(
                "select one, two from two_column where perc >= 98;",
                (rs, rowNum) -> new TwoColumn(rs.getString("one"),rs.getString("two"), 0));
        fetch(twoColumns);
    }

    private void fetch(List<TwoColumn> value) {
        final StopWatch stopWatch = new StopWatch("Fetch test");

        for (int i = 0; i < 5; i++) {
            stopWatch.start("run-" + i);
            for (TwoColumn twoColumn : value) {

                jdbcTemplate.query("select * from two_column where one = ? and two = ?",
                        ps -> {
                            ps.setString(1, twoColumn.getOne());
                            ps.setString(2, twoColumn.getTwo());
                        },
                        (RowMapper<String>) (rs, rowNum) -> null);
            }
            stopWatch.stop();
        }

        log.info(stopWatch.prettyPrint());
    }
}