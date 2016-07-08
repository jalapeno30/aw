package com.ilts.anywhere.coreplatform.utility;

public class CPUtility {
	
	public static String drawDayToString(DrawDay day) {
		String dayString = "";
		
		switch(day) {
			case NEXT:
				dayString = "Next";
				break;
			case SUN: 
				dayString = "Sun";
				break;
			case MON:
				dayString = "Mon";
				break;
			case TUE:
				dayString = "Tue";
				break;
			case WED:
				dayString = "Wed";
				break;
			case THU:
				dayString = "Thu";
				break;
			case FRI:
				dayString = "Fri";
				break;
			case SAT:
				dayString = "Sat";
				break;
		}
		
		return dayString;
	}
	
	public static String selectionTypeToString(SelectionType type) {
		String selectionString = "";
		
		switch(type) {
			case QUICK_PICK:
				selectionString = "QP";
				break;
			case SYSTEM:
				selectionString = "LottoSystem";
				break;
			case STRAIGHT:
				selectionString = "Straight";
				break;
			case PARTIAL_QUICK_PICK:
				selectionString = "Partial";
				break;
		}
		
		return selectionString;
	}
	
}
