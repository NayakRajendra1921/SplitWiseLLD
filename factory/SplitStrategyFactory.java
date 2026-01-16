package factory;

import enums.SplitType;
import strategy.EqualSplitStrategy;
import strategy.PercentageSplitStrategy;
import strategy.SplitStrategy;

public class SplitStrategyFactory {
    public static SplitStrategy getStrategy(SplitType splitType){
        return switch(splitType){
            case EQUAL -> new EqualSplitStrategy();
            case PERCENTAGE -> new PercentageSplitStrategy();
        };
    }
}
