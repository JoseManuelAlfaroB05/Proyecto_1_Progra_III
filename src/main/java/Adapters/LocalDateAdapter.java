package Adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(fecha);
    }

    @Override
    public String marshal(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }

        return fecha.toString();
    }
}