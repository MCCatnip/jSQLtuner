package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.dto.QueryComplexityEstimation;
import pl.piotrsukiennik.tuner.datasource.RecommendationContext;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationService;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Service
public class DataSourceRecommendationServiceImpl<RQ extends ReadQuery> implements DataSourceRecommendationService<RQ> {

    private static final Log LOG = LogFactory.getLog( DataSourceRecommendationServiceImpl.class );

    private final static double REQUIRED_CUMULATIVE_PROBABILITY = 0.1;

    @Override
    public <DS extends DataSourceIdentity> Collection<DS> possible( RecommendationContext<RQ,DS> context ){
        if (isShardable( context )){
            return context.getNodes();
        }
        return Collections.EMPTY_LIST;
    }


    public <DS extends DataSourceIdentity> boolean isShardable( RecommendationContext<RQ,DS> context ){
        ContinuousDistribution distribution = context.getQueryDistributions().get( QueryComplexityEstimation.Type.EXECUTION_COMPLEXITY );
        try {
            double cumulativeProbability = distribution.cumulativeProbability(
             context.getQueryComplexityEstimation().getExecutionComplexity()
            );
            if ( REQUIRED_CUMULATIVE_PROBABILITY <cumulativeProbability){
                return true;
            }
        }
        catch ( MathException e ) {
            LOG.error( e );
        }
        return false;
    }
}