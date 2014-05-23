package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class ReadQueryExecutionResult {

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQuery readQuery;

    private long executionTimeNano;

    private long rows;

    private long rowSize;

    ReadQueryExecutionResult( ReadQuery readQuery,
                                     DataSourceIdentity dataSource,
                                     CachedRowSet resultSet,
                                     long executionTimeNano,
                                     long rows,
                                     long rowSize ) {

        this.readQuery = readQuery;
        this.dataSource = dataSource;
        this.resultSet = resultSet;
        this.executionTimeNano = executionTimeNano;
        this.rows = rows;
        this.rowSize = rowSize;
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    public CachedRowSet getResultSet() {
        return resultSet;
    }

    public long getRowSize() {
        return rowSize;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }

    public long getRows() {
        return rows;
    }

    public DataSourceIdentity getDataSource() {
        return dataSource;
    }

    public void setDataSource( DataSourceIdentity dataSource ) {
        this.dataSource = dataSource;
    }


}