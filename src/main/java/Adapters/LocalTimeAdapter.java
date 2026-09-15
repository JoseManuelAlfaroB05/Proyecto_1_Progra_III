package Adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String hora) {
        if (hora == null || hora.trim().isEmpty()) {
            return null;
        }

        return LocalTime.parse(hora);
    }

    @Override
    public String marshal(LocalTime hora) {
        if (hora == null) {
            return null;
        }

        return hora.toString();
    }
}