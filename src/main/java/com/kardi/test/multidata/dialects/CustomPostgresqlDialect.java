package com.kardi.test.multidata.dialects;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class CustomPostgresqlDialect extends PostgreSQL94Dialect {
    public CustomPostgresqlDialect() {
        super();
        registerColumnType(Types.VARCHAR, "text");

        // register a special function to perform a date operation
        registerFunction("addmonths", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "?1 + ?2 * '1 month'::interval"));
    }
}
