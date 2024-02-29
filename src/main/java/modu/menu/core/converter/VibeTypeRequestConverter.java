package modu.menu.core.converter;

import modu.menu.vibe.domain.VibeType;
import org.springframework.core.convert.converter.Converter;

public class VibeTypeRequestConverter implements Converter<String, VibeType> {

    @Override
    public VibeType convert(String source) {
        return VibeType.valueOf(source.toUpperCase());
    }
}
