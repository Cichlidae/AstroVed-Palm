package ru.premaservices.util;

public final class CommonUtil {
	
	public static int count (String s, char c) {
		int r = 0;
		int l = s.length();
		for (int i = 0; i < l; ++i)
			if (s.charAt(i) == c)
				++r;
		return r;
	}

	public static String[] split (String s, char c) {
		if (s == null)
			return null;
		if (s.trim().equals(""))
			return new String[0];
		int n = count(s, c);
		String r[] = new String[n + 1];

		int i = 0;
		int b = 0;
		int l = s.length();
		do {
			int e = s.indexOf(c, b);
			if (e < 0)
				e = l;
			r[i++] = s.substring(b, e);

			b = e + 1;
		} while (b < l);
		return r;
	}
	
	public static Integer[] splitInteger (String s, char c) {
		if (s == null)
			return null;
		int n = count(s, c);
		Integer r[] = new Integer[n + 1];

		int i = 0;
		int b = 0;
		int l = s.length();
		do {
			int e = s.indexOf(c, b);
			if (e < 0)
				e = l;
			try {
				r[i++] = Integer.parseInt(s.substring(b, e));
			}
			catch (NumberFormatException ex) {
				r[i++] = 0;
			}

			b = e + 1;
		} while (b < l);
		return r;	
	}
	
	public static boolean isNotBlank (String str) {
		return (str != null && str.length() > 0 && !str.trim().equals(""));
	}
	
	public static boolean isEqual (String str1, String str2) {
		return (str1.compareTo(str2) == 0);
	}

}
