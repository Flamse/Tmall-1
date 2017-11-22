package tmall.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;

public class Daate {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");//∏Ò ΩªØ
		Date now = new Date();
		System.out.println(sdf.format(now)+RandomUtils.nextInt(0, 10000));
	}
}
