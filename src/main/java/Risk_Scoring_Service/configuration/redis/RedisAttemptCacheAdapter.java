package Risk_Scoring_Service.configuration.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import Risk_Scoring_Service.domain.port.AttemptCachePort;

@Component
public class RedisAttemptCacheAdapter implements AttemptCachePort {

    private static final Duration TTL = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    public RedisAttemptCacheAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public int incrementAttempts(String customerId) {

        Long value = redisTemplate.opsForValue()
                .increment(buildKey(customerId));

        redisTemplate.expire(buildKey(customerId), TTL);

        return value == null ? 0 : value.intValue();
    }

    @Override
    public int getAttempts(String customerId) {

        String value =
                redisTemplate.opsForValue().get(buildKey(customerId));

        return value == null ? 0 : Integer.parseInt(value);
    }

    private String buildKey(String customerId) {
        return "velocity:" + customerId;
    }

}
