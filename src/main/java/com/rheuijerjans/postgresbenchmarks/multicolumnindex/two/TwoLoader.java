package com.rheuijerjans.postgresbenchmarks.multicolumnindex.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TwoLoader {

    private static final Logger log = LoggerFactory.getLogger(TwoLoader.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private long totalInserted = 0L; // this is a global variable.

    public TwoLoader(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void createData() {
        log.info("starting data creator");
        final Random random = new Random();

        final List<TwoColumn> twoColumns = new ArrayList<>();

        final int batchSize = 500_000;

        for (long i = 0; i < batchSize * 10; i++) {

            final TwoColumn twoColumn = new TwoColumn(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    random.nextInt(100));

            twoColumns.add(twoColumn);

            if (twoColumns.size() == batchSize) {
                saveTwoColumns(twoColumns);
            }
        }
    }

    private void saveTwoColumns(List<TwoColumn> twoColumns) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(twoColumns);

        log.info("Start insert");
        int[] updateCount = namedParameterJdbcTemplate.batchUpdate(
                "INSERT INTO two_column (one, two, perc)" +
                        " VALUES (:one, :two, :perc)",
                batch);

        log.info("Inserted {} records.", Arrays.stream(updateCount).sum());
        totalInserted += twoColumns.size();
        log.info("Total Inserted {}.", totalInserted);
        twoColumns.clear();
    }

}
