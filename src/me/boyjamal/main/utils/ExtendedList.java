package me.boyjamal.main.utils;

import java.util.ArrayList;

public class ExtendedList<E> extends ArrayList<E> {
	
	   public ExtendedList(E... autoAdds) {
	      Object[] var5 = autoAdds;
	      int var4 = autoAdds.length;

	      for(int var3 = 0; var3 < var4; ++var3) {
	         E autoAdd = (E) var5[var3];
	         this.add(autoAdd);
	      }

	   }
	}
