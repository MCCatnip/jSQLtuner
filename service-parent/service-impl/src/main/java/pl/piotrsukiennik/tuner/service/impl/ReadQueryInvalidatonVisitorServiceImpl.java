package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.ReadQueryInvalidatonVisitorService;
import pl.piotrsukiennik.tuner.querytree.ReadQueryTree;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:04
 */
@Service
class ReadQueryInvalidatonVisitorServiceImpl implements ReadQueryInvalidatonVisitorService {

    @Autowired
    private ReadQueryTree<ReadQuery> readQueryTree;

    @Override
    public void putCachedQuery( ReadQuery query ) {
        readQueryTree.putQuery( query );
    }

    @Override
    public Collection<ReadQuery> visit( InsertQuery insertQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( insertQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( DeleteQuery deleteQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( deleteQuery.getTableSource().getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( UpdateQuery updateQuery ) {
        Collection<ReadQuery> queriesToInvalidate = new LinkedHashSet<ReadQuery>();
        if ( updateQuery.getColumnValues() != null ) {
            Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
            for ( ColumnValue columnValue : updateQuery.getColumnValues() ) {
                tablesToStarInvalidate.add( columnValue.getColumn().getTable() );
                queriesToInvalidate.addAll( readQueryTree.getQueriesInvalidatedBy( columnValue.getColumn() ) );
            }
            for ( Table table : tablesToStarInvalidate ) {
                queriesToInvalidate.addAll( readQueryTree.getQueriesInvalidatedByStar( table ) );
            }
            return queriesToInvalidate;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( AlterTableQuery alterTableQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( alterTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( CreateTableQuery createTableQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( createTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( TruncateQuery truncateQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( truncateQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( DropTableQuery dropTableQuery ) {
        return readQueryTree.getQueriesInvalidatedBy( dropTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( SelectQuery selectQuery ) {
        return Collections.EMPTY_LIST;
    }


    @Override
    public Collection<ReadQuery> visit( CreateViewQuery createViewQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( CreateIndexQuery createIndexQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( CallQuery callQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( GenericDropQuery genericDropQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( ReplaceQuery replaceQuery ) {
        return Collections.EMPTY_LIST;
    }

}