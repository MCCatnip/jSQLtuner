package pl.piotrsukiennik.parser.impl.statement;

import net.sf.jsqlparser.statement.replace.Replace;
import pl.piotrsukiennik.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.model.query.impl.ReplaceQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class ReplaceStatementParser extends StatementParser<ReplaceQuery> {
    public ReplaceStatementParser( QueryParsingContext parsingContext, Replace replace ) {
        super( parsingContext, replace, new ReplaceQuery() );
    }

    @Override
    public void visit( Replace replace ) {
        query.setTable( parsingContext.getTable( replace.getTable() ) );
        super.visit( replace );
    }


}