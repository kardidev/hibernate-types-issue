package com.kardi.test.multidata;

import com.kardi.test.multidata.config.TestPostgresqlConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPostgresqlConfig.class})
public class PostgresqlTest extends TestBase {
}
