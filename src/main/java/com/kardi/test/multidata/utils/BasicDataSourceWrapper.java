package com.kardi.test.multidata.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

public class BasicDataSourceWrapper extends BasicDataSource {

    @Override
    public void close() throws SQLException {
        try {
            super.close();
        } finally {
            System.out.println("DataSource has been closed");
        }
    }
}
