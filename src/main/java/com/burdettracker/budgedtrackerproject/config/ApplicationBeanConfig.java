package com.burdettracker.budgedtrackerproject.config;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.google.gson.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@Configuration
public class ApplicationBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

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
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    return parse;
                }
                return null;
            }
        });

        modelMapper.typeMap(RegisterUserDTO.class, User.class).addMappings(mp -> {
            mp.skip(User::setPassword);
        });
        modelMapper.typeMap(ExpenseDTO.class, Expense.class).addMappings(mp -> {
            mp.skip(Expense::setDateDue);
        });
        return modelMapper;
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
  }
