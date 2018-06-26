package com.reaktorlabs.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Viki
 */
@FacesConverter("stringLocaldateConverter")
public class StringLocaldateConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(value, formatter);
        
        return date;
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LocalDate date = (LocalDate) value;
        String stringMonth = "";
        String stringDay = "";

        Integer year = date.getYear();
        Integer month = date.getMonthValue();
        Integer dayOfMonth = date.getDayOfMonth();
        
        if(month < 10) {
            stringMonth = "0" + month;
        } else {
            stringMonth = String.valueOf(month);
        }
        
        if(dayOfMonth < 10) {
            stringDay = "0" + dayOfMonth;
        } else {
            stringDay = String.valueOf(dayOfMonth);
        }

        return year+"-"+stringMonth+"-"+stringDay;
    }

}

