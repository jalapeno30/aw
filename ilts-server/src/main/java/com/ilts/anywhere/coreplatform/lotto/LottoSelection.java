package com.ilts.anywhere.coreplatform.lotto;

import java.util.List;

public class LottoSelection {
	private List<String> types;
	private List<Integer> numbers;
	private Integer bonusNumber;
	
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public List<Integer> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	public Integer getBonusNumber() {
		return bonusNumber;
	}
	public void setBonusNumber(Integer bonusNumber) {
		this.bonusNumber = bonusNumber;
	}
}
