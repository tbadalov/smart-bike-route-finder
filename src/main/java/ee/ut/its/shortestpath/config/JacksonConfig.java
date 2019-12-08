package ee.ut.its.shortestpath.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ee.ut.its.shortestpath.path.DirectionSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper jsonObjectMapper() {
        ArrayList<Module> modules = new ArrayList<>();

        //CollectionType Serialization
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new DirectionSerializer());

        return Jackson2ObjectMapperBuilder.json()
                .modulesToInstall(simpleModule)
                .build();
    }
}