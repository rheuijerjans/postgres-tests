package com.rheuijerjans.postgresbenchmarks.multicolumnindex.one;

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
public class OneLoader {

    private static final Logger log = LoggerFactory.getLogger(OneLoader.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private long totalInserted = 0L; // this is a global variable.

    public OneLoader(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void createData() {
        log.info("starting data creator");
        final Random random = new Random();

        final List<OneColumn> oneColumns = new ArrayList<>();

        final int batchSize = 500_000;

        for (long i = 0; i < batchSize * 10; i++) {

            final OneColumn oneColumn = new OneColumn(
                    UUID.randomUUID().toString(),
                    random.nextInt(100));

            oneColumns.add(oneColumn);

            if (oneColumns.size() == batchSize) {
                saveOneColumns(oneColumns);
            }
        }
    }

    private void saveOneColumns(List<OneColumn> oneColumns) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(oneColumns);

        log.info("Start insert");
        int[] updateCount = namedParameterJdbcTemplate.batchUpdate(
                "INSERT INTO one_column (one, perc)" +
                        " VALUES (:one, :perc)",
                batch);

        log.info("Inserted {} records.", Arrays.stream(updateCount).sum());
        totalInserted += oneColumns.size();
        log.info("Total Inserted {}.", totalInserted);
        oneColumns.clear();
    }

}
