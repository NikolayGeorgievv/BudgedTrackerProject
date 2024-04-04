package com.burdettracker.budgedtrackerproject.config;

import com.google.gson.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    Gson gson() {

        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new FromDateToJSON())
                .registerTypeAdapter(LocalDateTime.class, new FromJSONToDate())
                .setPrettyPrinting()
                .create();
    }

    private static class FromDateToJSON implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    }

    private static class FromJSONToDate implements JsonDeserializer<LocalDate> {


        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate
                    .parse(json.getAsString(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        }
    }




    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {

                if(mappingContext.getSource() != null) {
                    LocalDate parse = LocalDate
                            .parse(mappingContext.getSource(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    return parse;
                }
                return null;
            }
        });


        return modelMapper;
    }
  }
