package modu.menu.core.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> list;

    private long totalElements;

    private int totalPages;

    private int curPage;

    private Boolean first;

    private Boolean last;

    private Boolean empty;

    public PageResponse(List<T> list, Page<T> page) {
        this.list = list;
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.curPage = page.getNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }

    /* 제네릭 타입의 경우, 컴파일러는 메서드의 시그니처에서 제네릭 매개변수를 지우고 검사하므로
    두 개의 생성자가 동일한 시그니처를 가지게 된다.
    추가적인 K 타입 처리를 위해 type 매개변수 추가
    */
    public <K> PageResponse(List<T> list, Page<K> page, Class<K> type) {
        this.list = list;
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.curPage = page.getNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }
}