package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.other.Values;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.source.Source;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface QueryDao extends CrudDao {
    <T extends Query> T get( String hash );

    <T extends Query> T get( Class<? extends Query> clazz, String hash );

    <T extends Query> T submit( T query );

    void submit( OperatorExpression expression );

    void submit( ColumnValue columnValue );


    GroupByFragment getGroupByFragment( Expression element, int position );

    void submit( GroupByFragment groupByFragment );

    void submit( OrderByFragment orderByFragment );

    void submit( Values values );

    void submit( Projection projection );

    void submit( Source source );


}
