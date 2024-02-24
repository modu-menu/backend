package modu.menu.vibe.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VibeType {

    NOISY("시끌벅적해요"),
    TRENDY("트렌디해요"),
    GOOD_SERVICE("서비스가 좋아요"),
    QUIET("조용해요"),
    MODERN("모던해요"),
    NICE_VIEW("뷰맛집이에요");

    private final String title;

    @JsonValue
    public String getTitle() {
        return title;
    }
}
