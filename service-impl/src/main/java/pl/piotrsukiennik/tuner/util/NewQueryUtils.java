package pl.piotrsukiennik.tuner.util;

import net.sf.jsqlparser.expression.Alias;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.JoinsAwareQuery;
import pl.piotrsukiennik.tuner.model.query.ProjectionsAwareQuery;
import pl.piotrsukiennik.tuner.model.query.SourcesAwareQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.schema.Index;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.QueryContext;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:43
 */
public class NewQueryUtils {

    private NewQueryUtils() {

    }

    public static String getTableAlias( Alias alias ) {
        return alias == null ? "" : alias.getName();
    }

    public static String getTableSourceValue( net.sf.jsqlparser.schema.Table table ) {
        return table.getWholeTableName() + " " + getTableAlias( table.getAlias() );
    }

    public static Index map( QueryContext queryContext, Table table, net.sf.jsqlparser.statement.create.table.Index index ) {
        return queryContext.getIndex( table, index.getName() );
    }

    public static Table map( QueryContext queryContext, net.sf.jsqlparser.schema.Table table ) {
        return queryContext.getTable( table.getWholeTableName() );
    }

    public static void addProjection( ProjectionsAwareQuery query, Projection projection ) {
        Set<pl.piotrsukiennik.tuner.model.expression.Expression> projections = query.getProjections();
        if ( projections == null ) {
            projections = new LinkedHashSet<pl.piotrsukiennik.tuner.model.expression.Expression>();
            query.setProjections( projections );
        }
        projection.setPosition( projections.size() );
        projections.add( projection );
    }


    public static void addSource( SourcesAwareQuery query, Source source ) {
        Set<Source> sources = query.getSources();
        if ( sources == null ) {
            sources = new LinkedHashSet<Source>();
            query.setSources( sources );
        }
        sources.add( source );
    }


    public static void addJoin( JoinsAwareQuery query, JoinFragment source ) {
        Set<JoinFragment> sources = query.getJoins();
        if ( sources == null ) {
            sources = new LinkedHashSet<JoinFragment>();
            query.setJoins( sources );
        }
        sources.add( source );

    }

    public static void addOrderByFragment( SelectQuery selectQuery, OrderByFragment orderByFragment ) {
        Set<OrderByFragment> sources = selectQuery.getOrders();
        if ( sources == null ) {
            sources = new LinkedHashSet<OrderByFragment>();
            selectQuery.setOrders( sources );
        }
        orderByFragment.setPosition( sources.size() );
        sources.add( orderByFragment );
    }

    public static void addGroupByFragment( SelectQuery selectQuery, Expression expression ) {
        Set<GroupByFragment> sources = selectQuery.getGroups();
        if ( sources == null ) {
            sources = new LinkedHashSet<GroupByFragment>();
            selectQuery.setGroups( sources );
        }
        int position = sources.size();
        sources.add( Dao.getQuery().getGroupByFragment( expression, position ) );
    }
}
