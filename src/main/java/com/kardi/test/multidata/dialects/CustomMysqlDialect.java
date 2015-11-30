package com.kardi.test.multidata.dialects;

import org.hibernate.dialect.MySQL57InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.mapping.Column;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class CustomMysqlDialect extends MySQL57InnoDBDialect {
    public CustomMysqlDialect() {
        super();

        registerColumnType(Types.CLOB, "longtext");
        registerColumnType(Types.VARCHAR, "longtext");
        registerColumnType(Types.VARCHAR, 16777215, "mediumtext");
        registerColumnType(Types.VARCHAR, 65535, "text");
        // by default
        registerColumnType(Types.VARCHAR, Column.DEFAULT_LENGTH, "mediumtext");
        // MySql has some advantages, when it works with limited size
        // so, if size is specified and it's less than 255, we use nvarchar
        registerColumnType(Types.VARCHAR, 254, "nvarchar($l)");

        registerColumnType(Types.BLOB, "longblob");
        registerColumnType(Types.VARBINARY, "longblob");
        registerColumnType(Types.VARBINARY, Column.DEFAULT_LENGTH, "mediumblob");
        registerColumnType(Types.VARBINARY, 254, "tinyblob");

        // register a special function to perform a date operation
        registerFunction("addmonths", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "DATE_ADD(?1, INTERVAL ?2 MONTH)"));
    }
}
