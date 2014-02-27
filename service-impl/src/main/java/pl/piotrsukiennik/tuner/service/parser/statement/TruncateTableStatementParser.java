package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.truncate.Truncate;
import pl.piotrsukiennik.tuner.model.query.impl.TruncateQuery;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class TruncateTableStatementParser extends StatementParser<TruncateQuery> {
    public TruncateTableStatementParser( QueryParsingContext parsingContext, Truncate truncate ) {
        super( parsingContext, truncate, new TruncateQuery() );
    }

    @Override
    public void visit( Truncate truncate ) {
        query.setTable( parsingContext.getTable( truncate.getTable() ) );
    }


}