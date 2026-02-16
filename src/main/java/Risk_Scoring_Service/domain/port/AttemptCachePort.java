package Risk_Scoring_Service.domain.port;

public interface AttemptCachePort {

    int incrementAttempts(String customerId);

    int getAttempts(String customerId);


}
