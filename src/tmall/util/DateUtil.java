package tmall.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {
	// java.util.Dateתjava.sql.Timestamp
	public static Timestamp d2t(Date date) {
		if (null == date) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	// java.sql.Timestampתjava.util.Date
	public static Date t2d(Timestamp ts) {
		if (null == ts) {
			return null;
		}
		return new Date(ts.getTime());
	}

}
