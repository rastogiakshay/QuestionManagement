package com.qm.POJO;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class PageDetails {
Optional<Integer> page;

public Optional<Integer> getPage() {
	return page;
}

public void setPage(Optional<Integer> page) {
	this.page = page;
}
}
