package com.example.board.boardservice.log;

import lombok.Getter;
@Getter
public class Counter {
    private Long count;
    private Long time;

    public Counter(Long count, Long time) {
        this.count = count;
        this.time = time;
    }

    public void increaseCount() {
        count++;
    }
}
