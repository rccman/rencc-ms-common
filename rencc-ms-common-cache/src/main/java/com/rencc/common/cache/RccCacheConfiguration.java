package com.rencc.common.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @Description:
 * @Author: renchaochao
 * @Date: 2020/12/30 17:03
 **/
@Slf4j
@Configuration
public class RccCacheConfiguration {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory( connectionFactory );
        setMySerializer( template );
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 设置序列化方法
     */
    private void setMySerializer(RedisTemplate template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>( Object.class );
        ObjectMapper om = new ObjectMapper();
        om.setVisibility( PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY );
        om.enableDefaultTyping( ObjectMapper.DefaultTyping.NON_FINAL );
        //对于找不到匹配属性的时候忽略报错
        //这里配成false,就是有个别请求参数不对应,也会将请求参数正常解析为User对象;如果配成true,如果遇到请求参数不对应,就会抛异常,返回400.
        om.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        jackson2JsonRedisSerializer.setObjectMapper( om );
        template.setKeySerializer( template.getStringSerializer() );
        template.setValueSerializer( jackson2JsonRedisSerializer );
    }
}
